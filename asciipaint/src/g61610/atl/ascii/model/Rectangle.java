package g61610.atl.ascii.model;

/**
 * This class defines what a Rectangle is, it implements ColoredShape which already defines the setColor and getColor.
 * A rectangle is a geometrical object with a width and a height, in top of that we define what point the rectangle should
 * be moved on, the upperLeft point!
 */
public class Rectangle extends ColoredShape {
    private final Point upperLeft;
    private final double width;
    private final double height;

    /**
     * The rectangles constructor calls its parent (ColoredShape) to initialize the color attribute, it sets the upperLeft
     * attribute to a new point for protection of reference and sets the width and height respectively as passed in the constructor.
     * @param upperLeft upperLeft point of the rectangle
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @param color character representing the color of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height, char color) {
        super(color);
        // If the width is smaller than 0 or the height is smaller than 0 we throw an error
        if (width <= 0 || height <= 0) {
            throw new AsciiPaintException("Rectangles width and height must be greater than 0");
        }
        this.upperLeft = new Point(upperLeft);
        this.width = width;
        this.height = height;
    }

    /**
     * If the point's x coordinate is greater than the upperLeft point x coordinate but still smaller or equal to the upperLeft
     * x coordinate + the width of the rectangle, it means the point is within the width of the rectangle, but this is not enough
     * we must check it is within the height of the rectangle, to do that we must check that the y coordinate of the point is below
     * the upperLeft y coordinate and the y coordinate is above or equal to the upperLeft point y coordinate, which means it is within
     * the height of the rectangle, if both of these statements are true we return true, if at least one of them is false we return
     * false, because it means either the point is within the width but outside the height or within the height or outside the width,
     * or the point is neither within the width neither within the height.
     *
     * @param p point we are trying to see if its inside our shape
     * @return true or false depending on whether the point is inside or not
     */
    public boolean isInside(Point p) {
        return (p.getX() >= this.upperLeft.getX() && p.getX() < this.upperLeft.getX() + width) && (p.getY() >= this.upperLeft.getY()
                && p.getY() < this.upperLeft.getY() + height);
    }

    /**
     * This method moves the rectangle, how do you move the rectangle ? by moving the upperLeft point, the display method will display
     * the rectangle based on this point.
     * @param dx the x-axis offset
     * @param dy the y-axis offset
     */
    public void move(double dx, double dy) {
        this.upperLeft.move(dx,dy);
    }
}
