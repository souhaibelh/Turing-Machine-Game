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

/**
 * This is the model, used to update the view with Observer Observable pattern.
 */
public class TuringMachine implements Observable {
    private final List<Observer> observerList;
    private final CommandManager cm;
    private final Game game;

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
        cm.undo();
    }

    /**
     * Redoes the last command
     */
    public void redo() {
        cm.redo();
    }

    /**
     * Adds a code to the game and notifies the observers in case of success or fail
     * @param code code to add
     */
    public void addCode(String code) {
        TuringMachineChangeEvent change = new TuringMachineChangeEvent();
        try {
            Code currentCode = new Code(code);
            AddCodeCommand addCodeCommand = new AddCodeCommand(game, currentCode, this);
            cm.newCommand(addCodeCommand);
        } catch (TuringMachineException e) {
            change.setErrorCommandType(CommandType.ADD_CODE);
            change.setErrorMessage(e.getMessage());
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
        } catch (TuringMachineException e) {
            change.setErrorCommandType(CommandType.VALIDATE_VERIFIER);
            change.setErrorMessage(e.getMessage());
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
        } catch (TuringMachineException e) {
            change.setErrorCommandType(CommandType.NEXT_ROUND);
            change.setErrorMessage(e.getMessage());
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
        } catch (TuringMachineException e) {
            change.setErrorCommandType(CommandType.CHECK_CODE);
            change.setErrorMessage(e.getMessage());
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
        for (Observer b : observerList) {
            b.update(event);
        }
    }
}
