package Model.verifiers;

import Model.enums.Category;
import Model.Code;
import Model.TuringMachineVerifier;

import java.util.List;

public class ExtremumVerifier extends TuringMachineVerifier {
    private final boolean strictMin;
    public ExtremumVerifier(int id, boolean strictMin) {
        super(id, strictMin ? "Determines which digit is strictly the smallest" : "Determines which digit is extremely the biggest");
        this.strictMin = strictMin;
    }

    @Override
    public Category getResult(Code code) {
        int digit = 0;
        List<Integer> allDigits = code.getDigitContainer();
        for (int i=0; i<allDigits.size(); i++) {
            for (int j=i+1; j<allDigits.size(); j++) {
                if (allDigits.get(i).equals(allDigits.get(j))) {
                    return Category.getExtremumCategory(-1);
                }
            }
            if (strictMin && allDigits.get(i) < allDigits.get(digit)) {
                digit = i;
            } else if (!strictMin && allDigits.get(i) > allDigits.get(digit)) {
                digit = i;
            }
        }
        return Category.getExtremumCategory(digit);
    }
}
