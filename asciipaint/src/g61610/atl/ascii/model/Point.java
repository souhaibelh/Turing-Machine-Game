package g61610.atl.ascii.model;

public class Point {
    private double x;
    private double y;

    public Point (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this(p.x,p.y);
    }

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public double distanceTo(Point other) {
        return Math.sqrt(((other.x - this.x) * (other.x - this.x)) + ((other.y - this.y) * (other.y - this.y)));
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
