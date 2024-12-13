package backend.academy.flame.core.transforms;

import backend.academy.flame.model.Point;

@SuppressWarnings({"MagicNumber"})
public class SphereTransform implements Transformation {
    @Override
    public Point apply(Point point) {
        double denominator = Math.pow(point.x(), 2) + Math.pow(point.y(), 2);
        double x = (point.x() / denominator) / 3;
        double y = (point.y() / denominator) / 3;
        return new Point(x, y);
    }
}
