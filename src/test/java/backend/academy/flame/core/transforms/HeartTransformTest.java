package backend.academy.flame.core.transforms;
import backend.academy.flame.core.transforms.HeartTransform;
import backend.academy.flame.model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HeartTransformTest {

    @Test
    void apply_transformsPointCorrectly_test1() {
        HeartTransform transform = new HeartTransform();
        Point inputPoint = new Point(0, 0);
        Point result = transform.apply(inputPoint);
        assertEquals(0, result.x(), 0.001);
        assertEquals(0, result.y(), 0.001);
    }

    @Test
    void apply_transformsPointCorrectly_test2() {
        HeartTransform transform = new HeartTransform();
        Point inputPoint = new Point(1, 0);
        Point result = transform.apply(inputPoint);
        assertEquals(0, result.x(), 0.001);
        assertEquals(-0.5, result.y(), 0.001);
    }

    @Test
    void apply_transformsPointCorrectly_test3() {
        HeartTransform transform = new HeartTransform();
        Point inputPoint = new Point(0, 1);
        Point result = transform.apply(inputPoint);
        assertEquals(0.5, result.x(), 0.001);
        assertEquals(0, result.y(), 0.001);
    }

    @Test
    void apply_handlesNullInput() {
        HeartTransform transform = new HeartTransform();
        assertThrows(NullPointerException.class, () -> transform.apply(null));
    }
}
