package g61610.atl.ascii.model;

import java.util.ArrayList;
import java.util.List;

public class Drawing {
    private List<Shape> shapes;
    private int height;
    private int width;

    public Drawing() {
        this.shapes = new ArrayList<Shape>();
        this.height = 25;
        this.width = 25;
    }

    public Drawing(int width, int height) {
        this.shapes = new ArrayList<Shape>();
        this.height = height;
        this.width = width;
    }

    public void addShape(Shape shape) {
        this.shapes.add(shape);
    }

    public Shape getShapeAt(Point p) {
        Shape latestShape = null;
        for (int i=0; i<this.shapes.size(); i++) {
            if (this.shapes.get(i).isInside(p)) {
                latestShape = this.shapes.get(i);
            }
        }
        return latestShape;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public List<Shape> getShapes() {
        return this.shapes;
    }
}
