package Model.verifiers;

import Model.enums.Category;
import Model.Code;
import Model.TuringMachineVerifier;

public class OccurrenceVerifier extends TuringMachineVerifier {
    private final int value;

    public OccurrenceVerifier(int id, int value) {
        super(id, "Counts how many times the value " + value + " appears in the code");
        this.value = value;
    }

    @Override
    public Category getResult(Code code) {
        long valueRepetitions = code.getDigitContainer().stream().filter(digit -> digit == value).count();
        return Category.getRepetitionCategory(valueRepetitions);
    }

}
