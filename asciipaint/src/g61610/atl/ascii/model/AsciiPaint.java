package g61610.atl.ascii.model;

import g61610.atl.ascii.util.CommandManager;

import java.util.Arrays;
import java.util.List;

/**
 * This serves as the class that the controller will use to handle everything, kinda like the entrance of a home.
 */
public class AsciiPaint {
    private final Drawing drawing;
    private final CommandManager commandManager = new CommandManager();

    /**
     * Constructor that initializes the drawing board to its default state.
     */
    public AsciiPaint() {

        drawing = new Drawing();
    }

    /**
     * Constructor that initializes drawing board to a specific width and height
     * @param width width of the drawing board
     * @param height height of the drawing board
     */
    public AsciiPaint(int width, int height) {
        drawing = new Drawing(width,height);
    }

    /**
     * Method that adds a new circle, it adds the circle shape in the List of shapes that serves as an attribute inside the
     * Drawing instance.
     * @param x x-coordinate of the point that will serve as a center for the circle
     * @param y y-coordinate of the point that will serve as a center for the circle
     * @param radius radius of the circle
     * @param color character that will represent the color of the circle
     */
    public void newCircle(int x, int y, double radius, char color) {
        // The radius of a circle cannot be smaller than 0 nor 0 (if it's 0 then it's a point).
        if (radius <= 0) {
            throw new IllegalArgumentException("A circles radius must be greater than 0: "+radius);
        }
        Circle circle = new Circle(new Point(x,y),radius,color);
        Command command = new AddShapeCommand(drawing,circle);
        commandManager.newCommand(command);
    }

    /**
     * Method that adds a new rectangle, it adds the rectangle shape in the List of shapes that serves as an attribute inside
     * the Drawing instance.
     * @param x x-coordinate of the upperLeft point of the rectangle
     * @param y y-coordinate of the upperLeft point of the rectangle
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @param color character that will represent the color of the rectangle
     */
    public void newRectangle(int x, int y, double width, double height, char color) {
        // If the width is smaller than 0 or the height is smaller than 0 we throw an error
        if (width <= 0 || height <= 0) {
            throw new AsciiPaintException("Rectangles width and height must be greater than 0");
        }
        Rectangle rectangle = new Rectangle(new Point(x,y),width,height,color);
        Command command = new AddShapeCommand(drawing,rectangle);
        commandManager.newCommand(command);
    }

    /**
     * Method that adds a new square, it adds teh square shape in the List of shapes that serves as an attribute inside
     * the Drawing instance.
     * @param x x-coordinate of the upperLeft point of the square
     * @param y y-coordinate of the upperLeft point of the square
     * @param side length of the side of the square
     * @param color character that will represent the color of the rectangle
     */
    public void newSquare(int x, int y, double side, char color) {
        // If the side is smaller or equal to 0 we throw an error
        if (side <= 0) {
            throw new AsciiPaintException("Squares width and height must be greater than 0");
        }
        Square square = new Square(new Point(x,y),side,color);
        Command command = new AddShapeCommand(drawing,square);
        commandManager.newCommand(command);
    }

    public void newLine(int x1, int y1, int x2, int y2, char color) {
        Line line = new Line(new Point(x1,y1),new Point(x2,y2),color);
        Command command = new AddShapeCommand(drawing, line);
        commandManager.newCommand(command);
    }

    public void group(char color, int... index) {
        Shape[] shapes = new Shape[index.length];
        Arrays.sort(index);
        for (int id : index) {
            validateIndex(id);
        }
        for (int i=0; i<index.length; i++) {
            shapes[i] = drawing.getShapes().get(index[i]);
        }
        Command group = new GroupCommand(color, drawing, shapes);
        commandManager.newCommand(group);
    }

    public void ungroup(int group_index) {
        validateIndex(group_index);
        if (!(drawing.getShapes().get(group_index) instanceof Group)) {
            throw new AsciiPaintException("Cannot ungroup a non Group shape");
        }
        Command ungroup = new UngroupCommand(drawing,(Group) drawing.getShapes().get(group_index));
        commandManager.newCommand(ungroup);
    }

    public void undo() {
        commandManager.undo();
    }

    public void redo() {
        commandManager.redo();
    }

    /**
     * @return a new drawing created with the copy constructor inside the Drawing class
     */
    public Drawing getDrawing() {
        return new Drawing(drawing);
    }

    /**
     * @return an unmodifiable List of the shapes available in the Drawing instance
     */
    public List<Shape> getShapes() {
        return drawing.getShapes();
    }

    /**
     * Method that will move a shape by calling its move method, before doing so it first
     * calls the method ValidateIndex that will validate the index by making sure it's not
     * smaller than 0 and not bigger than the amount of items inside the List that contains
     * all the shapes inside our drawing instance.
     * @param index index of the shape inside the List of shapes of the Drawing instance
     * @param x the x-axis offset in which to move the shape
     * @param y the y-axis offset in which to move the shape
     * @throws AsciiPaintException throws this error if the index is smaller than 0 and bigger than the amount of items inside
     * the List of shapes that contains all the shapes inside the drawing instance.
     */
    public void moveShape(int index, int x, int y) throws AsciiPaintException {
        validateIndex(index);
        Command command = new MoveShapeCommand(drawing.getShapes().get(index),x,y);
        commandManager.newCommand(command);
    }

    /**
     * Method that will change the color of a shape by calling its setColor method, before doing so it
     * first calls the method ValidateIndex that will validate the index by making sure it's not smaller
     * than 0 and not bigger than the amount of items inside teh List that contains all the shapes
     * inside our drawing instance.
     * @param index index of the shape inside the List of shapes of the Drawing instance
     * @param new_color the new character that will represent the color of the Shape from now on
     * @throws AsciiPaintException throws this error if the index is smaller than 0 and bigger than the amount of items inside
     * the List of shapes that contains all the shapes inside the drawing instance.
     */
    public void changeColor(int index, char new_color) throws AsciiPaintException {
        validateIndex(index);
        Command command = new ChangeColorCommand(drawing.getShapes().get(index),new_color);
        commandManager.newCommand(command);
    }

    /**
     * This method here verifies an index, it is used by the changeColor method and the moveShape method, and it's purpose
     * is to verify an integer named in this case index is not smaller than 0 and not bigger than the size of the List of
     * shapes that is an attribute to the Drawing instance (in case it is smaller than 0 or bigger than List of shapes we throw
     * a AsciiPaintException)
     * @param index the index we must verify.
     */
    private void validateIndex(int index) {
        if (drawing.getShapes().isEmpty()) {
            throw new AsciiPaintException("The list is empty, add some shapes before trying to modify the color or move one of them");
        } else if (index < 0 || index > getShapes().size() - 1) {
            String error_index = getShapes().isEmpty() ? "0" : String.valueOf(getShapes().size() - 1);
            throw new AsciiPaintException("Index to access shape (color/move/group command) must be between 0 and " + error_index);
        }
    }
}
