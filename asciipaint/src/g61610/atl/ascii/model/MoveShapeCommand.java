package g61610.atl.ascii.model;

import g61610.atl.ascii.model.Command;
import g61610.atl.ascii.model.Shape;

public class MoveShapeCommand implements Command {
    private final Shape shape;
    private final double dx;
    private final double dy;

    MoveShapeCommand(Shape shape, double dx, double dy) {
        this.shape = shape;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute() {
        shape.move(dx,dy);
    }

    @Override
    public void unexecute() {
        shape.move(-dx,-dy);
    }
}
