package g61610.atl.ascii.model;

public class Line extends ColoredShape {
    private final Point point_a;
    private final Point point_b;

    Line(Point point_a, Point point_b, char color) {
        super(color);
        this.point_a = new Point(point_a);
        this.point_b = new Point(point_b);
    }

    @Override
    public boolean isInside(Point p) {
        return isPointOnLine(p, point_a, point_b);
    }

    @Override
    public void move(double dx, double dy) {
        point_a.move(dx,dy);
        point_b.move(dx,dy);
    }

    private boolean isPointOnLine(Point p, Point start, Point end) {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();

        double dxc = p.getX() - start.getX();
        double dyc = p.getY() - start.getY();

        double crossProduct = dxc * dy - dyc * dx;

        if (Math.abs(crossProduct) > 0.5) {
            return false;
        }

        double dotProduct = dxc * dx + dyc * dy;
        if (dotProduct < 0) {
            return false;
        }

        double squaredLength = dx * dx + dy * dy;
        return dotProduct <= squaredLength;
    }
}
