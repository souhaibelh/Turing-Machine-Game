package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Defines what a round is in TuringMachine game
 */
public class Round extends Consumable {
    private final Level level;
    private final List<TuringMachineVerifier> consumedVerifiers;
    private Code userCode;

    /**
     * Constructor takes in a level and constructs a round with it
     * @param level level from which we construct the round
     */
    public Round(Level level) {
        this.level = level;
        consumedVerifiers = new ArrayList<>();
    }

    /**
     * Gives a protected copy of the list
     * @return unmodifiable list of consumed verifiers
     */
    public List<TuringMachineVerifier> getConsumedVerifiers() {
        return Collections.unmodifiableList(this.consumedVerifiers);
    }

    /**
     * Attaches the user code to the round, will be used later on to verify it with the round's code. Remember
     * a round can start whenever, and a user can choose the code after.
     * @param code code submitted by the user
     */
    public void setUserCode(Code code) {
        this.userCode = code;
    }

    /**
     * Returns the user code
     * @return user code
     */
    public Code getUserCode() {
        return userCode;
    }

    /**
     * This method verifies a verifier of the round
     * @param verifierIndex the index of the verifier
     * @return a boolean containing the result of the verification
     */
    public boolean verify(int verifierIndex) {
        TuringMachineVerifier verifier = getVerifierByIndex(verifierIndex); // we give the verifier index, and it gives us the verifier from the level
        if (consumedVerifiers.size() >= 3) { // if 3 or more than 3 verifiers are consumed throw an exception
            throw new TuringMachineException("You have already used 3 validators in this round");
        } else if (userCode == null) { // if the code of the user is null it means he hasn't added a code yet, throw an exception
            throw new TuringMachineException("Add a code to the round before verifying!");
        }

        boolean result = verifier.getResult(userCode) == verifier.getResult(level.getCode()); // store the result computed by the verifiers
        verifier.consume(); // consume the verifier
        consumedVerifiers.add(verifier); // add the verifier to the consumedVerifiers list (keeps track of it)
        return result; // return the result computer by the verifiers
    }

    /**
     * Unverified a verifier, method only used with the undo redo, if we unverified a verifier it means it was verified
     * and therefore there is no need to make checks.
     * @param verifierIndex index of the verifier to unverified
     */
    public void unVerify(int verifierIndex) {
        TuringMachineVerifier verifier = getVerifierByIndex(verifierIndex);
        verifier.unconsume();
    }

    /**
     * Returns a verifier with a given index
     * @param index index of the verifier
     * @return the verifier
     */
    public TuringMachineVerifier getVerifierByIndex(int index) {
        if (index < 0 || index > 5) { // if the verifier is not within the range 0-5 throw an exception
            throw new TuringMachineException("Invalid verifier index: " + index);
        } else if (index > level.getVerifiers().size() - 1) {
            throw new TuringMachineException("You can't use this verifier on this level!");
        }
        return level.getVerifiers().get(index); // return the verifier in the level
    }
}
