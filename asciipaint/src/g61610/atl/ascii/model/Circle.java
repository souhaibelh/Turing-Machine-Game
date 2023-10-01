package g61610.atl.ascii.model;

public class Circle extends ColoredShape {
    private Point center;
    private final double radius;

    public Circle(Point center, double radius, char color) {
        super(color);
        if (radius <= 0) {
            throw new IllegalArgumentException("A circles radius must be greater than 0");
        }
        this.center = new Point(center);
        this.radius = radius;
    }

    public boolean isInside(Point p) {
        if (radius < 4) {
            return p.distanceTo(this.center) <= this.radius;
        }
        return p.distanceTo(this.center) < this.radius;
    }

    public void move(double dx, double dy) {
        this.center.move(dx,dy);
    }
}
