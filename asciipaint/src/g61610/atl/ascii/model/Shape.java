package g61610.atl.ascii.model;

/**
 * The shape interface, it defines a contract for all shapes (they must all override these methods).
 */

public interface Shape {

    /**
     * The implementation of this method should return true if a point is inside the shape, or false if it isnt.
     * @param p point we are trying to see if its inside our shape
     * @return either true or false depending on if the point is inside or outside
     */
    boolean isInside(Point p);

    /**
     * The implementation of this method should move a shape by either moving the center (for a circle) or the upper left point
     * (for squares and rectangles), it should move those points with an offset of dx in the x-axis and dy in the y-axis.
     * @param dx the x-axis offset
     * @param dy the y-axis offset
     */
    void move(double dx, double dy);

    /**
     * The implementation of this method should return the character describing the color of the shape.
     * @return character describing the color of the shape
     */
    char getColor();

    /**
     * The implementation of this method should set a certain shapes color to the color passed in parameters on this
     * method
     * @param c the new color of the shape
     */
    void setColor(char c);
}
