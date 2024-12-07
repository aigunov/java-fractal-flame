package backend.academy.flame.core.transforms;

import backend.academy.flame.model.Point;

public class PolarTransform implements Transformation {
    @Override
    public Point apply(Point point) {
        var x = (Math.atan2(point.y(), point.x())) / Math.PI;
        var y = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2)) - 1;
        return new Point(x, y);
    }
}
