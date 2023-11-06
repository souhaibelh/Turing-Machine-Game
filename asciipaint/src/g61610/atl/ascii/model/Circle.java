package g61610.atl.ascii.model;

/**
 * This class defines what a Circle is, it implements ColoredShape which already defines the setColor and getColor.
 * A Circle is a geometrical object with a center point and a radius.
 */
public class Circle extends ColoredShape {
    private final Point center;
    private final double radius;

    /**
     * This is the circle's constructor, it takes in the center point, the length of the radius and a single character
     * describing the color of the Circle, it calls the parent's constructor (ColoredShape) with the color of the Circle
     * as argument and then assigns the Point passed in as an argument to be the center of the Circle, and the double representing
     * the radius as the radius of the circle.
     * @param center the center of the Circle
     * @param radius the radius of the Circle
     * @param color single character representing the color of the Circle
     */
    public Circle(Point center, double radius, char color) {
        super(color);
        // The radius of a circle cannot be smaller than 0 nor 0 (if it's 0 then it's a point).
        if (radius <= 0) {
            throw new IllegalArgumentException("A circles radius must be greater than 0: "+radius);
        }
        this.center = new Point(center);
        this.radius = radius;
    }

    /**
     * Returns true if the point is in the circle and false if it isn't.
     * @param p point we are trying to see if its inside our circle
     * @return true or false depending on whether the point is inside the circle or no
     */
    public boolean isInside(Point p) {
        return p.distanceTo(center) < radius;
    }

    /**
     * This method here moves the center of the Circle by an offset.
     * @param dx the x-axis offset
     * @param dy the y-axis offset
     */
    public void move(double dx, double dy) {
        center.move(dx,dy);
    }

    @Override
    public String toString() {
        return "Circle";
    }
}
