package g61610.atl.ascii.model;

/**
 * Defines what a line is
 */
public class Line extends ColoredShape {
    private final Point point_a;
    private final Point point_b;

    /**
     * Constructor of the line
     * @param point_a point that defines the start of the line
     * @param point_b point that defines teh end of the line
     * @param color color of the line
     */
    Line(Point point_a, Point point_b, char color) {
        super(color);
        this.point_a = new Point(point_a);
        this.point_b = new Point(point_b);
    }

    /**
     * Determines whether a point is inside a line
     * @param p point we are trying to see if its inside our shape
     * @return true if the point is inside the line false if it isn't
     */
    @Override
    public boolean isInside(Point p) {
        return isPointOnLine(p, point_a, point_b);
    }

    /**
     * Moves the line
     * @param dx the x-axis offset
     * @param dy the y-axis offset
     */
    @Override
    public void move(double dx, double dy) {
        point_a.move(dx,dy);
        point_b.move(dx,dy);
    }

    /**
     * Determines whether a point is inside a line or no
     * @param p point
     * @param start start of the line
     * @param end end of the line
     * @return true if the point is inside the line false if it isn't
     */
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

    @Override
    public String toString() {
        return "Line";
    }
}
