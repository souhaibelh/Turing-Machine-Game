package g61610.atl.ascii.model;

import java.util.List;

public class Drawing {
    private List<Shape> shapes;
    private int height;
    private int width;

    public Drawing() {
        this.height = 200;
        this.width = 200;
    }

    public Drawing(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public void addShape(Shape shape) {
        this.shapes.add(shape);
    }

    public Shape getShapeAt(Point p) {
        for (int i=0; i<this.shapes.size(); i++) {
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
}
