package g61610.atl.ascii.view;

import g61610.atl.ascii.model.AsciiPaint;
import g61610.atl.ascii.model.Point;
import g61610.atl.ascii.model.Shape;

public class View {
    public static void show(AsciiPaint paint) {
        for (int i=0; i<paint.getDrawing().getWidth(); i++) {
            for (int j=0; j<paint.getDrawing().getHeight(); j++){
                Point p = new Point(i,j);
                Shape currentshape = paint.getDrawing().getShapeAt(p);
                if (currentshape != null) {
                    System.out.print(" " + currentshape.getColor() + " ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
    }

    public static void displayCommands() {
        System.out.println("\nGAME COMMANDS:");
        System.out.println("\nIMPORTANT*: For a circle, the x and y coordinates are meant to describe the" +
                " center of the circle, for the rectangle and square they describe\nthe upper left point");
        System.out.println("\nADD SHAPE COMMANDS:");
        System.out.println("\n    -add circle command: add circle x y radius color");
        System.out.println("    -add rectangle command: add rectangle x y width height color");
        System.out.println("    -add square command: add square x y side color");
        System.out.println("\nOTHER COMMANDS:");
        System.out.println("\n    -show command: show <- this command displays the board");
        System.out.println("    -list command: list <- this command shows a ordered list of all the shapes in the board");
        System.out.println("    -color command: color i z <- this command replaces the color of the shape at the index i for the color z");
        System.out.println("    -move i a b <- this command moves the shape at the index i by an offset of a in the x axis and an offset of b in the y axis");
        System.out.println("    -exit <- exits the game and brings you back to the first menu\n");
    }
}
