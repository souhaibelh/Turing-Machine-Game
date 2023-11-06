package g61610.atl.ascii.model;

public class RemoveShapeCommand implements Command {
    private final Shape shape;
    private final Drawing drawing;

    RemoveShapeCommand(Shape shape, Drawing drawing) {
        this.shape = shape;
        this.drawing = drawing;
    }

    @Override
    public void execute() {
        drawing.removeShape(shape);
    }

    @Override
    public void unexecute() {
        drawing.addShape(shape);
    }
}
