package Model;

import Model.commands.AddCodeCommand;
import Model.commands.CheckCodeCommand;
import Model.commands.NextRoundCommand;
import Model.commands.ValidateCommand;
import Model.enums.CommandType;
import ModelUtils.CommandManager;
import ModelUtils.Observable;
import ModelUtils.Observer;

import java.util.ArrayList;
import java.util.List;

import static Model.enums.CommandType.*;

/**
 * This is the model, used to update the view with Observer Observable pattern.
 */
public class TuringMachine implements Observable {
    private final List<Observer> observerList;
    private final CommandManager cm;
    private final Game game;
    private boolean verificationResult;
    private boolean isWon;

    /**
     * Has a command manager for the commands, so we can undo redo and has a game instance and also a observerList
     * in this case only the GameView will observe.
     */
    public TuringMachine() {
        observerList = new ArrayList<>();
        this.game = new Game();
        cm = new CommandManager();
    }

    /**
     * Sets the level of the game this model interacts with
     * @param level level to set
     */
    public void setGameLevel(Level level) {
        this.game.setLevel(level);
    }

    /**
     * Undoes the last command
     */
    public void undo() {
        CommandType undone = cm.undo();
        TuringMachineChangeEvent change = new TuringMachineChangeEvent();
        if (undone != null) {
            switch (undone) {
                case ADD_CODE -> {
                    change.setUndoneCommandType(CommandType.ADD_CODE);
                    change.setScore(game.getScore());
                    change.setCode(game.getCurrentUserCode());
                    change.setCurrentRound(game.getTotalRounds());
                }
                case VALIDATE_VERIFIER -> {
                    change.setUndoneCommandType(VALIDATE_VERIFIER);
                    change.setScore(game.getScore());
                    change.setCurrentRound(game.getTotalRounds());
                }
                case NEXT_ROUND -> {
                    change.setUndoneCommandType(NEXT_ROUND);
                    change.setCode(game.getCurrentUserCode());
                    change.setScore(game.getScore());
                    change.setCurrentRound(game.getTotalRounds());
                }
                case CHECK_CODE -> {
                    change.setUndoneCommandType(CommandType.CHECK_CODE);
                    change.setGameFinished(false);
                    change.setGameWon(false);
                }
            }
        }
        notifyObservers(change);
    }

    /**
     * Redoes the last command
     */
    public void redo() {
        CommandType redone = cm.redo();
        TuringMachineChangeEvent change = new TuringMachineChangeEvent();
        if (redone != null) {
            switch (redone) {
                case ADD_CODE -> {
                    change.setDoneCommandType(CommandType.ADD_CODE);
                    change.setScore(game.getScore());
                    change.setCode(game.getCurrentUserCode());
                    change.setCurrentRound(game.getTotalRounds());
                }
                case VALIDATE_VERIFIER -> {
                    change.setDoneCommandType(VALIDATE_VERIFIER);
                    change.setVerifierResult(game.getCurrentValidatorResult());
                    change.setScore(game.getScore());
                    change.setCurrentRound(game.getTotalRounds());
                }
                case NEXT_ROUND -> {
                    change.setDoneCommandType(NEXT_ROUND);
                    change.setScore(game.getScore());
                    change.setCurrentRound(game.getTotalRounds());
                    notifyObservers(change);
                }
                case CHECK_CODE -> {
                    change.setDoneCommandType(CommandType.CHECK_CODE);
                    change.setGameFinished(true);
                    change.setGameWon(isWon);
                }
            }
        }
        notifyObservers(change);
    }

    /**
     * Adds a code to the game and notifies the observers in case of success or fail
     * @param code code to add
     */
    public void addCode(String code) {
        TuringMachineChangeEvent change = new TuringMachineChangeEvent();
        try {
            Code currentCode = new Code(code);
            AddCodeCommand addCodeCommand = new AddCodeCommand(game, currentCode);
            cm.newCommand(addCodeCommand);
            change.setDoneCommandType(CommandType.ADD_CODE);
            change.setScore(game.getScore());
            change.setCode(addCodeCommand.getNewCode());
            change.setCurrentRound(game.getTotalRounds());
        } catch (TuringMachineException e) {
            change.setErrorCommandType(ADD_CODE);
            change.setErrorMessage(e.getMessage());
        } finally {
            notifyObservers(change);
        }
    }

    /**
     * Verifies a verifier by index and notifies the observers in case of success or fail
     * @param verifierIndex index of verifier
     */
    public void validate(int verifierIndex) {
        TuringMachineChangeEvent change = new TuringMachineChangeEvent();
        try {
            ValidateCommand validateCommand = new ValidateCommand(game, verifierIndex, this);
            cm.newCommand(validateCommand);
            verificationResult = validateCommand.getIsValidVerification();
            change.setDoneCommandType(VALIDATE_VERIFIER);
            change.setVerifierResult(validateCommand.getIsValidVerification());
            change.setScore(game.getScore());
            change.setCurrentRound(game.getTotalRounds());
            change.setVerifierIndex(verifierIndex);
        } catch (TuringMachineException e) {
            change.setErrorCommandType(VALIDATE_VERIFIER);
            change.setErrorMessage(e.getMessage());
        } finally {
            notifyObservers(change);
        }
    }

    /**
     * Skips the current round and notifies the observers
     */
    public void nextRound() {
        TuringMachineChangeEvent change = new TuringMachineChangeEvent();
        try {
            NextRoundCommand nextRoundCommand = new NextRoundCommand(game, this);
            cm.newCommand(nextRoundCommand);
            change.setDoneCommandType(NEXT_ROUND);
            change.setScore(game.getScore());
            change.setCurrentRound(game.getTotalRounds());
        } catch (TuringMachineException e) {
            change.setErrorCommandType(NEXT_ROUND);
            change.setErrorMessage(e.getMessage());
        } finally {
            notifyObservers(change);
        }
    }

    /**
     * Method that checks if we won or lost the game
     */
    public void checkCode() {
        TuringMachineChangeEvent change = new TuringMachineChangeEvent();
        try {
             CheckCodeCommand checkCodeCommand = new CheckCodeCommand(game, this);
             cm.newCommand(checkCodeCommand);
             isWon = checkCodeCommand.getWon();
             change.setDoneCommandType(CommandType.CHECK_CODE);
             change.setGameFinished(true);
             change.setGameWon(isWon);
        } catch (TuringMachineException e) {
             change.setErrorCommandType(CommandType.CHECK_CODE);
             change.setErrorMessage(e.getMessage());
        } finally {
            notifyObservers(change);
        }
    }

    public Game getGame() {
        return game;
    }

    /**
     * returns current level of the game
     * @return current level of teh game
     */
    public Level getCurrentLevel() {
        return game.getCurrentLevel();
    }

    @Override
    public void registerObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observerList.remove(o);
    }

    @Override
    public void notifyObservers(TuringMachineChangeEvent event) {
        for (Observer x : observerList) {
            x.update(event);
        }
    }
}
