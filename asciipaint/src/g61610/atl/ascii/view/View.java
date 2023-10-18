package g61610.atl.ascii.view;

import g61610.atl.ascii.model.*;

import java.util.List;

public class View {

    /**
     * This method here displays the board, it adds a line of "-" in the top and the bottom of the board, it iterates
     * through the board with width and height, creates a point with each width and height combination and calls the method
     * getShapeAt(Point p) with the created point, if the method returns null it means there isn't any shape, and we only
     * display 3 spaces, if the shape is not null then we display a space, the shape color (1 character) and another space.
     * @param paint this is the controller
     */
    public void displayBoard(AsciiPaint paint) { // pas de static !
        for (int i=0; i<paint.getDrawing().getWidth(); i++) {
            for (int j=0; j<paint.getDrawing().getHeight(); j++) {
                Point p = new Point(j,i); // reversed for drawing purposes
                Shape shape_has_p = paint.getDrawing().getShapeAt(p);
                if (shape_has_p != null) {
                    System.out.print(" " + shape_has_p.getColor() + " ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
    }

    /**
     * This method displays the list of shapes containing all available shapes in our drawing, if the list is empty we say so,
     * if it isn't empty we display the index of the shape followed by its name and color.
     * @param shapeList list containing all available shapes in our drawing
     */
    public void displayList(List<Shape> shapeList) {
        if (shapeList.isEmpty()) {
            System.out.println("\nYour drawing board is empty! Add new shapes first (type commands if you don't know how to)");
        } else {
            System.out.println();
            for (int i=0; i<shapeList.size(); i++) {
                String shapename;
                if (shapeList.get(i) instanceof Circle) {
                    shapename = "Circle";
                } else if (shapeList.get(i) instanceof Square) {
                    shapename = "Square";
                } else {
                    shapename = "Rectangle";
                }
                System.out.println(i + ". Shape: " + shapename + "-> Color: " + shapeList.get(i).getColor());
            }
            System.out.println();
        }
    }

    /**
     * This method outputs all the commands that the user attempted to add but weren't following the syntax or respecting
     * the parameters of each command.
     * @param invalid_commands list containing all the commands that were invalid when the user passed in a commands input
     */
    public void displayInvalidCommands(List<String> invalid_commands) {
        if (invalid_commands.isEmpty()) {
            System.out.println("\nAll commands executed successfully");
        } else {
            System.out.println("\nEncountered an issue while attempting to execute the following commands:\n");
            for (int i=0; i<invalid_commands.size(); i++) {
                System.out.println((i + 1) + ". " + invalid_commands.get(i));
            }
        }
    }

    /**
     * This method is purely to display the commands the user can use on the drawing board to draw shapes, modify them
     * or exit the drawing board.
     */
    public void displayCommands() {
        System.out.println("\nGAME COMMANDS:");
        System.out.println("""

                IMPORTANT*: For a circle, the x and y coordinates are meant to describe the center of the circle, for the rectangle and square they describe
                the upper left point""");
        System.out.println("\nMULTIPLE COMMANDS:");
        System.out.println("\n    -You can add multiple commands in one line (any command accepted, included but not limited to: show,list,move,color...), separate each command by a coma, for example:");
        System.out.println("    -add rectangle 15 15 15 15 D, add square 15 15 10 D, move 10 10 10, add circle 15 10 5 D");
        System.out.println("\nADD SHAPE COMMANDS:");
        System.out.println("\n    -add circle command: add circle x y radius color");
        System.out.println("    -add rectangle command: add rectangle x y width height color");
        System.out.println("    -add square command: add square x y side color");
        System.out.println("\nOTHER COMMANDS:");
        System.out.println("\n    -show command: show <- this command displays the board");
        System.out.println("    -list command: list <- this command shows a ordered list of all the shapes in the board");
        System.out.println("    -color command: color i z <- this command replaces the color of the shape at the index i for the color z");
        System.out.println("    -move i a b <- this command moves the shape at the index i by an offset of 'a' in the x axis and an offset of 'b' in the y axis (example: move 0 5 5, moves the shape at the index 0" +
                "in the List of shapes inside ur drawing board (first shape added) by 5 to the right and by 5 down, if we had used move 0 5 -5 we would have moved the shape up)");
        System.out.println("    -exit <- exits the game and brings you back to the first menu\n");
    }
}
