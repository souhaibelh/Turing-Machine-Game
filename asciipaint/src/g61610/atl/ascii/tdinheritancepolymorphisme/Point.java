package g61610.atl.ascii.tdinheritancepolymorphisme;

public class Point implements Movable {
    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public Point move(double dx, double dy) {
        x += dx;
        y += dy;
        return this;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
