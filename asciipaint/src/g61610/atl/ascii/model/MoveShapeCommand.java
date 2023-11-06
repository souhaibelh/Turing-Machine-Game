package g61610.atl.ascii.model;

import g61610.atl.ascii.model.Command;
import g61610.atl.ascii.model.Shape;

/**
 * Defines the move shape command, has 3 attributes, the shape to move, the x offset and the y offset
 */
public class MoveShapeCommand implements Command {
    private final Shape shape;
    private final double dx;
    private final double dy;

    /**
     * Constructor of the move shape command
     * @param shape shape to move
     * @param dx x offset to move the shape
     * @param dy y offset to move the shape
     */
    MoveShapeCommand(Shape shape, double dx, double dy) {
        this.shape = shape;
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Moves the shape
     */
    @Override
    public void execute() {
        shape.move(dx,dy);
    }

    /**
     * Reverses the move
     */
    @Override
    public void unexecute() {
        shape.move(-dx,-dy);
    }
}
