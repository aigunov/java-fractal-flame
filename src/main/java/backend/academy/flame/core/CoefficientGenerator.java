package backend.academy.flame.core;

import backend.academy.flame.model.Coefficients;
import backend.academy.flame.model.Palette;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class CoefficientGenerator {
    private Random random = new SecureRandom();
    private final List<Coefficients> coefficients = new ArrayList<>();

    public List<Coefficients> generateCoefficientsCompression(int n) {
        double a;
        double b;
        double d;
        double e;
        double c;
        double f;
        var count = n;

        while (count > 0) {
            a = 2 * random.nextDouble() - 1;
            b = 2 * random.nextDouble() - 1;
            d = 2 * random.nextDouble() - 1;
            e = 2 * random.nextDouble() - 1;
            c = 2 * random.nextDouble() - 1;
            f = 2 * random.nextDouble() - 1;

            if (a * a + d * d < 1
                && b * b + e * e < 1
                && a * a + b * b + d * d + e * e < 1 + (a * e - b * d) * (a * e - b * d)) {
                Palette[] palettes = Palette.values();
                var palette = palettes[random.nextInt(palettes.length)];
                coefficients.add(new Coefficients(a, b, c, d, e, f, palette));
                count -= 1;
            }
        }
        return  coefficients;
    }
}
