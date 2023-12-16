package GeneralMethods;

import java.util.Random;

/**
 * Math class used to do necessary math calculations for our game
 */
public class MathMethods {

    /**
     * Method that returns random integer between 0 and the max set in parameters exclusive, for example:
     * input: 15, return: a random number between 0 and 14
     * @param max max number to be generated (exclusive)
     * @return random number between 0 and max (non inclusive)
     */
    public static int getRandomNumber(int max) {
        Random rnumber = new Random();
        return rnumber.nextInt(max);
    }
}
