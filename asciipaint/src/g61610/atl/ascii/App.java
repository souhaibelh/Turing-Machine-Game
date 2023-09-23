package g61610.atl.ascii;

import g61610.atl.ascii.controller.Application;
import g61610.atl.ascii.model.Circle;
import g61610.atl.ascii.model.Point;
import g61610.atl.ascii.model.Shape;
import g61610.atl.ascii.model.Square;
import g61610.atl.ascii.view.View;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static String fixStringRegex(String move) {
        if (move.charAt(move.length() - 1) != ',' && move.charAt(move.length()-1) != ' ') {
            move = move + ",";
        }
        if (move.charAt(move.length()-1) == ' ') {
            move = move.substring(0,move.length() - 1);
            if (move.charAt(move.length() - 2) != ',') {
                move = move + ",";
            }
        }
        return move;
    }
    public static boolean[] askInput(Application board) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("\nWhat is your next move, Picasso! (If you are confused, type commands to get a " +
                " list of everything you can do!)");
        String move = keyboard.nextLine();

        if (!move.equals("list") && !move.equals("commands") && !move.equals("exit") && !move.equals("show")) {
            move = fixStringRegex(move);
        }

        Pattern multipleadd = Pattern.compile("^((add|move|color)( (rectangle|circle|square))? ([0-9]+)( [0-9]+)?( -?[0-9]+)?( -?[0-9]+)?( [a-zA-Z])?, ?)+$");
        Pattern addrectangle = Pattern.compile("^add rectangle ([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+) ([a-zA-Z])$");
        Pattern addsquare = Pattern.compile("^add square ([0-9]+) ([0-9]+) ([0-9]+) ([a-zA-Z])$");
        Pattern addcircle = Pattern.compile("^add circle ([0-9]+) ([0-9]+) ([0-9]+) ([a-zA-Z])$");
        Pattern colorchange = Pattern.compile("^color ([0-9]+) ([a-zA-Z])$");
        Pattern moveshape = Pattern.compile("^move ([0-9]+) (-?[0-9]+) (-?[0-9]+)$");

        while (!move.equals("commands") && !move.equals("exit") && !move.equals("list") && !move.equals("show")
        && !multipleadd.matcher(move).matches()) {
            System.out.println("Invalid command, type commands to see all the possible commands and make sure to not write any extra spaces");
            move = keyboard.nextLine();
            if (!move.equals("list") && !move.equals("commands") && !move.equals("exit") && !move.equals("show")) {
                move = fixStringRegex(move);
            }
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
                board.displayBoard();
                returnedoptions[1] = false;
                break;
            case "list":
                List<Shape> totalshapes = board.getShapes();
                System.out.println("Shapes in the board:\n");
                for (int i=0; i<totalshapes.size(); i++) {
                    String shapename;
                    if (totalshapes.get(i) instanceof Circle) {
                        shapename = "Circle";
                    } else if (totalshapes.get(i) instanceof Square) {
                        shapename = "Square";
                    } else {
                        shapename = "Rectangle";
                    }
                    System.out.println("    " + (i + 1) + ". Shape: " + shapename + ". Color: " + totalshapes.get(i).getColor() + " [index: " + i + " , use this number to move/remove or change the colour of the shape]");
                }
                System.out.println();
                returnedoptions[1] = false;
                break;
            default:
                String[] splitarguments = move.split(",");
                for (int i=0; i<splitarguments.length; i++) {
                    if (splitarguments[i].charAt(0) == ' ' && splitarguments[i].length() == 1) {
                        System.out.println("onlycenter");
                    }
                    if (splitarguments[i].charAt(0) == ' '){
                        int nbspaces = 1;
                        int j = 1;
                        while (splitarguments[j].charAt(j) == ' ') {
                            nbspaces++;
                            j++;
                        }
                        splitarguments[i] = splitarguments[i].substring(nbspaces);
                    }
                    Matcher rectanglematcher = addrectangle.matcher(splitarguments[i]);
                    Matcher circlematcher = addcircle.matcher(splitarguments[i]);
                    Matcher squarematcher = addsquare.matcher(splitarguments[i]);
                    Matcher colorchangematcher = colorchange.matcher(splitarguments[i]);
                    Matcher moveshapematcher = moveshape.matcher(splitarguments[i]);

                    if (rectanglematcher.matches()) {
                        rectanglematcher = addrectangle.matcher(splitarguments[i]);
                        rectanglematcher.find();
                        int x = Integer.parseInt(rectanglematcher.group(1));
                        int y = Integer.parseInt(rectanglematcher.group(2));
                        double width = Double.parseDouble(rectanglematcher.group(4));
                        double height = Double.parseDouble(rectanglematcher.group(3));
                        char color = rectanglematcher.group(5).charAt(0);
                        board.newRectangle(x,y,width,height,color);
                    } else if (circlematcher.matches()) {
                        circlematcher = addcircle.matcher(splitarguments[i]);
                        circlematcher.find();
                        int x = Integer.parseInt(circlematcher.group(1));
                        int y = Integer.parseInt(circlematcher.group(2));
                        double radius = Double.parseDouble(circlematcher.group(3));
                        char color = circlematcher.group(4).charAt(0);
                        board.newCircle(x,y,radius,color);
                    } else if (squarematcher.matches()) {
                        squarematcher = addsquare.matcher(splitarguments[i]);
                        squarematcher.find();
                        int x = Integer.parseInt(squarematcher.group(1));
                        int y = Integer.parseInt(squarematcher.group(2));
                        double side = Double.parseDouble(squarematcher.group(3));
                        char color = squarematcher.group(4).charAt(0);
                        board.newSquare(x,y,side,color);
                    } else if (colorchangematcher.matches()) {
                        colorchangematcher = colorchange.matcher(splitarguments[i]);
                        colorchangematcher.find();
                        int index = Integer.parseInt(colorchangematcher.group(1));
                        char newcolor = colorchangematcher.group(2).charAt(0);
                        board.changeShapeColor(index,newcolor);
                    } else if (moveshapematcher.matches()) {
                        moveshapematcher = moveshape.matcher(splitarguments[i]);
                        moveshapematcher.find();
                        int index = Integer.parseInt(moveshapematcher.group(1));
                        int offsetX = Integer.parseInt(moveshapematcher.group(2));
                        int offsetY = Integer.parseInt(moveshapematcher.group(3));
                        board.moveShape(index,offsetX,offsetY);
                    }
                }
        }
        return returnedoptions;
    }

    public static Application startDrawingBoard() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("\nWhat dimensions do you wish your drawing board to have, use the format -> width height (ex: 150 200), or type default for default dimensions: ");
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
                        board.displayBoard();
                    }
                }
            } else {
                firstMenu = false;
            }
        }
    }
}
