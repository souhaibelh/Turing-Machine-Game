package Applications;

import Model.Code;
import Model.Level;
import Model.TuringMachine;
import GeneralMethods.FileMethods;
import View.View;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Constants.NumericConstants.CODE_SIZE;

public class Application {
    private final List<Level> gameLevels = FileMethods.getLevels();
    private TuringMachine facade;
    private final View view;

    Application(View view) {
        this.view = view;
    }
    public List<Level> getGameLevels() {
        return gameLevels;
    }

    public void setFacade(TuringMachine facade) {
        this.facade = facade;
    }

    public void askInput() {
        System.out.println();
        view.displayLevel(facade.getCurrentLevel());
        System.out.println();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("What is your next move (Type commands for info)");
        String move = keyboard.nextLine();

        Pattern validate = Pattern.compile("(?: ?)+(?:v|validate)(?: ?)+([0-9]+)(?: ?)+");
        Pattern enterCode = Pattern.compile("(?: ?)+code(?: ?)+([1-5]{3})(?: ?)+");
        Matcher enterCodeMatcher = enterCode.matcher(move);
        Matcher validateMatcher = validate.matcher(move);

        while (!move.equals("nround") && !move.equals("quit") && !move.equals("guess") && !move.equals("commands") && !move.equals("undo") && !move.equals("redo") && !enterCodeMatcher.matches() && !validateMatcher.matches()) {
            System.out.println("Invalid, input, type commands to see what you can do");
            move = keyboard.nextLine();
            enterCodeMatcher = enterCode.matcher(move);
            validateMatcher = validate.matcher(move);
        }

        switch (move) {
            case "nround" -> facade.nextRound();
            case "quit" -> System.exit(0);
            case "guess" -> {
                boolean isWinner = false ; //facade.checkCode();
                if (isWinner) {
                    view.displayWinner(facade.getGame());
                    System.exit(0);
                } else {
                    view.displayLoser(facade.getGame());
                    System.exit(0);
                }
            }
            case "commands" -> view.displayCommands();
        }

        if (validateMatcher.matches()) {
            boolean validation = true; //facade.validate(Integer.parseInt(validateMatcher.group(1)));
            System.out.println(validation);
        } else if (enterCodeMatcher.matches()) {
            Code code = new Code(enterCodeMatcher.group(1));
        }
        System.out.println();
    }

    public Level askLevel() {
        Scanner keyboard = new Scanner(System.in);
        String selection;
        Pattern selectionPattern = Pattern.compile("(?: ?)+(?:([0-9]+)|r)(?: ?)+");
        Matcher selectionMatcher;

        while (true) {
            System.out.println("Introduce a number between 1 and 16 or 'r' for a random problem ");
            selection = keyboard.nextLine();
            selectionMatcher = selectionPattern.matcher(selection);

            if (selectionMatcher.matches()) {
                if (selection.contains("r")) {
                    return gameLevels.get(getRandomNumber(16));
                } else {
                    if (validateProblemIndex(Integer.parseInt(selectionMatcher.group(1)))) {
                        return gameLevels.get(Integer.parseInt(selectionMatcher.group(1)) - 1);
                    }
                }
            }
            System.out.println("Invalid input, try again");
        }
    }

    public static boolean validateProblemIndex(int index) {
        return index > 0 && index < 17;
    }

    public static int getRandomNumber(int max) {
        Random rnumber = new Random();
        return rnumber.nextInt(max);
    }
}