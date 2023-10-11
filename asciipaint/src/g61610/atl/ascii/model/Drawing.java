package g61610.atl.ascii.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class defines the drawing board, the drawing board has a width and a height and a list that contains all the shapes
 * inside this drawing board.
 */
public class Drawing {
    private List<Shape> shapes = new ArrayList<>();
    private final int height;
    private final int width;
    private static final int default_height = 50;
    private static final int default_width = 50;

    /**
     * This is the constructor of a drawing board, it takes in a width and a height and creates a drawing board with those
     * width and height.
     * @param width width of the board
     * @param height height of the board
     */
    public Drawing(int width, int height) {
        // a board's height and with must be bigger than 0
        if (width <= 0 || height <= 0) {
            throw new AsciiPaintException("Drawing board width or height cannot be smaller or equal to 0");
        }
        this.height = height;
        this.width = width;
    }

    /**
     * Copy constructor, it takes in a drawing creates a new one with same with and height, and it assigns its shapes to the returned
     * List from the getShapes() method which returns a unmodifiable list (for obvious security reasons).
     * @param drawing the drawing to copy
     */
    public Drawing(Drawing drawing) {
        this(drawing.width, drawing.height);
        this.shapes = drawing.getShapes();
    }

    /**
     * Default constructor of the drawing board, it makes a board with default width and height.
     */
    public Drawing() {
        this(default_width,default_height); // Default constructor with default width and height.
    }

    /**
     * Method that adds a shape in the list, basically adds a shape to the board
     * @param shape shape to add to the board
     */
    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    /**
     * Iterates through all the shapes inside the board and returns the last one added that contains the Point p passed in
     * as a parameter, we want to get the last one for displaying purposes, so if we for example add a square of dimensions 20x20
     * and inside it there is a square of dimensions 5x5 with different colour, if we add the second one after the first one
     * we will get the last one, and we will be able to display it above the previous one added.
     * @param p point to check what shape contains it
     * @return the last added shape that contains the Point p
     */
    public Shape getShapeAt(Point p) {
        for (int i=shapes.size() - 1; i>=0; i--) {
            if (shapes.get(i).isInside(p)) {
                return shapes.get(i);
            }
        }
        return null;
    }

    /**
     * @return the height of the drawing board
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the width of the drawing board
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return a copy of the list containing the current shapes in the drawing board, this is so we never return the
     * reference of the shapes we have in the board to avoid changing them from outside this class or any malicious changes.
     */
    public List<Shape> getShapes() {
        return List.copyOf(shapes);
    }
}
