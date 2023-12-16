package Model.verifiers;

import Model.enums.Category;
import Model.Code;
import Model.TuringMachineVerifier;

public class DigitValueVerifier extends TuringMachineVerifier {
    private final Integer value;
    private final Integer digitPosition;

    public DigitValueVerifier(int id, int value, int digitPosition) {
        super(id, "Compares Digit at position " + digitPosition + " with the value " + value);
        this.value = value;
        this.digitPosition = digitPosition;
    }

    @Override
    public Category getResult(Code code) {
        return Category.getEqualityCategory(code.getDigit(digitPosition).compareTo(value));
    }
}
