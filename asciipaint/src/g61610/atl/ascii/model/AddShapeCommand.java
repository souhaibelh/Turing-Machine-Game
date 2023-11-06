package g61610.atl.ascii.model;

public class AddShapeCommand implements Command {
    private final Shape shape;
    private final Drawing drawing;
    
    AddShapeCommand(Drawing drawing, Shape shape) {
        this.shape = shape;
        this.drawing = drawing;
    }

    @Override
    public void execute() {
        this.drawing.addShape(shape);
    }

    @Override
    public void unexecute() {
        this.drawing.removeShape(shape);
    }
}
