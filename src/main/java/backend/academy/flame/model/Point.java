package backend.academy.flame.model;

public record Point(double x, double y) {
    public Point rotate(double angle, int width, int height) {
        double centerX = width / 2.0;
        double centerY = height / 2.0;

        double relativeX = x - centerX;
        double relativeY = y - centerY;

        double rotatedX = relativeX * Math.cos(angle) - relativeY * Math.sin(angle);
        double rotatedY = relativeX * Math.sin(angle) + relativeY * Math.cos(angle);

        return new Point(rotatedX + centerX, rotatedY + centerY);
    }
}
