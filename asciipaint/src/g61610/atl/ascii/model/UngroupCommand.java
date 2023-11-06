package g61610.atl.ascii.model;

/**
 * Defines the ungroup command, has 2 attributes, the drawing from which to ungroup a group and the group to ungroup
 */
public class UngroupCommand implements Command {
    private final Drawing drawing;
    private final Group group;

    /**
     * Constructor of the ungroup command
     * @param drawing drawing where the group we want to ungroup resides
     * @param group group to ungroup
     */
    public UngroupCommand(Drawing drawing, Group group) {
        this.drawing = drawing;
        this.group = group;
    }

    /**
     * Ungroups the group
     */
    @Override
    public void execute() {
        drawing.removeShape(group);
        for (Shape shape : group.getShapes()) {
            drawing.addShape(shape);
        }
    }

    /**
     * Regroups the group
     */
    @Override
    public void unexecute() {
        drawing.addShape(group);
        for (Shape shape : group.getShapes()) {
            drawing.removeShape(shape);
        }
    }
}
