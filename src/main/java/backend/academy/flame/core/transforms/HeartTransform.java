package backend.academy.flame.core.transforms;

import backend.academy.flame.model.Point;

public class HeartTransform implements Transformation {
    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        var x = (r * Math.sin(r * Math.atan2(point.y(), point.x()))) / 2;
        var y = (-r * Math.cos(r * Math.atan2(point.y(), point.x()))) / 2;
        return new Point(x, y);
    }
}
