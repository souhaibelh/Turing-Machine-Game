package g61610.atl.ascii.controller;

import g61610.atl.ascii.model.AsciiPaint;
import g61610.atl.ascii.model.AsciiPaintException;
import g61610.atl.ascii.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {
    private final AsciiPaint paint;

    /**
     * Tells if the drawing is over or no, used in the drawing loop.
     */
    private boolean drawing_over = false;

    private final View view;

    public Application(AsciiPaint paint, View view) {
        this.view = view;
        this.paint = paint;
    }

    /**
     * This method takes a matcher, extracts from it all the parameters needed to create a Rectangle, and creates one safely
     * by adding a try catch block around it. If the height and width are invalid displays an error in the console.
     * to say so.
     * @param rectangle_matcher matcher containing the x,y,height,width and character representing the color of the shape.
     */
    private void addRectangle(Matcher rectangle_matcher) {
        int x = Integer.parseInt(rectangle_matcher.group(1));
        int y = Integer.parseInt(rectangle_matcher.group(2));
        double height = Double.parseDouble(rectangle_matcher.group(3));
        double width = Double.parseDouble(rectangle_matcher.group(4));
        char shape_color = rectangle_matcher.group(5).charAt(0);
        try {
            this.paint.newRectangle(x,y,width,height,shape_color);
        } catch (AsciiPaintException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method takes a matcher, extracts from it all the parameters needed to create a Circle, and creates one safely
     * by adding a try catch block around it. If the radius is invalid displays an error in the console.
     * @param circle_square_matcher matcher containing the x,y,radius and character representing the color of the shape.
     */
    private void addCircleSquare(Matcher circle_square_matcher) {
        int x = Integer.parseInt(circle_square_matcher.group(2));
        int y = Integer.parseInt(circle_square_matcher.group(3));
        double radius_side = Double.parseDouble(circle_square_matcher.group(4));
        char shape_color = circle_square_matcher.group(5).charAt(0);
        try {
            if (circle_square_matcher.group(1).equals("square")) {
                this.paint.newSquare(x,y,radius_side,shape_color);
                System.out.println("squareadded");
            } else {
                this.paint.newCircle(x, y, radius_side, shape_color);
                System.out.println("circleadded");
            }
        } catch (AsciiPaintException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addLine(Matcher lineMatcher) {
        int x1 = Integer.parseInt(lineMatcher.group(1));
        int y1 = Integer.parseInt(lineMatcher.group(2));
        int x2 = Integer.parseInt(lineMatcher.group(3));
        int y2 = Integer.parseInt(lineMatcher.group(4));
        char color = lineMatcher.group(5).charAt(0);
        paint.newLine(x1,y1,x2,y2,color);
    }

    /**
     * This method takes a matcher, extracts from it the index, offset_x and offset_y in which to move the shape at the
     * index given and performs it. If the index is invalid it displays an error in the console.
     * @param move_matcher matcher containing the index, offset_x and offset_y.
     */
    private void moveShape(Matcher move_matcher) {
        int index = Integer.parseInt(move_matcher.group(1));
        int offset_x = Integer.parseInt(move_matcher.group(2));
        int offset_y = Integer.parseInt(move_matcher.group(3));
        try {
            this.paint.moveShape(index,offset_x,offset_y);
        } catch (AsciiPaintException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method takes a matcher, extracts from it the index and the color to set the Shape at the index given. If the
     * index is invalid it displays an error in the console.
     * @param color_matcher matcher containing the index and the new character representing the color.
     */
    private void changeColor(Matcher color_matcher) {
        int index = Integer.parseInt(color_matcher.group(1));
        char new_color = color_matcher.group(2).charAt(0);
        try {
            this.paint.changeColor(index,new_color);
        } catch (AsciiPaintException e) {
            System.out.println(e.getMessage());
        }
    }

    private void groupShapes(Matcher groupMatcher) {
        char color = groupMatcher.group(1).charAt(0);
        String[] indexes_string = groupMatcher.group(2).split(" ");
        int[] indexes = new int[indexes_string.length];
        for (int i=0; i<indexes.length; i++) {
            indexes[i] = Integer.parseInt(indexes_string[i]);
        }
        try {
            this.paint.group(color,indexes);
        } catch (AsciiPaintException e) {
            System.out.println(e.getMessage());
        }
    }

    private void ungroupShapes(Matcher ungroupMatcher) {
        paint.ungroup(Integer.parseInt(ungroupMatcher.group(1)));
    }

    /**
     * We accept one or multiple commands from the user, if at least one of the commands introduced is valid we loop
     * through the commands, we second check each command with a more precise Pattern, and we perform each one of them,
     * all the non-valid commands are added to a list that is displayed in the end to let the user know. If at least
     * one of the commands affects the visuals of the board (adding a shape, moving a shape, changing the color of a shape)
     * then we display the board after all commands are treated.
     * @param keyboard scanner to detect input
     */
    private void askInput(Scanner keyboard) {
        System.out.println("\nWhat is your next drawing little Picasso (if you have no idea what to type here type commands)");
        String commands = keyboard.nextLine();
        String[] commands_array = commands.split(",");

        Pattern rectangle = Pattern.compile("(?: ?)+add +rectangle +(-?[0-9+]+) +(-?[0-9+]+) +([0-9+]+) +([0-9+]+)" +
                " +([a-zA-Z])(?: ?)+");
        Pattern circle_square = Pattern.compile("(?: ?)+add +(circle|square) +(-?[0-9]+) +(-?[0-9]+) +([0-9]+) +([a-zA-Z])(?: ?)+");
        Pattern move = Pattern.compile("(?: ?)+move +([0-9]+) +(-?[0-9]+) +(-?[0-9]+)(?: ?)+");
        Pattern color = Pattern.compile("(?: ?)+color +([0-9]+) +([a-zA-Z])(?: ?)+");
        Pattern group = Pattern.compile("(?: ?)+group +([a-zA-Z]) +((?:[0-9]+ )+)(?: ?)+");
        Pattern ungroup = Pattern.compile("(?: ?)+ungroup +([0-9]+)(?: ?)+");
        Pattern line = Pattern.compile("(?: ?)+add +line +([0-9]+) +([0-9]+) +([0-9]+) +([0-9]+) +([a-zA-Z])(?: ?)+");

        List<String> invalid_commands = new ArrayList<>();
        boolean displayBoard = false;
        for (String c : commands_array) {
            Matcher circle_square_matcher = circle_square.matcher(c);
            Matcher rectangle_matcher = rectangle.matcher(c);
            Matcher move_matcher = move.matcher(c);
            Matcher color_matcher = color.matcher(c);
            Matcher group_matcher = group.matcher(c);
            Matcher ungroup_matcher = ungroup.matcher(c);
            Matcher line_matcher = line.matcher(c);

            if (c.contains("exit")) {
                this.drawing_over = true;
            } else if (c.contains("list")) {
                view.displayList(this.paint.getShapes());
            } else if (c.contains("show")) {
                view.displayBoard(this.paint);
            } else if (c.contains("commands")) {
                view.displayCommands();
            } else if (c.contains("undo")) {
                paint.undo();
                displayBoard = true;
            } else if (c.contains("redo")) {
                paint.redo();
                displayBoard = true;
            } else if (rectangle_matcher.matches()) {
                addRectangle(rectangle_matcher);
                displayBoard = true;
            } else if (circle_square_matcher.matches()) {
                addCircleSquare(circle_square_matcher);
                displayBoard = true;
            } else if (line_matcher.matches()) {
                addLine(line_matcher);
                displayBoard = true;
            } else if (move_matcher.matches()) {
                moveShape(move_matcher);
                displayBoard = true;
            } else if (color_matcher.matches()) {
                changeColor(color_matcher);
                displayBoard = true;
            } else if (group_matcher.matches()) {
                groupShapes(group_matcher);
                displayBoard = true;
            } else if (ungroup_matcher.matches()) {
                ungroupShapes(ungroup_matcher);
                displayBoard = true;
            } else {
                invalid_commands.add(c);
            }
        }
        if (displayBoard) view.displayBoard(this.paint);
        view.displayInvalidCommands(invalid_commands);
    }

    /**
     * As long as the game is not over, we keep asking for commands and perform them
     */
    public void start() {
        Scanner keyboard = new Scanner(System.in);
        while (!drawing_over) {
            askInput(keyboard);
        }
    }
}
