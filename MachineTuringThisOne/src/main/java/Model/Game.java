package Model;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Game {
    private Level currentLevel;
    private List<Round> gameRounds;
    private int totalRounds;
    private int totalVerifiedVerifiers;
    private boolean initialized;
    private boolean gameOver;
    private boolean gameWon;

    /**
     * Starts a fresh game
     */
    public Game() {
        initialized = false;
    }

    public void setInitialState() {
        gameOver = false;
        initialized = true;
        totalRounds = 1;
        totalVerifiedVerifiers = 0;
        gameRounds = new ArrayList<>();
    }

    private void generalVerifications() {
        if (!initialized) {
            throw new TuringMachineException("This game has been constructed but not initialized, make sure to setLevel" +
                    "method before being able to call methods");
        } else if (gameOver) {
            String txt = gameWon ? "won" : "lost";
            throw new TuringMachineException("This game is over, you already " + txt + " it");
        }
    }

    public int getScore() {
        generalVerifications();
        return totalVerifiedVerifiers + totalRounds;
    }

    public Level getCurrentLevel() {
        generalVerifications();
        return currentLevel;
    }

    public Code getCodeLevel() {
        generalVerifications();
        return currentLevel.getCode();
    }

    public Code getCurrentUserCode() {
        generalVerifications();
        return gameRounds.get(gameRounds.size() - 1).getUserCode();
    }

    /**
     * Method attempts to add a code to a round.
     * @param code code to be added to the round.
     */
    public void addCode(Code code) {
        generalVerifications();
        for (Round r : gameRounds) { // Iterate through all played rounds in this game
            // if the round code is not null which means it was a played round and the round user code is equal
            // to the user code introduced, throw an exception
            if (r.getUserCode() != null && r.getUserCode().equals(code)) {
                throw new TuringMachineException("You already played a round with that same code: " + code);
            }
        }
        if (gameRounds.get(gameRounds.size() - 1).isConsumed()) { // if the last added round is already finished throw an exception
            throw new TuringMachineException("Can't add code, there aren't any active rounds");
        } else {
            // if the code is valid and there is an active round we can add it to
            unconsumeVerifiers(); // we unconsume the verifiers that got consumed on the last round
            gameRounds.get(gameRounds.size() - 1).setUserCode(code); // and we add the code to the active round
        }
    }

    /**
     * Method that is going to unverified all the verifiers in the level
     */
    public void unconsumeVerifiers() {
        generalVerifications();
        // For each verifier in the level, revert the consume
        currentLevel.getVerifiers().forEach(Consumable::unconsume);
    }

    /**
     * Method that is going to verify a verifier thanks to the index
     * @param validatorIndex index of the verifier
     * @return result of the verification
     */
    public boolean verify(int validatorIndex) {
        generalVerifications();
        // if there isn't any round or the last round is already consumed we throw an exception
        if (gameRounds.isEmpty() || gameRounds.get(gameRounds.size() - 1).isConsumed()) {
            throw new TuringMachineException("No active rounds");
        }
        boolean verifierResult = gameRounds.get(gameRounds.size() - 1).verify(validatorIndex); // store the result of the verification
        totalVerifiedVerifiers++; // we increase the amount of verified verifiers in the game
        return verifierResult; // return the result of the verification
    }

    /**
     * Method that skips the round to the next one
     */
    public void nextRound() {
        generalVerifications();
        // if there aren't any rounds or the last round is active but hasn't a code set to it, yet we throw an exception
        if (gameRounds.isEmpty() || gameRounds.get(gameRounds.size() - 1).getUserCode() == null) {
            throw new TuringMachineException("You can still introduce a code in this round");
        } else {
            // if the skip round is valid we unconsume all verifiers (we don't have to worry about keeping track of the
            // consumed verifiers in the round as those are added to a list consumedVerifiers that every round has,
            // so we can later on compare the consumed ones and the non consumed ones for the undo redo commands
            unconsumeVerifiers();
            gameRounds.get(gameRounds.size() - 1).consume(); // we consume the last round
            gameRounds.add(new Round(currentLevel)); // we add a new round
            totalRounds++; // we increase the rounds counter
        }
    }

    /**
     * Method used to check if the code is the winning code
     * @return true if the code is right false if no, (could also call this method checkGameWin)
     */
    public boolean checkCode() {
        generalVerifications();
        // if there aren't any rounds throw an exception
        if (gameRounds.isEmpty()) {
            throw new TuringMachineException("You haven't played a single round yet");
        }
        // if the current round is already consumed throw an exception
        if (gameRounds.get(gameRounds.size() - 1).isConsumed()) {
            throw new TuringMachineException("You must be in an active round to verify your code");
        }
        // otherwise return the result of the verification
        return gameWon = gameRounds.get(gameRounds.size() - 1).getUserCode().equals(currentLevel.getCode());
    }

    /**
     * This method returns the total amount of played rounds
     * @return total played rounds
     */
    public int getTotalRounds() {
        generalVerifications();
        return totalRounds;
    }

    /**
     * This method reverts the verification of a verifier thanks to an index, since we only use this method for the
     * undo redo command, and if we must undo something it means it was rightly done so there is no need to verify
     * for complex logic.
     * @param verifierIndex index of the verifier
     */
    public void unverify(int verifierIndex) {
        generalVerifications();
        gameRounds.get(gameRounds.size() - 1).unVerify(verifierIndex);
        totalVerifiedVerifiers--;
    }

    /**
     * This method goes back to the previous round
     */
    public void previousRound() {
        generalVerifications();
        gameRounds.remove(gameRounds.size() - 1); // we remove the last round
        gameRounds.get(gameRounds.size() - 1).unconsume(); // we unconsume the current round
        // we re-establish the state of the verifiers of the round that was unconsumed by accessing its
        // consumed verifiers list, filtering the level list to only get the ones that are in the
        // consumed verifiers list, then we call the consume method on them
        IntStream.range(0, gameRounds.get(gameRounds.size() - 1).getConsumedVerifiers().size()).forEach(i ->
                currentLevel.getVerifiers().stream()
                        .filter(v -> v.equals(gameRounds.get(gameRounds.size() - 1).getConsumedVerifiers().get(i)))
                        .forEach(Consumable::consume)
        );

        totalRounds--;
    }

    /**
     * Sets an initial state to the game, then assigns the leftover attributes, this makes the game ready to be played
     * @param level level this game will be played on
     */
    public void setLevel(Level level) {
        setInitialState();
        currentLevel = level;
        gameRounds.add(new Round(currentLevel));
    }
}
