package backend.academy.flame.core;

import backend.academy.flame.model.Coefficients;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CoefficientGeneratorTest {
    @Test
    void generateCoefficientsCompression_returnsCorrectNumberOfCoefficients() {
        CoefficientGenerator generator = new CoefficientGenerator();
        int n = 10;
        List<Coefficients> coefficients = generator.generateCoefficientsCompression(n);
        assertEquals(n, coefficients.size());
    }

    @Test
    void generateCoefficientsCompression_coefficientsSatisfyConditions() {
        CoefficientGenerator generator = new CoefficientGenerator();
        int n = 10;
        List<Coefficients> coefficients = generator.generateCoefficientsCompression(n);

        for (Coefficients coeff : coefficients) {
            assertTrue(coeff.a() * coeff.a() + coeff.d() * coeff.d() < 1);
            assertTrue(coeff.b() * coeff.b() + coeff.e() * coeff.e() < 1);
            assertTrue(coeff.a() * coeff.a() + coeff.b() * coeff.b() + coeff.d() * coeff.d() + coeff.e() * coeff.e() <
                1 + (coeff.a() * coeff.e() - coeff.b() * coeff.d()) * (coeff.a() * coeff.e() - coeff.b() * coeff.d()));
            assertNotNull(coeff.palette());
        }
    }


    @Test
    void generateCoefficientsCompression_handlesZeroInput() {
        CoefficientGenerator generator = new CoefficientGenerator();
        List<Coefficients> coefficients = generator.generateCoefficientsCompression(0);
        assertTrue(coefficients.isEmpty());
    }

    @Test
    void generateCoefficientsCompression_handlesLargeInput() {
        CoefficientGenerator generator = new CoefficientGenerator();
        int n = 1000;
        List<Coefficients> coefficients = generator.generateCoefficientsCompression(n);
        assertEquals(n, coefficients.size());
    }

    @Test
    void generateCoefficientsCompression_differentPalettes(){
        CoefficientGenerator generator = new CoefficientGenerator();
        int n = 10;
        List<Coefficients> coefficients = generator.generateCoefficientsCompression(n);
        boolean differentPalettes = false;
        if(coefficients.size() > 1){
            differentPalettes = coefficients.get(0).palette() != coefficients.get(1).palette();
        }
        assertTrue(differentPalettes);
    }
}
