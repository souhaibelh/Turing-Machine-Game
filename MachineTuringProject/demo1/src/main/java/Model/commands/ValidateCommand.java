package Model.commands;
import Model.enums.CommandType;
import Model.Game;
import Model.TuringMachine;
import Model.TuringMachineChangeEvent;
import ModelUtils.Command;

public class ValidateCommand implements Command {
    private final Game game;
    private final int verifierIndex;
    private final TuringMachine turingMachine;
    private final TuringMachineChangeEvent event;

    public ValidateCommand(Game game, int verifierIndex, TuringMachine turingMachine) {
        this.game = game;
        this.verifierIndex = verifierIndex;
        this.turingMachine = turingMachine;
        this.event = new TuringMachineChangeEvent();
    }

    @Override
    public void execute() {
        boolean result = game.verify(verifierIndex);
        event.setDoneCommandType(CommandType.VALIDATE_VERIFIER);
        event.setScore(game.getScore());
        event.setCurrentRound(game.getTotalRounds());
        event.setVerifierIndex(verifierIndex);
        event.setVerifierResult(result);
        turingMachine.notifyObservers(event);
    }

    @Override
    public void unexecute() {
        game.unverify(verifierIndex);
        event.setUndoneCommandType(CommandType.VALIDATE_VERIFIER);
        event.setScore(game.getScore());
        event.setCurrentRound(game.getTotalRounds());
        event.setVerifierIndex(verifierIndex);
        turingMachine.notifyObservers(event);
    }
}
