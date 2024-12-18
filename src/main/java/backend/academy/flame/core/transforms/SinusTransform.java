package backend.academy.flame.core.transforms;

import backend.academy.flame.model.Point;

@SuppressWarnings({"MagicNumber"})
public class SinusTransform implements Transformation {
    @Override
    public Point apply(Point point) {
        var x = (Math.sin(point.x())) / 1.2;
        var y = (Math.sin(point.y())) / 1.2;
        return new Point(x, y);
    }
}
