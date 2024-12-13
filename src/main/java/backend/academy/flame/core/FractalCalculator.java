package backend.academy.flame.core;

import backend.academy.flame.cli.ReportGenerator;
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
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings({"MagicNumber"})
@Getter
@Setter
public abstract class FractalCalculator {
    private ReportGenerator reportGenerator = new ReportGenerator(System.out);

    protected double xMax;
    protected double xMin;
    protected double yMax = 1.0;
    protected double yMin = -1.0;

    protected CoefficientGenerator coefficientGenerator;
    protected Pixel[][] pixels;
    protected TransformationFactory transformationFactory;
    protected Transformation transformation;

    public FractalCalculator() {
        coefficientGenerator = new CoefficientGenerator();
    }

    public static FractalCalculator chooseRealisation(int countOfThreads) {
        if (countOfThreads == 1) {
            return new FractalCalculatorSingleThread();
        } else {
            return new MultiThreadCalculator(countOfThreads);
        }
    }

    public abstract void render(Configs configs);

    public FractalImage process(Configs configs) {
        chooseTransformation(configs.transform());
        init(configs.width(), configs.height());

        long startTime = System.currentTimeMillis();

        render(configs);

        correction(configs.width(), configs.height());

        long endTime = System.currentTimeMillis();
        reportGenerator.generateReport(configs, endTime - startTime);
        return new FractalImage(configs, pixels, configs.width(), configs.height());
    }

    protected Point rotate(Point point, double angle, int width, int height) {
        double centerX = width / 2.0;
        double centerY = height / 2.0;

        double relativeX = point.x() - centerX;
        double relativeY = point.y() - centerY;

        double rotatedX = relativeX * Math.cos(angle) - relativeY * Math.sin(angle);
        double rotatedY = relativeX * Math.sin(angle) + relativeY * Math.cos(angle);

        return new Point(rotatedX + centerX, rotatedY + centerY);
    }

    protected void correction(int width, int height) {
        double max = 0.0;
        double gamma = 0.6;
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

    protected void init(int width, int height) {
        double aspectRatio = (double) width / height;
        xMax = aspectRatio / 2;
        xMin = -xMax;

        pixels = new Pixel[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y] = new Pixel();
            }
        }

    }

    protected void chooseTransformation(TransformationFunction transform) {
        transformationFactory = switch (transform) {
            case SINUS -> new SinusTransformFactory();
            case DISK -> new DiskTransformFactory();
            case HEART -> new HeartTransformFactory();
            case POLAR -> new PolarTransformationFactory();
            case SPHERE -> new SphereTransformFactory();
        };
        transformation = transformationFactory.transformation();
    }
}
