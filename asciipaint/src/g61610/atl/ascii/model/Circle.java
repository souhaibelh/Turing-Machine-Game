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
     * as argument and then assigns the Point passed in as a argument to be the center of the Circle, and the double representing
     * the radius as the radius of the circle.
     * @param center the center of the Circle
     * @param radius the radius of the Circle
     * @param color single character representing the color of the Circle
     */
    public Circle(Point center, double radius, char color) {
        super(color);
        // The radius of a circle cannot be smaller than 0 nor 0 (if it's 0 then it's a point).
        if (radius <= 0) {
            throw new IllegalArgumentException("A circles radius must be greater than 0");
        }
        this.center = new Point(center);
        this.radius = radius;
    }

    /**
     * This method works differently than pure math, usually in mathematics every point between the center and the radius
     * are inside the circle, here it works different for displaying purposes. If the circle is too small (radius smaller than 4)
     * then we check if the point is between the center and the radius with the radius included (this will make it look like a circle
     * in the console) because otherwise we would have a '+' shaped circle. If the radius is bigger or equal than 4 we are going to
     * check if the point is between the center and the radius without including the radius (to give it a circular form).
     * @param p point we are trying to see if its inside our shape
     * @return true or false depending on whether the point p is inside or no the shape.
     */
    public boolean isInside(Point p) {
        /*
            If the radius is smaller than 4 we consider the radius border to be also part of the point, if it is bigger than 4
            we don't.
         */
        return radius < 4 ? p.distanceTo(this.center) <= this.radius : p.distanceTo(this.center) < this.radius;
    }

    /**
     * This method here moves the center of the Circle by an offset.
     * @param dx the x-axis offset
     * @param dy the y-axis offset
     */
    public void move(double dx, double dy) {
        this.center.move(dx,dy);
    }
}
