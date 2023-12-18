package Model.commands;

import Model.enums.CommandType;
import Model.Game;
import Model.TuringMachine;
import Model.TuringMachineChangeEvent;
import ModelUtils.Command;

/**
 * Defines next round command
 */
public class NextRoundCommand implements Command {
    private final Game game;
    private final TuringMachine turingMachine;
    private final TuringMachineChangeEvent event;

    /**
     * Takes in the game and the facade
     * @param game game
     * @param turingMachine facade
     */
    public NextRoundCommand(Game game, TuringMachine turingMachine) {
        event = new TuringMachineChangeEvent();
        this.turingMachine = turingMachine;
        this.game = game;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.NEXT_ROUND;
    }

    /**
     * We execute the command, exception handling is done in the facade TuringMachine,
     * if round skipped successfully we store the important information that the observer needs in the
     * TuringMachineChangeEvent instance, and then we use turingMachine to notify observers.
     */
    @Override
    public void execute() {
        game.nextRound();
    }

    /**
     * We execute the command, exception handling is done in the facade TuringMachine,
     * if round revert skip is successful, we store the important information that the observer needs in the
     * TuringMachineChangeEvent instance, and then we use turingMachine to notify observers.
     */
    @Override
    public void unexecute() {
        game.previousRound();
    }
}
