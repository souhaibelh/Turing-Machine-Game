package g61610.atl.ascii.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Drawing {
    private List<Shape> shapes = new ArrayList<>();
    private final int height;
    private final int width;

    public Drawing(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Drawing board width or height cannot be smaller or equal to 0");
        }
        this.height = height;
        this.width = width;
    }

    public Drawing() {
        this(50,50); // Default constructor with default width and height.
    }

    public void addShape(Shape shape) {
        this.shapes.add(shape);
    }

    public Shape getShapeAt(Point p) {
        for (int i=this.shapes.size() - 1; i>=0; i--) {
            if (this.shapes.get(i).isInside(p)) {
                return this.shapes.get(i);
            }
        }
        return null;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public List<Shape> getShapes() {
        return Collections.unmodifiableList(this.shapes);
    }
}
