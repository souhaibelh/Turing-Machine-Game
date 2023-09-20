package g61610.atl.ascii.model;

public class Rectangle extends ColoredShape {
    private Point upperLeft;
    private double width;
    private double height;

    public Rectangle(Point upperLeft, double width, double height, char color) {
        super(color);
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    public boolean isInside(Point p) {
        if (p.getX() > this.upperLeft.getX() && p.getX() <= this.upperLeft.getX() + width && (p.getY() < this.upperLeft.getY() && p.getY() >= this.upperLeft.getY() - height)) {
            return true;
        } else {
            return false;
        }
    }

    public void move(double dx, double dy) {
        this.upperLeft.move(dx,dy);
    }
}
