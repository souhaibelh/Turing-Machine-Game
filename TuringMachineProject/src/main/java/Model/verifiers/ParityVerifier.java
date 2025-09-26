package Model.verifiers;

import Model.enums.Category;
import Model.Code;
import Model.TuringMachineVerifier;

import static Model.enums.Category.getParityCategory;

public class ParityVerifier extends TuringMachineVerifier {
    private final Integer digitPosition;

    public ParityVerifier(int id, int digitPosition) {
        super(id, "Verifies the parity of the digit at position " + digitPosition);
        this.digitPosition = digitPosition;
    }

    @Override
    public Category getResult(Code code) {
        return getParityCategory(code.getDigit(digitPosition) % 2);
    }
}
