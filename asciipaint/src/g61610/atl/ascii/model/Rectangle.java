package g61610.atl.ascii.model;

public class Rectangle extends ColoredShape {
    private final Point upperLeft;
    private final double width;
    private final double height;

    public Rectangle(Point upperLeft, double width, double height, char color) {
        super(color);
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Rectangles width and height must be greater than 0");
        }
        this.upperLeft = new Point(upperLeft);
        this.width = width;
        this.height = height;
    }

    public boolean isInside(Point p) {
        return p.getX() > this.upperLeft.getX() && p.getX() <= this.upperLeft.getX() + width && (p.getY() < this.upperLeft.getY() && p.getY() >= this.upperLeft.getY() - height);
    }

    public void move(double dx, double dy) {
        this.upperLeft.move(dx,dy);
    }
}
