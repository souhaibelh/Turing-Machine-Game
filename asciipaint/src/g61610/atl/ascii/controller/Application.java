package g61610.atl.ascii.controller;

import g61610.atl.ascii.model.AsciiPaint;
import g61610.atl.ascii.model.AsciiPaintException;
import g61610.atl.ascii.view.View;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {
    private AsciiPaint paint;
    private boolean drawing_over = false;
    private boolean application_on = true;

    private void startBoard(Scanner keyboard) {
        System.out.println("\nIntroduce the dimensions of your board in this format: width height or type in default or d for default size");
        keyboard.nextLine(); // clear buffer
        String dimensions = keyboard.nextLine();
        Pattern dimensions_pattrn = Pattern.compile("^([0-9]+) ([0-9]+)|default|d$");
        while (!dimensions_pattrn.matcher(dimensions).matches()) {
            System.out.println("\nInvalid format, follow this example: '200 150' (200 width 150 height) or 'default' (default board size) or 'd' (default board size)");
            dimensions = keyboard.nextLine();
        }

        if (dimensions.charAt(0) == 'd') {
            this.paint = new AsciiPaint();
        } else {
            Matcher dimension_pattrn_matcher = dimensions_pattrn.matcher(dimensions);
            if (dimension_pattrn_matcher.find()) {
                this.paint = new AsciiPaint(Integer.parseInt(dimension_pattrn_matcher.group(1)),Integer.parseInt(dimension_pattrn_matcher.group(2)));
            }
        }
    }

    private String fixString(String action) {
        return action.charAt(action.length() - 1) == ',' ? action : action + ",";
    }

    private void askInput(Scanner keyboard) {
        System.out.println("\nWhat is your next drawing little Picasso (if you have no idea what to type here type commands)");
        String action = keyboard.nextLine();
        action = fixString(action);

        Pattern action_pattrn = Pattern.compile("(?: +)?(add rectangle ([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+) ([a-zA-Z])|add square ([0-9]+) ([0-9]+) ([0-9]+) ([a-zA-Z]+)|add circle ([0-9]+) ([0-9]+) ([0-9]+) ([a-zA-Z]+)|move ([0-9]+) -?([0-9]+) -?([0-9]+)|color ([0-9]+) ([a-zA-Z]+)|exit|commands|list|show)(?: +)?,+");
        while (!action_pattrn.matcher(action).find()) {
            System.out.println("\nInvalid action, type 'commands' to see everything you can do");
            action = keyboard.nextLine();
        }

        Matcher action_pattrn_matcher = action_pattrn.matcher(action);
        while (action_pattrn_matcher.find()) {
            if (action_pattrn_matcher.group().contains("rectangle")) {
                int x = Integer.parseInt(action_pattrn_matcher.group(2));
                int y = Integer.parseInt(action_pattrn_matcher.group(3));
                double width = Double.parseDouble(action_pattrn_matcher.group(5));
                double height = Double.parseDouble(action_pattrn_matcher.group(4));
                char color = action_pattrn_matcher.group(6).charAt(0);
                this.paint.newRectangle(x,y,width,height,color);
                View.displayBoard(this.paint);
            } else if (action_pattrn_matcher.group().contains("square")) {
                int x = Integer.parseInt(action_pattrn_matcher.group(7));
                int y = Integer.parseInt(action_pattrn_matcher.group(8));
                double side = Double.parseDouble(action_pattrn_matcher.group(9));
                char color = action_pattrn_matcher.group(10).charAt(0);
                this.paint.newSquare(x,y,side,color);
                View.displayBoard(this.paint);
            } else if (action_pattrn_matcher.group().contains("circle")) {
                int x = Integer.parseInt(action_pattrn_matcher.group(11));
                int y = Integer.parseInt(action_pattrn_matcher.group(12));
                double radius = Double.parseDouble(action_pattrn_matcher.group(13));
                char color = action_pattrn_matcher.group(14).charAt(0);
                this.paint.newCircle(x,y,radius,color);
                View.displayBoard(this.paint);
            } else if (action_pattrn_matcher.group().contains("move")) {
                int index = Integer.parseInt(action_pattrn_matcher.group(15));
                int x = Integer.parseInt(action_pattrn_matcher.group(16));
                int y = Integer.parseInt(action_pattrn_matcher.group(17));
                try {
                    this.paint.moveShape(index,x,y);
                } catch (AsciiPaintException e) {
                    System.out.println(e.getMessage());
                }
            } else if (action_pattrn_matcher.group().contains("color")) {
                int index = Integer.parseInt(action_pattrn_matcher.group(18));
                char color = action_pattrn_matcher.group(19).charAt(0);
                try {
                    this.paint.changeColor(index,color);
                } catch (AsciiPaintException e) {
                    System.out.println(e.getMessage());
                }
            } else if (action_pattrn_matcher.group().contains("exit")) {
                this.drawing_over = true;
            } else if (action_pattrn_matcher.group().contains("commands")) {
                View.displayCommands();
            } else if (action_pattrn_matcher.group().contains("list")) {
                View.displayList(this.paint.getShapes());
            } else if (action_pattrn_matcher.group().contains("show")) {
                View.displayBoard(this.paint);
            }
        }
    }

    public void start() {
        Scanner keyboard = new Scanner(System.in);
        while (application_on) {
            System.out.println("WELCOME TO ASCIIPAINT");
            System.out.println("1. Start drawing");
            System.out.println("2. Exit");
            int menu_option = keyboard.nextInt();
            while (menu_option != 2 && menu_option != 1) {
                System.out.println("\nInvalid option, press 1 to start a new drawing board or 2 to exit the application");
                menu_option = keyboard.nextInt();
            }
            if (menu_option == 1) {
                startBoard(keyboard);
                while (!drawing_over) {
                    askInput(keyboard);
                }
            } else {
                this.application_on = false;
            }
        }
    }
}
