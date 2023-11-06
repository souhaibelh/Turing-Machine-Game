package g61610.atl.ascii.model;

public class ChangeColorCommand implements Command {
    private final Shape shape;
    private final char old_color;
    private final char new_color;

    public ChangeColorCommand(Shape shape, char new_color) {
        this.shape = shape;
        this.old_color = shape.getColor();
        this.new_color = new_color;
    }

    @Override
    public void execute() {
        shape.setColor(new_color);
    }

    @Override
    public void unexecute() {
        shape.setColor(old_color);
    }
}
