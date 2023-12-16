package Model;

import GeneralMethods.FileMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class defines what is a level in turing machine game.
 */
public class Level {
    private final int num;
    private final int difficulty;
    private final int luck;
    private final Code code;

    private final List<Integer> verifiersNbs;
    private List<TuringMachineVerifier> verifiers;

    /**
     * A level is identified by a number, difficulty, luck, code and the verifiers list it uses in the game.
     * @param num number of the level
     * @param difficulty difficulty level
     * @param luck luck factor involved in passing the level
     * @param code level code that must be guessed by the user to win the level
     * @param verifierNbs list of integers containing all the verifiers this level has
     */
    public Level(int num, int difficulty, int luck, Code code, List<Integer> verifierNbs) {
        this.num = num;
        this.difficulty = difficulty;
        this.luck = luck;
        this.code = code;
        this.verifiers = new ArrayList<>();
        this.verifiersNbs = verifierNbs;
    }

    /**
     * Sets the instances of verifiers to the level, thanks to this we can load them later on when the user picks a level,
     * loading all the verifiers can become a tedious task in the future.
     */
    public void setVerifiers() {
        verifiers = FileMethods.getValidators(this.verifiersNbs);
    }

    public int getLuck() {
        return this.luck;
    }

    public int getNum() {
        return this.num;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public Code getCode() {
        return this.code;
    }

    /**
     * Returns a protected unmodifiable list of verifiers
     * @return unmodifiable list
     */
    public List<TuringMachineVerifier> getVerifiers() {
        return Collections.unmodifiableList(verifiers);
    }

    @Override
    public String toString() {
        String firstPart = "Level: " + num + " Difficulty: " + difficulty + " Luck: " + luck + "\n";
        StringBuilder verifiers = new StringBuilder("Verifiers: \n");
        for (TuringMachineVerifier x : this.verifiers) {
            verifiers.append(x);
        }
        return firstPart + verifiers + "\n";
    }
}
