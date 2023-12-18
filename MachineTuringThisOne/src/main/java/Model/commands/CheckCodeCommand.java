package Model.commands;

import Model.enums.CommandType;
import Model.Game;
import Model.TuringMachine;
import Model.TuringMachineChangeEvent;
import ModelUtils.Command;

/**
 * Check command for undo redo
 */
public class CheckCodeCommand implements Command {
    private final Game game;
    private final TuringMachine turingMachine;
    private final TuringMachineChangeEvent event;
    private boolean isWon;

    /**
     * Constructs the command, uses the game and the facade
     * @param game game
     * @param turingMachine facade
     */
    public CheckCodeCommand(Game game, TuringMachine turingMachine) {
        this.game = game;
        this.turingMachine = turingMachine;
        event = new TuringMachineChangeEvent();
    }

    public boolean getWon() {
        return this.isWon;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.CHECK_CODE;
    }

    /**
     * We execute the command, exception handling is done in the facade TuringMachine,
     * if code checked successfully we store the important information that the observer needs in the
     * TuringMachineChangeEvent instance, and then we use turingMachine to notify observers.
     */
    @Override
    public void execute() {
        isWon = game.checkCode();
    }

    /**
     * We revert the execution of the command, exception handling is done in the facade TuringMachine,
     * if unchecked successfully we store the important information that the observer needs in the TuringMachineChangeEvent
     * instance, and we use turingMachine to notify observers.
     */
    @Override
    public void unexecute() {
        game.uncheckCode();
    }
}
