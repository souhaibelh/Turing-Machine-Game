package g61610.atl.ascii.tdinheritancepolymorphisme;

public class ColoredPoint extends Point {
    private int color;

    public ColoredPoint(double x, double y, int color) {
        super(x,y);
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + String.format("%08X", color);
    }
}