package g61610.atl.ascii.model;

public abstract class ColoredShape implements Shape {
    private char color;

    public ColoredShape(char color) {
        this.color = color;
    }

    public char getColor() {
        return this.color;
    }

    public void setColor(char color) {
        this.color = color;
    }
}
