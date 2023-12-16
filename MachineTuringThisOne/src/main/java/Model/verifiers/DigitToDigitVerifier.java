package Model.verifiers;

import Model.enums.Category;
import Model.Code;
import Model.TuringMachineVerifier;

public class DigitToDigitVerifier extends TuringMachineVerifier {
    private final Integer firstPosition;
    private final Integer secondPosition;

    public DigitToDigitVerifier(int id, int firstPosition, int secondPosition) {
        super(id, "Compares Digit at position " + firstPosition + " with the digit at position " + secondPosition);
        this.firstPosition = firstPosition;
        this.secondPosition = secondPosition;
    }

    @Override
    public Category getResult(Code code) {
        return Category.getEqualityCategory(code.getDigit(firstPosition).compareTo(code.getDigit(secondPosition)));
    }
}
