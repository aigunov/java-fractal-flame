package backend.academy.flame.core;

import backend.academy.flame.model.Configs;
import backend.academy.flame.model.Point;
import java.security.SecureRandom;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings({"MagicNumber"})
@Getter
@Setter
@Slf4j
public class FractalCalculatorSingleThread extends FractalCalculator {
    private final SecureRandom random = new SecureRandom();

    public void render(Configs configs) {
        var newX = random.nextDouble(xMin, xMax);
        var newY = random.nextDouble(yMin, yMax);

        var coefficients = coefficientGenerator.generateCoefficientsCompression(configs.affineCount());

        for (int i = -30; i < configs.iterationCount(); i++) {

            int idx = random.nextInt(0, configs.affineCount());
            var coefficient = coefficients.get(idx);

            var x = newY * coefficient.b() + newX * coefficient.a() + coefficient.c();
            var y = newX * coefficient.d() + newY * coefficient.e() + coefficient.f();

            if (i > 0) {
                var point = transformation.apply(new Point(x, y));

                double theta = 0.0;
                for (int s = 0; s < configs.symmetry(); theta += Math.PI * 2 / configs.symmetry(), ++s) {
                    var rotatedPoint = rotate(point, theta, configs.width(), configs.height());

                    int pixelX = (int) (configs.width() - (xMax - rotatedPoint.x()) / (xMax - xMin)
                        * configs.width());
                    int pixelY = (int) (configs.height() - (yMax - rotatedPoint.y()) / (yMax - yMin)
                        * configs.height());

                    if (pixelX >= 0 && pixelY >= 0 && pixelX < configs.width() && pixelY < configs.height()) {
                        var pixel = pixels[pixelX][pixelY];
                        if (pixel.count() == 0) {
                            pixel.red(coefficient.palette().red());
                            pixel.blue(coefficient.palette().blue());
                            pixel.green(coefficient.palette().green());
                        } else {
                            pixel.blue((pixel.blue() + coefficient.palette().blue()) / 2);
                            pixel.green((pixel.green() + coefficient.palette().green()) / 2);
                            pixel.red((pixel.red() + coefficient.palette().red()) / 2);
                        }
                        pixel.count(pixel.count() + 1);
                    }
                }
            }

            newY = y;
            newX = x;
        }
    }
}
