package View;

import Model.Game;
import Model.Level;

import java.util.List;

public class View {
    public void displayLevels(List<Level> gameLevels) {
        for (Level l : gameLevels) {
            System.out.println(l);
        }
        System.out.println();
    }

    public void displayWinner(Game game) {
        System.out.println("You've won!");
        System.out.println("Your code: " + game.getCurrentUserCode());
        System.out.println("Machine code: " + game.getCodeLevel());
    }

    public void displayLoser(Game game) {
        System.out.println("You lost!");
        System.out.println("Your code: " + game.getCurrentUserCode());
        System.out.println("Machine code: " + game.getCodeLevel());
    }

    public void displayCommands() {
        System.out.println("Game commands: ");
        System.out.println("nround = skips the round and allows for a new code to be given");
        System.out.println("guess = checks if u guessed the right code");
        System.out.println("quit = quits the game");
        System.out.println("code 123 = sets the code for the round");
        System.out.println("v index = validator index to use");
    }

    public void displayLevel(Level level) {
        System.out.println(level);
    }
}
