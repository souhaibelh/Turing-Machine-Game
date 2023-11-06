package g61610.atl.ascii.model;

/**
 * Defines the change color command, has 3 attributes the shape from which we remove the color, the old color of the shape
 * (we need this for the undo command) and the new color of the shape to assign it to the shape
 */
public class ChangeColorCommand implements Command {
    private final Shape shape;
    private final char old_color;
    private final char new_color;

    /**
     * Initializes the change color command
     * @param shape shape of which to change the shape
     * @param new_color new color of the shape
     */
    public ChangeColorCommand(Shape shape, char new_color) {
        this.shape = shape;
        this.old_color = shape.getColor();
        this.new_color = new_color;
    }

    /**
     * Changes the color of the shape to the new color
     */
    @Override
    public void execute() {
        shape.setColor(new_color);
    }

    /**
     * Changes the color of the shape to it's old color
     */
    @Override
    public void unexecute() {
        shape.setColor(old_color);
    }
}
