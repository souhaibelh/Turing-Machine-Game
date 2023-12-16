package ModelUtils;

import Model.enums.Category;
import Model.Code;

/**
 * A verifier is something that can verify something and return a Category result in the case of TuringMachine game
 */
public interface Verifier {
    Category getResult(Code code);
}
