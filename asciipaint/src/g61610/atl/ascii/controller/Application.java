package g61610.atl.ascii.controller;

import g61610.atl.ascii.model.AsciiPaint;
import g61610.atl.ascii.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {
    private AsciiPaint paint;
    private boolean drawing_over = false;
    private boolean application_on = true;

    private void startBoard(Scanner keyboard) {
        System.out.println("\nIntroduce the dimensions of your board in this format: width height or type in default or d for default size");
        String dimensions = keyboard.nextLine();
        Pattern dimensions_pattrn = Pattern.compile("^(([0-9]+) ([0-9]+)|default|d)$");
        while (!dimensions_pattrn.matcher(dimensions).matches()) {
            System.out.println("\nInvalid format, follow this example: '200 150' (200 width 150 height) or 'default' (default board size) or 'd' (default board size)");
            dimensions = keyboard.nextLine();
        }
        Matcher dimensions_pattrn_matcher = dimensions_pattrn.matcher(dimensions);
        if (dimensions_pattrn_matcher.find()) {
            switch (dimensions_pattrn_matcher.group(1)) {
                case "d":
                case "default":
                    this.paint = new AsciiPaint();
                    break;
                default:
                    int width = Integer.parseInt(dimensions_pattrn_matcher.group(2));
                    int height = Integer.parseInt(dimensions_pattrn_matcher.group(3));
                    this.paint = new AsciiPaint(width,height);
                    break;
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

        Pattern action_pattrn = Pattern.compile("(add rectangle|add square|add circle|move|color|exit|show|list|" +
                "commands) ?(-?[0-9]+)? ?(-?[0-9]+)? ?(-?[0-9]+)? ?(-?[0-9]+)? ?([a-zA-Z])?,");

        while (!action_pattrn.matcher(action).find()) {
            System.out.println("\nInvalid action, type 'commands' to see everything you can do");
            action = keyboard.nextLine();
            action = fixString(action);
        }

        List<String> not_added_commands = new ArrayList<>();
        Matcher action_pattrn_matcher = action_pattrn.matcher(action);
        while (action_pattrn_matcher.find()) {
            switch (action_pattrn_matcher.group(1)) {
                case "add rectangle":
                    if (action_pattrn_matcher.group().matches("add rectangle ([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+) ([a-zA-Z]),")) {
                        addRectangle(action_pattrn_matcher);
                        View.displayBoard(this.paint);
                    } else {
                        not_added_commands.add("add rectangle");
                    }
                    break;
                case "add square":
                    if (action_pattrn_matcher.group().matches("add square ([0-9]+) ([0-9]+) ([0-9]+) ([a-zA-Z]),")) {
                        addSquare(action_pattrn_matcher);
                        View.displayBoard(this.paint);
                    } else {
                        not_added_commands.add("add square");
                    }
                    break;
                case "add circle":
                    if (action_pattrn_matcher.group().matches("add circle ([0-9]+) ([0-9]+) ([0-9]+) ([a-zA-Z]),")) {
                        addCircle(action_pattrn_matcher);
                        View.displayBoard(this.paint);
                    } else {
                        not_added_commands.add("add circle");
                    }
                    break;
                case "move":
                    if (action_pattrn_matcher.group().matches("move ([0-9]+) (-?[0-9]+) (-?[0-9]+),")) {
                        move(action_pattrn_matcher);
                    } else {
                        not_added_commands.add("move");
                    }
                    break;
                case "color":
                    if (action_pattrn_matcher.group().matches("color ([0-9]+) ([a-zA-Z]),")) {
                        changeColor(action_pattrn_matcher);
                    } else {
                        not_added_commands.add("color");
                    }
                    break;
                case "show":
                    View.displayBoard(this.paint);
                    break;
                case "list":
                    View.displayList(this.paint.getShapes());
                    break;
                case "exit":
                    this.drawing_over = true;
                    break;
                case "commands":
                    View.displayCommands();
                    break;
            }
        }
        View.displayErrorCommands(not_added_commands);
    }

    private void changeColor(Matcher action_pattrn_matcher) {
        int index = Integer.parseInt(action_pattrn_matcher.group(2));
        char color = action_pattrn_matcher.group(6).charAt(0);
        this.paint.changeColor(index,color);
    }

    private void move(Matcher action_pattrn_matcher) {
        int index = Integer.parseInt(action_pattrn_matcher.group(2));
        int x_offset = Integer.parseInt(action_pattrn_matcher.group(3));
        int y_offset = Integer.parseInt(action_pattrn_matcher.group(4));
        this.paint.moveShape(index,x_offset,y_offset);
    }

    private void addSquare(Matcher action_pattrn_matcher) {
        int x = Integer.parseInt(action_pattrn_matcher.group(2));
        int y = Integer.parseInt(action_pattrn_matcher.group(3));
        double side = Double.parseDouble(action_pattrn_matcher.group(4));
        char color = action_pattrn_matcher.group(6).charAt(0);
        this.paint.newSquare(x,y,side,color);
    }

    private void addRectangle(Matcher action_pattrn_matcher) {
        int x = Integer.parseInt(action_pattrn_matcher.group(2));
        int y = Integer.parseInt(action_pattrn_matcher.group(3));
        double width = Double.parseDouble(action_pattrn_matcher.group(5));
        double height = Double.parseDouble(action_pattrn_matcher.group(4));
        char color = action_pattrn_matcher.group(6).charAt(0);
        this.paint.newRectangle(x,y,width,height,color);
    }

    private void addCircle(Matcher action_pattrn_matcher) {
        int x = Integer.parseInt(action_pattrn_matcher.group(2));
        int y = Integer.parseInt(action_pattrn_matcher.group(3));
        double radius = Double.parseDouble(action_pattrn_matcher.group(4));
        char color = action_pattrn_matcher.group(6).charAt(0);
        this.paint.newCircle(x,y,radius,color);
    }

    public void start() {
        Scanner keyboard = new Scanner(System.in);
        while (application_on) {
            System.out.println("WELCOME TO ASCIIPAINT");
            System.out.println("1. Start new drawing board");
            System.out.println("2. Exit");
            String menu_option = keyboard.nextLine();
            while (!menu_option.equals("2") && !menu_option.equals("1")) {
                System.out.println("\nInvalid option, press 1 to start a new drawing board or 2 to exit the application");
                menu_option = keyboard.nextLine();
            }
            if (menu_option.equals("1")) {
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
