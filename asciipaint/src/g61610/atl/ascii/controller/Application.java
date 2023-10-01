package g61610.atl.ascii.controller;

import g61610.atl.ascii.model.*;
import g61610.atl.ascii.view.View;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {
    private AsciiPaint paint;
    private boolean drawing_over = false;
    private boolean application_on = true;

    private void prepareBoard(Scanner keyboard) {
        System.out.println("\nIntroduce the height and width of the drawing board like this: width height (or type in default (or simply d), for default size)");
        keyboard.nextLine(); // Clear scanner buffer
        String board_dimensions = keyboard.nextLine();

        Pattern dimensions = Pattern.compile("([0-9]+) ([0-9]+)|(default|d)");
        while (!dimensions.matcher(board_dimensions).find()) {
            System.out.println("Invalid syntax, use one of the following:\n- default or d : for default length\n- height width : for custom height and width");
            board_dimensions = keyboard.nextLine();
        }

        if (board_dimensions.charAt(0) == 'd') {
            paint = new AsciiPaint();
        } else {
            Matcher dimensions_matcher = dimensions.matcher(board_dimensions);
            if (dimensions_matcher.find()) {
                this.paint = new AsciiPaint(Integer.parseInt(dimensions_matcher.group(1)),Integer.parseInt(dimensions_matcher.group(2)));
            }
        }
    }

    private static String fixString(String move) {
        if (move.charAt(move.length()-1) != ',') {
            move += ",";
        }
        return move;
    }

    private void Input(Scanner keyboard) {
        System.out.println("\nWhat is your next move, Picasso! (If you are confused, type commands to get a " +
                " list of everything you can do!)");
        String move = keyboard.nextLine();
        move = fixString(move);

        Pattern commands = Pattern.compile("(?: +)?(add rectangle ([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+) ([a-zA-Z])|" +
                "add square ([0-9]+) ([0-9]+) ([0-9]+) ([a-zA-Z]+)|add circle ([0-9]+) ([0-9]+) ([0-9]+) ([a-zA-Z]+)|move " +
                "([0-9]+) -?([0-9]+) -?([0-9]+)|color ([0-9]+) ([a-zA-Z]+)|exit|commands|list|show)(?: +)?,+");
        while (!commands.matcher(move).find()) {
            System.out.println("not valid input, put new one");
            move = keyboard.nextLine();
            move = fixString(move);
        }

        Matcher matcher = commands.matcher(move);
        while (matcher.find()) {
            if (matcher.group().contains("rectangle")) {
                int x = Integer.parseInt(matcher.group(2));
                int y = Integer.parseInt(matcher.group(3));
                double width = Double.parseDouble(matcher.group(5));
                double height = Double.parseDouble(matcher.group(4));
                char color = matcher.group(6).charAt(0);
                this.paint.newRectangle(x,y,width,height,color);
                View.displayBoard(this.paint);
            } else if (matcher.group().contains("circle")) {
                int x = Integer.parseInt(matcher.group(11));
                int y = Integer.parseInt(matcher.group(12));
                double radius = Double.parseDouble(matcher.group(13));
                char color = matcher.group(14).charAt(0);
                this.paint.newCircle(x,y,radius,color);
                View.displayBoard(this.paint);
            } else if (matcher.group().contains("square")) {
                int x = Integer.parseInt(matcher.group(7));
                int y = Integer.parseInt(matcher.group(8));
                double side = Double.parseDouble(matcher.group(9));
                char color = matcher.group(10).charAt(0);
                this.paint.newSquare(x,y,side,color);
                View.displayBoard(this.paint);
            } else if (matcher.group().contains("move")) {
                int index = Integer.parseInt(matcher.group(15));
                int offsetX = Integer.parseInt(matcher.group(16));
                int offsetY = Integer.parseInt(matcher.group(17));
                try {
                    this.paint.moveShape(index,offsetX,offsetY);
                } catch (AsciiPaintException e) {
                    System.out.println(e.getMessage());
                }
            } else if (matcher.group().contains("color")) {
                int index = Integer.parseInt(matcher.group(18));
                char newcolor = matcher.group(19).charAt(0);
                try {
                    this.paint.changeColor(index,newcolor);
                } catch (AsciiPaintException e) {
                    System.out.println(e.getMessage());
                }
                View.displayBoard(this.paint);
            } else if (matcher.group().contains("commands")) {
                View.displayCommands();
            } else if (matcher.group().contains("show")) {
                View.displayBoard(this.paint);
            } else if (matcher.group().contains("list")) {
                View.displayList(this.paint.getShapes());
            } else if (matcher.group().contains("exit")) {
                this.drawing_over = true;
            }
        }
    }

    public void start() {
        Scanner keyboard = new Scanner(System.in);
        while (this.application_on) {
            System.out.println("WELCOME to ASCIIPAINT\n1. Start drawing\n2. exit");
            int menu_option = keyboard.nextInt();
            while (menu_option != 1 && menu_option != 2) {
                System.out.println("Invalid option, press 1 to start a drawing board or 2 to exit");
                menu_option = keyboard.nextInt();
            }
            if (menu_option == 1) {
                prepareBoard(keyboard);
                while (!this.drawing_over) {
                    Input(keyboard);
                }
            } else {
                this.application_on = false;
            }
        }
    }
}
