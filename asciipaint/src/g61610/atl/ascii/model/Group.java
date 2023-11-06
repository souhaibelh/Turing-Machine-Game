package g61610.atl.ascii.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group extends ColoredShape {
    private final List<Shape> Shapes;

    public Group(char color) {
        super(color);
        this.Shapes = new ArrayList<>();
    }

    @Override
    public boolean isInside(Point p) {
        for (Shape s : Shapes) {
            if (s.isInside(p)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void move(double dx, double dy) {
        for (Shape s : Shapes) {
            s.move(dx,dy);
        }
    }

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