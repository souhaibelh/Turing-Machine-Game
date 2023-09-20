package g61610.atl.ascii.model;

public interface Shape {
    boolean isInside(Point p);
    void move(double dx, double dy);
    char getColor();
    void setColor(char c);
}
