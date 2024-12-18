package backend.academy.flame.core.transforms;

import backend.academy.flame.model.Point;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SinusTransformTest {

    @Test
    void apply_transformsPointCorrectly_test1() {
        SinusTransform transform = new SinusTransform();
        Point inputPoint = new Point(0, 0);
        Point result = transform.apply(inputPoint);
        assertEquals(0, result.x(), 0.001);
        assertEquals(0, result.y(), 0.001);
    }

    @Test
    void apply_transformsPointCorrectly_test2() {
        SinusTransform transform = new SinusTransform();
        Point inputPoint = new Point(Math.PI / 2, Math.PI / 2);
        Point result = transform.apply(inputPoint);
        assertEquals(0.8333, result.x(), 0.001);
        assertEquals(0.8333, result.y(), 0.001);
    }

    @Test
    void apply_transformsPointCorrectly_test3() {
        SinusTransform transform = new SinusTransform();
        Point inputPoint = new Point(Math.PI, Math.PI);
        Point result = transform.apply(inputPoint);
        assertEquals(0, result.x(), 0.001);
        assertEquals(0, result.y(), 0.001);
    }

    @Test
    void apply_transformsPointCorrectly_test4() {
        SinusTransform transform = new SinusTransform();
        Point inputPoint = new Point(3 * Math.PI / 2, 3 * Math.PI / 2);
        Point result = transform.apply(inputPoint);
        assertEquals(-0.8333, result.x(), 0.001);
        assertEquals(-0.8333, result.y(), 0.001);
    }

    @Test
    void apply_handlesNullInput() {
        SinusTransform transform = new SinusTransform();
        assertThrows(NullPointerException.class, () -> transform.apply(null));
    }

}
