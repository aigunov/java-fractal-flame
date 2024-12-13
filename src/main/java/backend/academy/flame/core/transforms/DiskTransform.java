package backend.academy.flame.core.transforms;

import backend.academy.flame.model.Point;

public class DiskTransform implements Transformation {
    @Override
    public Point apply(Point point) {
        var x = (1 / Math.PI * Math.atan2(point.y(), point.x())
            * Math.sin(Math.PI * Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2)))) / 2;
        var y = (1 / Math.PI * Math.atan2(point.y(), point.x())
            * Math.cos(Math.PI * Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2)))) / 2;
        return new Point(x, y);
    }
}
