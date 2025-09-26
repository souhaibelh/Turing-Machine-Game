package Model.commands;

import Model.*;
import Model.enums.CommandType;
import ModelUtils.Command;

/**
 * Add code command for undo redo
 */
public class AddCodeCommand implements Command {
    private final Code newCode;
    private final Code oldCode;
    private final Game game;
    private final TuringMachineChangeEvent event;
    /**
     * Takes in the game, the code to add, the turing machine that added it (so we can update the observers)
     * @param game game being played
     * @param code code being added
     */
    public AddCodeCommand(Game game, Code code) {
        newCode = code;
        oldCode = game.getCurrentUserCode();
        this.game = game;
        event = new TuringMachineChangeEvent();
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.ADD_CODE;
    }

    /**
     * We execute the command, exception handling is done in the facade TuringMachine,
     * if code added successfully we store the important information that the observer needs in the
     * TuringMachineChangeEvent instance, and then we use turingMachine to notify observers.
     *
     * @return
     */
    @Override
    public void execute() {
        game.addCode(newCode);
    }

    /**
     * We revert the execution of the command, exception handling is done in the facade TuringMachine,
     * if old code is re established successfully we store the important information that the
     * observer needs in the TuringMachineChangeEvent instance, and we use turingMachine to notify observers.
     */
    @Override
    public void unexecute() {
        game.addCode(oldCode);
    }

    public Code getNewCode() {
        return newCode;
    }
}
