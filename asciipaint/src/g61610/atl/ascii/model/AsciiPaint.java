package g61610.atl.ascii.model;

import java.util.List;

public class AsciiPaint {
    private final Drawing drawing;

    public AsciiPaint() {
        this.drawing = new Drawing();
    }

    public AsciiPaint(int width, int height) {
        this.drawing = new Drawing(width,height);
    }

    public void newCircle(int x, int y, double radius, char color) {
        this.drawing.addShape(new Circle(new Point(x,y),radius,color));
    }

    public void newRectangle(int x, int y, double width, double height, char color) {
        this.drawing.addShape(new Rectangle(new Point(x,y),width,height,color));
    }

    public void newSquare(int x, int y, double side, char color) {
        this.drawing.addShape(new Square(new Point(x,y),side,color));
    }

    public Drawing getDrawing() {
        return new Drawing(this.drawing);
    }

    public List<Shape> getShapes() {
        return this.drawing.getShapes();
    }

    public void moveShape(int index, int x, int y) {
        if (index < 0 || index > this.drawing.getShapes().size() - 1) {
            throw new AsciiPaintException("Index of shape to move must be between 0 and " + (this.drawing.getShapes().size() - 1));
        }
        this.drawing.getShapes().get(index).move(x,y);
    }

    public void changeColor(int index, char newcolor) {
        if (index < 0 || index > this.drawing.getShapes().size() - 1) {
            throw new AsciiPaintException("Index of shape to change color must be between 0 and " + (this.drawing.getShapes().size() - 1));
        }
        this.drawing.getShapes().get(index).setColor(newcolor);
    }
}
