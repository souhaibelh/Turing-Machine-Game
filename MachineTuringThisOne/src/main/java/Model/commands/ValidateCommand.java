package Model.commands;
import Model.enums.CommandType;
import Model.Game;
import Model.TuringMachine;
import Model.TuringMachineChangeEvent;
import ModelUtils.Command;

public class ValidateCommand implements Command {
    private final Game game;
    private final int verifierIndex;
    private boolean isValidVerification;

    public ValidateCommand(Game game, int verifierIndex, TuringMachine turingMachine) {
        this.game = game;
        this.verifierIndex = verifierIndex;
    }

    public boolean getIsValidVerification() {
        return this.isValidVerification;
    }
    public int getVerifierIndex() {
        return this.verifierIndex;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.VALIDATE_VERIFIER;
    }

    @Override
    public void execute() {
        isValidVerification = game.verify(verifierIndex);
    }

    @Override
    public void unexecute() {
        game.unverify(verifierIndex);
    }
}
