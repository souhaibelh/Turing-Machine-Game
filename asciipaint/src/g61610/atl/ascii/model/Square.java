package g61610.atl.ascii.model;

public class Square extends Rectangle {

    public Square(Point upperLeft, double side, char color) {
        super(upperLeft, upperLeft.getX() + side, upperLeft.getX() + side, color);
    }
}
