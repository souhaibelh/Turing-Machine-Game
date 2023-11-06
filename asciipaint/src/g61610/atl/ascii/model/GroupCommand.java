package g61610.atl.ascii.model;

public class GroupCommand implements Command {
    private final Shape[] shapes;
    private final Group group;
    private final Drawing drawing;

    GroupCommand(char color, Drawing drawing, Shape... shape) {
        this.shapes = shape;
        this.drawing = drawing;
        group = new Group(color);
    }

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

    @Override
    public void unexecute() {
        drawing.removeShape(group);
        for (Shape s : group.getShapes()) {
            drawing.addShape(s);
        }
    }
}
