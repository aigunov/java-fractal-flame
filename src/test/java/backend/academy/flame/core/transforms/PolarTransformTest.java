package backend.academy.flame.core.transforms;

import backend.academy.flame.core.transforms.PolarTransform;
import backend.academy.flame.model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PolarTransformTest {

    @Test
    void apply_transformsPointCorrectly_test1() {
        PolarTransform transform = new PolarTransform();
        Point inputPoint = new Point(1, 0);
        Point result = transform.apply(inputPoint);
        assertEquals(0, result.x(), 0.001);
        assertEquals(0, result.y(), 0.001);
    }

    @Test
    void apply_transformsPointCorrectly_test2() {
        PolarTransform transform = new PolarTransform();
        Point inputPoint = new Point(0, 1);
        Point result = transform.apply(inputPoint);
        assertEquals(0.5, result.x(), 0.001);
        assertEquals(0, result.y(), 0.001);
    }

    @Test
    void apply_transformsPointCorrectly_test4() {
        PolarTransform transform = new PolarTransform();
        Point inputPoint = new Point(0, -1);
        Point result = transform.apply(inputPoint);
        assertEquals(-0.5, result.x(), 0.001);
        assertEquals(0, result.y(), 0.001);
    }

    @Test
    void apply_transformsPointCorrectly_test5() {
        PolarTransform transform = new PolarTransform();
        Point inputPoint = new Point(1, 1);
        Point result = transform.apply(inputPoint);
        assertEquals(0.25, result.x(), 0.001);
        assertEquals(1.4142 -1, result.y(), 0.001);
    }

    @Test
    void apply_handlesNullInput() {
        PolarTransform transform = new PolarTransform();
        assertThrows(NullPointerException.class, () -> transform.apply(null));
    }
}
