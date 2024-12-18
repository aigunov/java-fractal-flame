package backend.academy.flame.core.transforms;

import backend.academy.flame.model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SphereTransformTest {

    @Test
    void apply_transformsPointCorrectly_test1() {
        SphereTransform transform = new SphereTransform();
        Point inputPoint = new Point(1, 0);
        Point result = transform.apply(inputPoint);
        assertEquals(0.3333, result.x(), 0.001);
        assertEquals(0, result.y(), 0.001);
    }

    @Test
    void apply_transformsPointCorrectly_test2() {
        SphereTransform transform = new SphereTransform();
        Point inputPoint = new Point(0, 1);
        Point result = transform.apply(inputPoint);
        assertEquals(0, result.x(), 0.001);
        assertEquals(0.3333, result.y(), 0.001);
    }

    @Test
    void apply_transformsPointCorrectly_test3() {
        SphereTransform transform = new SphereTransform();
        Point inputPoint = new Point(1, 1);
        Point result = transform.apply(inputPoint);
        assertEquals(0.1666, result.x(), 0.001);
        assertEquals(0.1666, result.y(), 0.001);
    }

    @Test
    void apply_handlesNullInput() {
        SphereTransform transform = new SphereTransform();
        assertThrows(NullPointerException.class, () -> transform.apply(null));
    }
}
