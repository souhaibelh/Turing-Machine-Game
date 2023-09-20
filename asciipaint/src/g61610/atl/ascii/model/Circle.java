package g61610.atl.ascii.model;

public class Circle extends ColoredShape {
    private Point center;
    private double radius;

    public Circle(Point center, double radius, char color) {
        super(color);
        this.center = center;
        this.radius = radius;
    }

    public boolean isInside(Point p) {
        boolean insidebool;
        if (radius < 4) {
            insidebool = p.distanceTo(this.center) <= this.radius;
        } else {
            insidebool = p.distanceTo(this.center) < this.radius;
        }
        if (insidebool) {
            return true;
        } else {
            return false;
        }
    }

    public void move(double dx, double dy) {
        this.center.move(dx,dy);
    }
}
