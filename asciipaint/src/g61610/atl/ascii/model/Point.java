package g61610.atl.ascii.model;

/**
 * This class defines what a Point is, a Point is an object with 2 attributes, representing the x and y axis coordinate
 * respectively, using "double" as type.
 */
public class Point {
    private double x;
    private double y;

    /**
     * Constructor that creates a new point with the x and y coordinates passed in as arguments.
     * @param x x coordinate of the point
     * @param y y coordinate of the point
     */
    public Point (double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * A second constructor that makes a new point from a point passed in as a parameter, this is called a
     * defensive copy, which means it simply makes a new point from another point to protect the reference of the
     * point passed in as argument (this will protect the Circle's center and the Rectangle's/Square's upperLeft point
     * from any malicious modification.)
     * @param p the point we will copy
     */
    public Point(Point p) {
        this(p.x,p.y);
    }

    /**
     * This method is quite simply, it moves the point by a dx offset on the x-axis and by a dy offset on the y-axis.
     * @param dx x-axis offset
     * @param dy y-axis offset
     */
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * This method calculates the distance between the point calling the method and the point passed in as a parameter,
     * it simply returns the result of the arithmetic formula to calculate distance between 2 points.
     * @param other the point we are trying to find the distance from the point that called the method
     * @return a double representing the distance between these 2 points
     */
    public double distanceTo(Point other) {
        return Math.sqrt(((other.x - this.x) * (other.x - this.x)) + ((other.y - this.y) * (other.y - this.y)));
    }

    /**
     * Returns the x coordinate of the Point that calls the method
     * @return x coordinate of the Point that calls the method
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the y coordinate of the Point that calls the method
     * @return y coordinate of the Point that calls the method
     */
    public double getY() {
        return this.y;
    }
}
