package g61610.atl.ascii.view;

import g61610.atl.ascii.model.AsciiPaint;
import g61610.atl.ascii.model.Point;
import g61610.atl.ascii.model.Rectangle;
import g61610.atl.ascii.model.Shape;

import java.util.ArrayList;
import java.util.List;

public class View {
    public static void show(AsciiPaint paint) {
        List<Integer> rowhasshape = new ArrayList<Integer>();
        List<Integer> colhasshape = new ArrayList<Integer>();
        for (int i=0; i < paint.getDrawing().getWidth(); i++) {
            for (int j=0; j < paint.getDrawing().getHeight(); j++) {
                if (paint.getDrawing().getShapeAt(new Point(i,j)) != null) {
                    rowhasshape.add(j);
                    colhasshape.add(i);
                }
            }
        }
        int maxx = colhasshape.get(0);
        int minx = colhasshape.get(0);
        int maxy = rowhasshape.get(0);
        int miny = rowhasshape.get(0);

        for (int i=0; i<rowhasshape.size(); i++) {
            if (rowhasshape.get(i) > maxy) {
                maxy = rowhasshape.get(i);
            } else if (rowhasshape.get(i) < miny) {
                miny = rowhasshape.get(i);
            }
        }

        for (int i=0; i<colhasshape.size(); i++) {
            if (colhasshape.get(i) > maxx) {
                maxx = colhasshape.get(i);
            } else if (colhasshape.get(i) < minx) {
                minx = colhasshape.get(i);
            }
        }

        for (int i=minx; i<=maxx; i++) {
            for (int j=miny; j<=maxy; j++){
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
        for (int i=minx; i<maxx; i++) {
            System.out.println(" ");
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

    public static void main(String[] args) {
        Rectangle rct = new Rectangle(new Point(5,5),5,5,'c');
        System.out.println(rct.getClass().getName());
    }
}
