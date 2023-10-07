package g61610.atl.ascii.model;

/**
 * This class here only consists of a constructor, every other method or attribute we can just use the parent's, since a
 * square is a rectangle, methods to calculate if a point is inside etc. are the same.
 */

public class Square extends Rectangle {

    /**
     *  The constructor takes in one side and calls the parent constructor with the same side as height and width, this is because
     *  a square is a rectangle which both sides are equal.
     * @param upperLeft upper left point of the square
     * @param side length of square's side
     * @param color the color of the square
     */
    public Square(Point upperLeft, double side, char color) {
        super(upperLeft, side, side, color);
    }
}
