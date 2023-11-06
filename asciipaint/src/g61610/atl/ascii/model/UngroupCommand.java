package g61610.atl.ascii.model;

public class UngroupCommand implements Command {
    private final Drawing drawing;
    private final Group group;

    public UngroupCommand(Drawing drawing, Group group) {
        this.drawing = drawing;
        this.group = group;
    }

    @Override
    public void execute() {
        drawing.removeShape(group);
        for (Shape shape : group.getShapes()) {
            drawing.addShape(shape);
        }
    }

    @Override
    public void unexecute() {
        drawing.addShape(group);
        for (Shape shape : group.getShapes()) {
            drawing.removeShape(shape);
        }
    }
}
