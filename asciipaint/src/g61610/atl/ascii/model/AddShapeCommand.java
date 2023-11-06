package g61610.atl.ascii.model;

/**
 * Defines the add command, has 2 attributes the drawing in which to add the shape and the shape to add
 */
public class AddShapeCommand implements Command {

    private final Shape shape;
    private final Drawing drawing;

    /**
     * Constructs the add shape command
     * @param drawing drawing in which to add the shape
     * @param shape shape to add to the drawing
     */
    AddShapeCommand(Drawing drawing, Shape shape) {
        this.shape = shape;
        this.drawing = drawing;
    }

    /**
     * Executes the command and adds a shape to the drawing
     */
    @Override
    public void execute() {
        this.drawing.addShape(shape);
    }

    /**
     * Unexecutes the command and removes the shape from the drawing
     */
    @Override
    public void unexecute() {
        this.drawing.removeShape(shape);
    }
}
