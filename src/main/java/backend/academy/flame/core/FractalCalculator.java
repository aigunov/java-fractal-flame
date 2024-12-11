package backend.academy.flame.core;

import backend.academy.flame.core.factory.DiskTransformFactory;
import backend.academy.flame.core.factory.HeartTransformFactory;
import backend.academy.flame.core.factory.PolarTransformationFactory;
import backend.academy.flame.core.factory.SinusTransformFactory;
import backend.academy.flame.core.factory.SphereTransformFactory;
import backend.academy.flame.core.factory.TransformationFactory;
import backend.academy.flame.core.transforms.Transformation;
import backend.academy.flame.model.Configs;
import backend.academy.flame.model.FractalImage;
import backend.academy.flame.model.Pixel;
import backend.academy.flame.model.Point;
import backend.academy.flame.model.TransformationFunction;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class FractalCalculator {
    private double XMAX;
    private double XMIN;
    private double YMAX = 1.0;
    private double YMIN = -1.0;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final CoefficientGenerator coeff;

    private TransformationFactory transformationFactory;
    private Transformation transformation;
    private Pixel[][] pixels;

    public FractalCalculator(CoefficientGenerator coeff) {
        this.coeff = coeff;
    }

    public FractalImage render(Configs configs) {
        chooseTransformation(configs.transform());
        calculateDiapason(configs.width(), configs.height());

        pixels = new Pixel[configs.width()][configs.height()];
        for (int x = 0; x < configs.width(); x++) {
            for (int y = 0; y < configs.height(); y++) {
                pixels[x][y] = new Pixel();
            }
        }

        var newX = random.nextDouble(XMIN, XMAX);
        var newY = random.nextDouble(YMIN, YMAX);

        var coefficients = coeff.generateCoefficientsCompression(configs.affineCount());

        for (int i = -30; i < configs.iterationCount(); i++) {

            int idx = random.nextInt(0, configs.affineCount());
            var coeff = coefficients.get(idx);

            var x = newX * coeff.a() + newY * coeff.b() + coeff.c();
            var y = newX * coeff.d() + newY * coeff.e() + coeff.f();

            if (i > 0) {
                var point = transformation.apply(new Point(x, y));

                double theta = 0.0;
                for (int s = 0; s < configs.symmetry(); theta += Math.PI * 2 / configs.symmetry(), ++s) {
                    var rotatedPoint = rotate(point, theta, configs.width(), configs.height());

                    double normalizedX = (XMAX - rotatedPoint.x()) / (XMAX - XMIN);
                    double normalizedY = (YMAX - rotatedPoint.y()) / (YMAX - YMIN);
                    int pixelX = (int) (configs.width() - normalizedX * configs.width());
                    int pixelY = (int) (configs.height() - normalizedY * configs.height());

                    if (pixelX >= 0 && pixelY >= 0 && pixelX < configs.width() && pixelY < configs.height()) {
                        var pixel = pixels[pixelX][pixelY];
                        if (pixel.count() == 0) {
                            pixel.red(coeff.palette().red());
                            pixel.green(coeff.palette().green());
                            pixel.blue(coeff.palette().blue());
                        } else {
                            pixel.red((pixel.red() + coeff.palette().red()) / 2);
                            pixel.green((pixel.green() + coeff.palette().green()) / 2);
                            pixel.blue((pixel.blue() + coeff.palette().blue()) / 2);
                        }
                        pixel.count(pixel.count() + 1);
                    }
                }
            }

            newX = x;
            newY = y;
        }
        correction(configs.width(), configs.height());
        return new FractalImage(configs, pixels, configs.width(), configs.height());
    }

    private Point rotate(Point point, double angle, int width, int height) {
        double centerX = width / 2.0;
        double centerY = height / 2.0;

        double relativeX = point.x() - centerX;
        double relativeY = point.y() - centerY;

        double rotatedX = relativeX * Math.cos(angle) - relativeY * Math.sin(angle);
        double rotatedY = relativeX * Math.sin(angle) + relativeY * Math.cos(angle);

        return new Point(rotatedX + centerX, rotatedY + centerY);
    }

    public void correction(int width, int height) {
        double max = 0.0;
        double gamma = 2.2;
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                if (pixels[row][col].count() != 0) {
                    pixels[row][col].normal(Math.log10(pixels[row][col].count()));
                    if (pixels[row][col].normal() > max) {
                        max = pixels[row][col].normal();
                    }
                }
            }
        }
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                pixels[row][col].normal(pixels[row][col].normal() / max);
                pixels[row][col].red(
                    (int) (pixels[row][col].red() * Math.pow(pixels[row][col].normal(), (1.0 / gamma))));
                pixels[row][col].green(
                    (int) (pixels[row][col].green() * Math.pow(pixels[row][col].normal(), (1.0 / gamma))));
                pixels[row][col].blue(
                    (int) (pixels[row][col].blue() * Math.pow(pixels[row][col].normal(), (1.0 / gamma))));
            }
        }
    }

    private void chooseTransformation(TransformationFunction transform) {
        transformationFactory = switch (transform) {
            case SINUS -> new SinusTransformFactory();
            case DISK -> new DiskTransformFactory();
            case HEART -> new HeartTransformFactory();
            case POLAR -> new PolarTransformationFactory();
            case SPHERE -> new SphereTransformFactory();
        };
        transformation = transformationFactory.transformation();
    }

    private void calculateDiapason(int width, int height) {
        double aspectRatio = (double) width / height;
        XMAX = aspectRatio / 2;
        XMIN = -XMAX;
    }

}
