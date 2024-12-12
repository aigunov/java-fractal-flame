package backend.academy.flame.core;

import backend.academy.flame.model.Configs;
import backend.academy.flame.model.FractalImage;
import backend.academy.flame.model.Point;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class FractalCalculatorSingleThread extends FractalCalculator {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public FractalImage render(Configs configs) {
        chooseTransformation(configs.transform());
        init(configs.width(), configs.height());

        var newX = random.nextDouble(XMIN, XMAX);
        var newY = random.nextDouble(YMIN, YMAX);

        var coefficients = coefficientGenerator.generateCoefficientsCompression(configs.affineCount());

        for (int i = -30; i < configs.iterationCount(); i++) {

            int idx = random.nextInt(0, configs.affineCount());
            var coefficient = coefficients.get(idx);

            var x = newX * coefficient.a() + newY * coefficient.b() + coefficient.c();
            var y = newX * coefficient.d() + newY * coefficient.e() + coefficient.f();

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
                            pixel.red(coefficient.palette().red());
                            pixel.green(coefficient.palette().green());
                            pixel.blue(coefficient.palette().blue());
                        } else {
                            pixel.red((pixel.red() + coefficient.palette().red()) / 2);
                            pixel.green((pixel.green() + coefficient.palette().green()) / 2);
                            pixel.blue((pixel.blue() + coefficient.palette().blue()) / 2);
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
}
