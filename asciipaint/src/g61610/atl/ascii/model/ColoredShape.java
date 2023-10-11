package g61610.atl.ascii.model;

/**
 * Abstract class that defines the contract of all shapes. This means all shapes will have a char that will represent the color
 * of the shape, a constructor that will set their color to the one passed in their constructors and 2 methods, one to get the color
 * of the shape and one to change the color of the shape.
 */
public abstract class ColoredShape implements Shape {
    private char color;

    public ColoredShape(char color) {
        this.color = color;
    }

    /**
     * @return the character that represents the color of a shape
     */
    public char getColor() {
        return color;
    }

    /**
     * Sets the color of a shape to the new color passed in as a parameter.
     * @param color the new color of the shape
     */
    public void setColor(char color) {
        this.color = color;
    }
}
