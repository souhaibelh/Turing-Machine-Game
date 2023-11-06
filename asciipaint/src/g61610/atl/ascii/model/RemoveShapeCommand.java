package g61610.atl.ascii.model;

/**
 * Defines the remove shape command, has 2 attributes the shape to remove and the drawing from which to remove it
 */
public class RemoveShapeCommand implements Command {
    private final Shape shape;
    private final Drawing drawing;

    /**
     * Constructor of the remove shape command
     */
    RemoveShapeCommand(Shape shape, Drawing drawing) {
        this.shape = shape;
        this.drawing = drawing;
    }

    /**
     * Removes the shape from the drawing
     */
    @Override
    public void execute() {
        drawing.removeShape(shape);
    }

    /**
     * Adds the shape to the drawing
     */
    @Override
    public void unexecute() {
        drawing.addShape(shape);
    }
}
