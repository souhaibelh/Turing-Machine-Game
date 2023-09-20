package g61610.atl.ascii;

import g61610.atl.ascii.controller.Application;
import g61610.atl.ascii.model.Circle;
import g61610.atl.ascii.model.Rectangle;
import g61610.atl.ascii.model.Shape;
import g61610.atl.ascii.view.View;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static boolean[] askInput(Application board) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("What is your next move, Picasso! (If you are confused, type commands to get a " +
                " list of everything you can do!)");
        String move = keyboard.nextLine();

        Pattern addrectangle = Pattern.compile("^add rectangle ([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+) ([a-zA-Z])$");
        Pattern addsquare = Pattern.compile("^add square ([0-9]+) ([0-9]+) ([0-9]+) ([a-zA-Z])$");
        Pattern addcircle = Pattern.compile("^add circle ([0-9]+) ([0-9]+) ([0-9]+) ([a-zA-Z])$");
        Pattern colorchange = Pattern.compile("^color ([0-9]+) ([a-zA-Z])$");
        Pattern moveshape = Pattern.compile("^move ([0-9]+) ([0-9]+) ([0-9]+)$");

        while (!move.equals("commands") && !move.equals("exit") && !move.equals("list") && !move.equals("show")
        && !addrectangle.matcher(move).find() && !addsquare.matcher(move).find() && !addcircle.matcher(move).find()
        && !colorchange.matcher(move).find() && !moveshape.matcher(move).find()) {
            System.out.println("Invalid command, type commands to see all the possible commands and make sure to not write any extra spaces");
            move = keyboard.nextLine();
        }
        boolean endgame = false;
        boolean displayboard = true;
        boolean[] returnedoptions = {endgame,displayboard};

        switch (move) {
            case "commands":
                View.displayCommands();
                returnedoptions[1] = false;
                return returnedoptions;
            case "exit":
                returnedoptions[0] = true;
                returnedoptions[1] = false;
                return returnedoptions;
            case "show":
                View.show(board.getPaint());
                break;
            case "list":
                List<Shape> totalshapes = board.getPaint().getDrawing().getShapes();
                System.out.println("Shapes in the board:\n");
                for (int i=0; i<totalshapes.size(); i++) {
                    String shapename;
                    if (totalshapes.get(i) instanceof Circle) {
                        shapename = "Circle";
                    } else if (totalshapes.get(i).getClass().getName().equals("g61610.atl.ascii.model.Rectangle")) {
                        shapename = "Rectangle";
                    } else {
                        shapename = "Square";
                    }
                    System.out.println("    " + (i + 1) + ". Shape: " + shapename + ". Color: " + totalshapes.get(i).getColor() + " [index: " + i + " , use this number to move/remove or change the colour of the shape]");
                }
                System.out.println();
                returnedoptions[1] = false;
                break;
            default:
                if (addrectangle.matcher(move).matches()) {
                    Matcher matcher = addrectangle.matcher(move);
                    matcher.find();
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    double width = Double.parseDouble(matcher.group(4));
                    double height = Double.parseDouble(matcher.group(3));
                    char color = matcher.group(5).charAt(0);
                    board.getPaint().newRectangle(x,y,width,height,color);
                } else if (addcircle.matcher(move).matches()) {
                    Matcher matcher = addcircle.matcher(move);
                    matcher.find();
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    double radius = Double.parseDouble(matcher.group(3));
                    char color = matcher.group(4).charAt(0);
                    board.getPaint().newCircle(x,y,radius,color);
                } else if (addsquare.matcher(move).matches()) {
                    Matcher matcher = addsquare.matcher(move);
                    matcher.find();
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    double side = Double.parseDouble(matcher.group(3));
                    char color = matcher.group(4).charAt(0);
                    board.getPaint().newSquare(x,y,side,color);
                } else if (colorchange.matcher(move).matches()) {
                    Matcher matcher = colorchange.matcher(move);
                    matcher.find();
                    int index = Integer.parseInt(matcher.group(1));
                    char newcolor = matcher.group(2).charAt(0);
                    board.getPaint().getDrawing().getShapes().get(index).setColor(newcolor);
                } else if (moveshape.matcher(move).matches()) {
                    Matcher matcher = moveshape.matcher(move);
                    matcher.find();
                    int index = Integer.parseInt(matcher.group(1));
                    double offsetX = Double.parseDouble(matcher.group(2));
                    double offsetY = Double.parseDouble(matcher.group(3));
                    board.getPaint().getDrawing().getShapes().get(index).move(offsetX,offsetY);
                }
        }
        return returnedoptions;
    }

    public static Application startDrawingBoard() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("What dimensions do you wish your drawing board to have, use the format -> width height (ex: 150 200), or type default for default dimensions: ");
        String dim = keyboard.nextLine();
        Pattern dimensions = Pattern.compile("^([0-9]+) ([0-9]+)");
        while (!dimensions.matcher(dim).find() && !dim.equals("default")) {
            System.out.println("Invalid input, make sure to type the dimensions in the following format with no extra spaces: width height");
            dim = keyboard.nextLine();
        }
        Application board;
        if (dim.equals("default")) {
            board = new Application();
        } else {
            Matcher dimmatcher = dimensions.matcher(dim);
            dimmatcher.find();
            board = new Application(Integer.parseInt(dimmatcher.group(1)),
                    Integer.parseInt(dimmatcher.group(2)));
        }
        return board;
    }

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        boolean firstMenu = true;

        while (firstMenu) {
            System.out.println("WELCOME to ASCII Paint !");
            System.out.println("1. New drawing board");
            System.out.println("2. Exit");
            int option = keyboard.nextInt();
            while (option < 1 || option > 2) {
                System.out.println("Please select a valid option:");
                System.out.println("1. New drawing board");
                System.out.println("2. Exit");
                option = keyboard.nextInt();
            }
            if (option == 1) {
                boolean exitgame = false;
                Application board = startDrawingBoard();
                while (!exitgame) {
                    boolean[] options = askInput(board);
                    exitgame = options[0];
                    if (options[1]) {
                        View.show(board.getPaint());
                    }
                }
            } else {
                firstMenu = false;
            }
        }
    }
}
