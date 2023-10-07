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

    // This attribute tells if the current drawing is over or no
    private boolean drawing_over = false;

    // This attribute tells if the app is open or no
    private boolean application_on = true;

    /**
     * This method is in charge of accepting the dimensions (width/height), verifying them to be valid, and then initializes
     * the attribute paint to be an AsciiPaint of such dimensions, if the user input indicates that the dimensions are default
     * then we call the default constructor of AsciiPaint.
     * @param keyboard scanner to detect input
     */
    private void startBoard(Scanner keyboard) {
        System.out.println("\nIntroduce the dimensions of your board in this format: width height or type in default or d for default size");
        String dimensions = keyboard.nextLine();
        Pattern dimensions_pattrn = Pattern.compile("^(?: ?)+(?:(([0-9]+) ([0-9]+)|default|d))(?: ?)+$");
        while (!dimensions_pattrn.matcher(dimensions).matches()) {
            System.out.println("\nInvalid format, follow this example: '200 150' (200 width 150 height) or 'default' (default board size) or 'd' (default board size)");
            dimensions = keyboard.nextLine();
        }
        Matcher dimensions_pattrn_matcher = dimensions_pattrn.matcher(dimensions);
        if (dimensions_pattrn_matcher.find()) {
            switch (dimensions_pattrn_matcher.group(1)) {
                case "d":
                case "default":
                    this.paint = new AsciiPaint(); // In case the input is d or default then we call the default constructor of AsciiPaint
                    break;
                default:
                    int width = Integer.parseInt(dimensions_pattrn_matcher.group(2));
                    int height = Integer.parseInt(dimensions_pattrn_matcher.group(3));
                    this.paint = new AsciiPaint(width,height);
                    break;
            }
        }
    }

    /**
     * This method asks for the user to input a list or a single command, it then passes it to a method that checks if there is a coma
     * in the end, if there isn't one we return the same command string with a coma added to the end, if it has one already we
     * return the same string we passed in as parameters. This is to make this part more user-friendly if the user forgets about adding a
     * coma in the end we add it automatically (we need it for the split command to transform the string with multiple commands into an array).
     * We split the commands by comas and put them in a String array, we iterate through each string and see if it fits with any of the
     * regexes we have for the adding, move and color commands, if it does then it performs the action, if it doesn't fit with none and in
     * top of that it doesn't fit with "exit" "list" "show" and "commands" then we add the command in a List created previously named
     * invalid_commands, in the end when all commands are performed we will display these invalid commands so the user can know what commands
     * had issues.
     * @param keyboard scanner to detect input
     */
    private void askInput(Scanner keyboard) {
        System.out.println("\nWhat is your next drawing little Picasso (if you have no idea what to type here type commands)");
        String commands = keyboard.nextLine();

        String[] commands_array = commands.split(","); // We split the String containing the commands in an array with the commands

        /*
            This pattern accepts string of the form: add rectangle (integer) (integer) (integer) (integer) (character from a to z)
            without caring about how many spaces there is between them, for example we can have 30 spaces between rectangle and integer,
            and it would still be accepted.
         */
        Pattern rectangle = Pattern.compile("(?: ?)+add +rectangle +(-?[0-9+]+) +(-?[0-9+]+) +([0-9+]+) +([0-9+]+) +([a-zA-Z])(?: ?)+");

        /*
            This pattern accepts both square and circles, they both take in the same amount of arguments to instance. It doesn't care
            about how many spaces there is between each parameter.
         */
        Pattern circle_square = Pattern.compile("(?: ?)+add +(circle|square) +(-?[0-9]+) +(-?[0-9]+) +([0-9]+) +([a-zA-Z])(?: ?)+");


        //  This pattern accepts move command, it doesn't care about the amount of spaces between parameters.
        Pattern move = Pattern.compile("(?: ?)+move +([0-9]+) +(-?[0-9]+) +(-?[0-9]+)(?: ?)+");


        //  This pattern accepts color command, it doesn't care about the amount of spaces between parameters.
        Pattern color = Pattern.compile("(?: ?)+color +([0-9]+) +([a-zA-Z])(?: ?)+");

        List<String> invalid_commands = new ArrayList<>(); // This list contains all invalid commands or ones who lack parameters

        /*
            This for loop is going to loop through each command in the string array, and it will check what they are and perform their
            actions respectively.
         */
        for (String c : commands_array) {

            /*
                These here are the matchers of the patterns, they match every string to all patterns, so we can see which one matches and
                execute its designed action.
             */
            Matcher circle_square_matcher = circle_square.matcher(c);
            Matcher rectangle_matcher = rectangle.matcher(c);
            Matcher move_matcher = move.matcher(c);
            Matcher color_matcher = color.matcher(c);

            // If else statements checking if the string is a certain command or another
            if (c.contains("exit")) {

                this.drawing_over = true;

            } else if (c.contains("list")) {

                View.displayList(this.paint.getShapes());

            } else if (c.contains("show")) {

                View.displayBoard(this.paint);

            } else if (c.contains("commands")) {

                View.displayCommands();

            } else if (rectangle_matcher.matches()) {

                int x = Integer.parseInt(rectangle_matcher.group(1));
                int y = Integer.parseInt(rectangle_matcher.group(2));
                double height = Double.parseDouble(rectangle_matcher.group(3));
                double width = Double.parseDouble(rectangle_matcher.group(4));
                char shape_color = rectangle_matcher.group(5).charAt(0);
                this.paint.newRectangle(x,y,width,height,shape_color);

            } else if (circle_square_matcher.matches()) {

                int x = Integer.parseInt(circle_square_matcher.group(2));
                int y = Integer.parseInt(circle_square_matcher.group(3));
                double radius_side = Double.parseDouble(circle_square_matcher.group(4));
                char shape_color = circle_square_matcher.group(5).charAt(0);
                /*
                    We use the same pattern for circles and squares since they take the same arguments, the if statement here
                    calls either the circle add method or the square add method based on the first group of the command which
                    indicates if we are adding a square or a circle.
                 */
                if (circle_square_matcher.group(1).equals("square")) {
                    this.paint.newSquare(x, y, radius_side, shape_color);
                } else {
                    this.paint.newCircle(x, y, radius_side, shape_color);
                }

            } else if (move_matcher.matches()) {

                int index = Integer.parseInt(move_matcher.group(1));
                int offset_x = Integer.parseInt(move_matcher.group(2));
                int offset_y = Integer.parseInt(move_matcher.group(3));
                this.paint.moveShape(index,offset_x,offset_y);

            } else if (color_matcher.matches()) {

                int index = Integer.parseInt(color_matcher.group(1));
                char new_color = color_matcher.group(2).charAt(0);
                this.paint.changeColor(index,new_color);

            } else {
                /*
                    If none of the commands matched with the String, it means either the String lacks parameters or is an invalid command
                    in that case we add it to the invalid commands list that will be output in the end to make sure the user sees where he
                    was wrong.
                 */
                invalid_commands.add(c);
            }
        }
        View.displayBoard(this.paint);
        // We output the list containing all the invalid commands the user introduced
        View.displayInvalidCommands(invalid_commands);
    }

    /**
     * This is the method that starts everything, it asks if the user wants to start a new drawing board or if he wants to exit the
     * application, as long as the application is open it will always display this, if the user presses new board then as long as the
     * drawing_over attribute is false it will ask for a new input, once its false it will go back to the main menu, which will only
     * stop running if the application_on attribute is false, and the only way to set it off is to choose so from the main menu itself.
     */
    public void start() {
        Scanner keyboard = new Scanner(System.in);
        while (application_on) {

            System.out.println("WELCOME TO ASCIIPAINT");
            System.out.println("1. Start new drawing board");
            System.out.println("2. Exit");

            String menu_option = keyboard.nextLine();

            // As long as the user inputs an invalid number (something different than 1 or 2, only options in the menu).
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
