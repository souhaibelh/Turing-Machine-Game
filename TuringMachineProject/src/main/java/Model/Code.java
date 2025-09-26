package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class defines what a Code is in the Turing Machine Game.
 */
public class Code {
    private final int codeSize = 3;
    private final List<Integer> digitContainer;

    /**
     * Constructor that constructs the code, takes in parameters a String representing the digits that the Code will contain,
     * it verifies with the codeSize our digit must have to see if the player gave the correct amount of digits or no.
     * @param code a String with only digits that represents the code
     */
    public Code(String code) {
        validateCode(code); // validates the code
        digitContainer = new ArrayList<>(); // starts the Integer list that will contain each digit
        for (int i=0; i<code.length(); i++) { // Iterates through each character in the code passed as parameters
            Integer digit = Integer.parseInt(String.valueOf(code.charAt(i))); // creates a Integer of equal value
            validateIndex(digit, 1, 5, "Code digits must be between 1 and 5: " + digit); // validates that it is a valid digit from 1 to 5
            digitContainer.add(digit); // finally adds it to the digitContainer list
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Code otherCode = (Code) o;
        return Objects.equals(digitContainer, otherCode.digitContainer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(digitContainer);
    }

    public List<Integer> getDigitContainer() {
        return Collections.unmodifiableList(digitContainer);
    }

    /**
     * Returns a digit from the list with a given index.
     * @param position index of the digit
     * @return the digit at that index
     */
    public Integer getDigit(int position) {
        validateIndex(position, 0, codeSize, "Can't get a digit out of bounds: " + position);
        return digitContainer.get(position);
    }

    /**
     * General method to validate indexes, takes an index, a minimum and a maximum and a message
     * if the index is smaller than the minimum or bigger than the maximum it throws a TuringMachineException
     * with the message given in parameters.
     * @param n index
     * @param min min value for the index
     * @param max max value for the index
     * @param message error String in case the index is not valid
     */
    private void validateIndex(Integer n, int min, int max, String message) {
        if (n < min || n > max) {
            throw new TuringMachineException(message);
        }
    }

    /**
     * Method used to validate the code passed in parameters in the constructor is valid, the code length
     * can't be different from the codeSize set by the game (in this case 3) and it has to be a number.
     * @param code string representation of the code
     */
    private void validateCode(String code) {
        if (code.length() != codeSize) {
            throw new TuringMachineException("Code must be composed of: " + codeSize + " digits.");
        }
        if (!code.matches("[0-9]+")) {
            throw new TuringMachineException("Code must be exclusively composed of digits");
        }
    }

    @Override
    public String toString() {
        String code = "";
        for (Integer i : digitContainer) {
            code += String.valueOf(i);
        }
        return code;
    }
}
