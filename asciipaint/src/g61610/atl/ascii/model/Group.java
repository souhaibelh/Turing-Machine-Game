package g61610.atl.ascii.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Defines the Group shape which is basically just multiple shapes
 */
public class Group extends ColoredShape {
    private final List<Shape> Shapes;

    /**
     * Constructs the group
     * @param color color to assign to all the shapes in the group
     */
    public Group(char color) {
        super(color);
        this.Shapes = new ArrayList<>();
    }

    /**
     * Checks if a certain point is inside the group (at least inside of one of the shapes in the group)
     * @param p point we are trying to see if its inside our shape
     * @return true if the point is inside one of the shapes in the group or false if not
     */
    @Override
    public boolean isInside(Point p) {
        for (Shape s : Shapes) {
            if (s.isInside(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Moves the group by moving each single one of the shapes by the same x and y offset
     * @param dx the x-axis offset
     * @param dy the y-axis offset
     */
    @Override
    public void move(double dx, double dy) {
        for (Shape s : Shapes) {
            s.move(dx,dy);
        }
    }

    /**
     * Method that adds a shape to the group
     * @param s shape to add to the group
     */
    public void addShape(Shape s) {
        Shapes.add(s);
    }

    public List<Shape> getShapes() {
        return Collections.unmodifiableList(Shapes);
    }

    @Override
    public String toString() {
        return "Group: " + getShapes();
    }
}