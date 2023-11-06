package g61610.atl.ascii.model;

/**
 * Defines the group command, has 3 attributes, the array containing the grouped shapes, the group itself and the
 * drawing in which to add or remove the group from.
 */
public class GroupCommand implements Command {
    private final Shape[] shapes;
    private final Group group;
    private final Drawing drawing;

    /**
     * Constructs the group command
     * @param color color of all the shapes in the group
     * @param drawing drawing in which to add/remove the group
     * @param shape array of shapes to add to the group
     */
    GroupCommand(char color, Drawing drawing, Shape... shape) {
        this.shapes = shape;
        this.drawing = drawing;
        group = new Group(color);
    }

    /**
     * Adds the shapes in a group then adds the group in the drawing and removes from the drawing the shapes
     */
    @Override
    public void execute() {
        for (Shape s : shapes) {
            group.addShape(s);
        }
        for (Shape s : shapes) {
            drawing.removeShape(s);
        }
        drawing.addShape(group);
    }

    /**
     * Adds the shapes to the drawing and removes the group from the drawing
     */
    @Override
    public void unexecute() {
        drawing.removeShape(group);
        for (Shape s : group.getShapes()) {
            drawing.addShape(s);
        }
    }
}
