package g61610.atl.ascii.model;

import java.util.List;

/**
 * This serves as the class that the controller will use to handle everything, kinda like the entrance of a home.
 */
public class AsciiPaint {
    private final Drawing drawing;

    /**
     * Constructor that initializes the drawing board to its default state.
     */
    public AsciiPaint() {
        this.drawing = new Drawing();
    }

    /**
     * Constructor that initializes drawing board to a specific width and height
     * @param width width of the drawing board
     * @param height height of the drawing board
     */
    public AsciiPaint(int width, int height) {
        this.drawing = new Drawing(width,height);
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
        // tester les valeurs et lancer une exception.
        this.drawing.addShape(new Circle(new Point(x,y),radius,color));
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
        this.drawing.addShape(new Rectangle(new Point(x,y),width,height,color));
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
        this.drawing.addShape(new Square(new Point(x,y),side,color));
    }

    /**
     * @return a new drawing created with the copy constructor inside the Drawing class
     */
    public Drawing getDrawing() {
        return new Drawing(this.drawing);
    }

    /**
     * @return an unmodifiable List of the shapes available in the Drawing instance
     */
    public List<Shape> getShapes() {
        return this.drawing.getShapes();
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
        this.drawing.getShapes().get(index).move(x,y);
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
        this.drawing.getShapes().get(index).setColor(new_color);
    }

    /**
     * This method here verifies an index, it is used by the changeColor method and the moveShape method, and it's purpose
     * is to verify an integer named in this case index is not smaller than 0 and not bigger than the size of the List of
     * shapes that is an attribute to the Drawing instance (in case it is smaller than 0 or bigger than List of shapes we throw
     * a AsciiPaintException)
     * @param index the index we must verify.
     */
    private void validateIndex(int index) {
        if (this.drawing.getShapes().isEmpty()) {
            throw new AsciiPaintException("The list is empty, add some shapes before trying to modify the color or move one of them");
        } else if (index < 0 || index > getShapes().size() - 1) {
            String error_index = getShapes().isEmpty() ? "0" : String.valueOf(getShapes().size() - 1);
            throw new AsciiPaintException("Index to access shape (color/move command) must be between 0 and " + error_index);
        }
    }
}
