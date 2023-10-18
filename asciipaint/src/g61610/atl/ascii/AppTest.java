package g61610.atl.ascii;

import g61610.atl.ascii.model.*;
import g61610.atl.ascii.model.Point;
import g61610.atl.ascii.model.Rectangle;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    /**
     * This securityTests method will test if the references of the points used to create the shapes are different (defensive copy), will also test that the getDrawing method inside AsciiPaint
     * returns a different drawing with the same characteristics as the one the AsciiPaint has as an attribute. Finally, it also tests that the getShapes() method gives an unmodifiable copy of the
     * real List inside Drawing. These tests confirm our project is secure (at least against references attacks/changes).
     */
    @Test
    public void securityTests() {
        // We make a point that will be used as the center of the circle
        Point circle_center = new Point(10,10);

        // We make a circle using the circle_center point as the center (the constructor will use a different point with same coordinates! Thanks to the default constructor by class Point)
        Circle circle = new Circle(circle_center,5,'A');

        /*
            To verify that the circle has a different point made with the same attributes as the one we introduced in as an argument (circle_center) we will move the Circle and see if our circle_center point gets
            moved too, it shouldn't since we have a defensive copy.
         */
        circle.move(10,10); // We move the circle by 10, which means it's center now has coordinates of: 10+10,10+10 so: 20,20
        assertNotEquals(20, circle_center.getX()); // We confirm the X coordinate of the circle_center point is not 20 as it should be if it was the one the Circle class uses as a center
        assertNotEquals(20, circle_center.getY()); // We confirm the Y coordinate of the circle_center point is not 20 as it should be if it was the one the Circle class uses as a center

        // We make a AsciiPaint instance
        AsciiPaint paint = new AsciiPaint();

        // We make an instance of Drawing that will be referenced to the Drawing returned by the AsciiPaint method getDrawing()
        Drawing drawing = paint.getDrawing();

        // We add a shape to the drawing instance
        Square fake_shape = new Square(new Point(10,10), 5, 'A');

        /*
            This throws an error because when we do drawing = paint.getDrawing(), the method inside the AsciiPaint that is defined as getDrawing() returns a whole different Drawing that has the same length and
            width but the List it uses as shapes is a ImmutableCopy of the original List (which means we can't modify it, if we attempt to by adding a shape it throws a UnsupportedOperationException), it can
            return this different drawing because the Drawing class has a defensive copy constructor that makes sure to use a ImmutableCopy of the List as the new List of the new Drawing.
         */
        assertThrows(UnsupportedOperationException.class, () -> {drawing.addShape(fake_shape);});
    }

    /**
     * This inputTests method will test if an input is correct, if it isn't it will also test for getting a AsciiException.
     */
    @Test
    public void inputTests() {
        // We make a AsciiPaint instance, and we add to it a rectangle
        AsciiPaint board = new AsciiPaint();
        board.newRectangle(0,0,5,10,'D');

        /*
            The List of shapes of the Drawing board should have 1 shape (we added a rectangle above), we have 2 methods that allow us to access a copy of the Shapes inside the List, the move method and the
            color method, we will look for a AsciiPaint exception when we launch a move command or a color command with an index that is bigger than the size - 1, in this case 0, and smaller than 0.
         */
        assertThrows(AsciiPaintException.class, () -> board.moveShape(10,5,5)); // Attempting to access a non-existent shape with the move method (index > size of List, in this case 0)
        assertThrows(AsciiPaintException.class, () -> board.moveShape(-10,5,5)); // Attempting to access a non-existent shape with the move method (index < 0)
        assertThrows(AsciiPaintException.class, () -> board.changeColor(10,'A')); // Attempting to access a non-existent shape with the color method (index > size of List, in this case 0)
        assertThrows(AsciiPaintException.class, () -> board.changeColor(-10,'S')); // Attempting to access a non-existent shape with the color method (index < 0)
    }

    /**
     * This here tests the setColor command verifying it actually does change the Color, the move method is tested thoroughly from now till the end of the testing class
     */
    @Test
    public void colorTest() {
        Square square = new Square(new Point(10,10), 5, 'A');
        square.setColor('B');
        assertSame(square.getColor(),'B');
    }

    /**
     * We verify the rectangle is the shape from upper_left_x to rectangle_width (0,1,2...rectangle_width) both vertically and horizontally (we use rectangle_height instead of rectangle_width and upper_left_y
     * instead of upper_left_x), we use 0 to rectangle_width and 0 to rectangle_height because the width of the rectangle is rectangle_width and height is rectangle_height, so the shape we get from 0 to there
     * should be the same, our rectangle. (We do this by using the assertSame method).
     * <p>
     * We also verify that the Rectangle actually contains the points it should contain by using the assertTrue method.
     * @param rectangle_drawing the drawing containing the rectangle
     * @param upper_left_x x-coordinate of the upperPoint of the Rectangle
     * @param upper_left_y y-coordinate of the upperPoint of the Rectangle
     * @param rectangle_width rectangle's width
     * @param rectangle_height rectangle's height
     * @param r1 rectangle
     */
    private void rectangleInRigthPlace(Drawing rectangle_drawing, double upper_left_x, double upper_left_y, double rectangle_width, double rectangle_height, Rectangle r1) {
        for (double i=upper_left_x; i<rectangle_width; i++) {
            for (double j=upper_left_y; j<rectangle_height; j++) {
                assertTrue(r1.isInside(new Point(i,j)));
                assertSame(r1, rectangle_drawing.getShapeAt(new Point(i,j)));
            }
        }
    }

    /**
     * This method here tests that a rectangle gets added correctly to a Drawing board, it also verifies the rectangle actually takes his place and after moving it we verify again that his place moved too in the
     * drawing board.
     */
    @Test
    public void testSquareRectangle() {
        // We make a drawing board of dimensions: 200x200
        Drawing rectangle_drawing = new Drawing(200,200);

        // We make a rectangle and add it inside the drawing board.
        double upper_left_x = 0.0;
        double upper_left_y = 0.0;
        double rectangle_width = 5;
        double rectangle_height = 6;
        Rectangle r1 = new Rectangle(new Point(upper_left_x,upper_left_y),rectangle_width,rectangle_height,'D');
        rectangle_drawing.addShape(r1);

        // We verify the rectangle got added correctly
        assertSame(r1, rectangle_drawing.getShapes().get(0));

        // read javadoc of this method
        rectangleInRigthPlace(rectangle_drawing, upper_left_x, upper_left_y, rectangle_width, rectangle_height, r1);

        // We move the shape
        int x_offset = 5;
        int y_offset = 5;
        r1.move(x_offset,y_offset);

        // We update the rectangle's upperLeft point coordinates, if it moved correctly they should be different now, let's do that manually as we don't have a getter in the Rectangle class
        upper_left_x += x_offset;
        upper_left_y += y_offset;

        // We perform the same test as above to verify that after moving the shape it is still being detected in the right position
        rectangleInRigthPlace(rectangle_drawing, upper_left_x, upper_left_y, rectangle_width, rectangle_height, r1);
    }

    /**
     * This method here tests that a Circle gets added correctly to the drawing board, we verify the center is rightly added, and we verify that the Shape actually moves by moving it and seeing if where the center
     * was supposed to be is still there, to verify this and make sure its right we move the circle by special values explained below in the method.
     */
    @Test
    public void testCircle() {
        // We make a drawing board of dimensions: 200x200
        Drawing circle_drawing = new Drawing(200,200);

        // We make a Circle and add it inside the drawing board.
        double center_x = 0.0;
        double center_y = 0.0;
        double radius = 5;
        Point circle_center = new Point(center_x,center_y);
        Circle c1 = new Circle(circle_center,radius,'D');
        circle_drawing.addShape(c1);

        // We verify the circle got added correctly
        assertSame(c1, circle_drawing.getShapes().get(0));

        // We verify the coordinate 0.0,0.0 (specified by the circle_center variable) is the center of the circle (the shape at that point is the circle)
        assertSame(c1, circle_drawing.getShapeAt(circle_center));

        // We move the by its radius both right and down
        c1.move(radius,radius);

        // After moving the circle by its radius length both right and down we confirm that the center is no longer the same circle_center variable (this is very normal since in the constructor of the Circle we
        // call a defensive copy constructor of Point that takes in the Point we pass in as parameters for safety issues, this was tested before, which means the point we used to create the circle didn't get moved).
        assertNotSame(c1, circle_drawing.getShapeAt(circle_center));

        // Finally we verify that the center contains the point that is supposedly the center
        Point new_center = new Point(center_x + radius, center_y + radius);
        assertSame(c1, circle_drawing.getShapeAt(new_center));
    }
}