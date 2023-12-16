package Model.enums;

/**
 * This enum defines the results of the verifier, it will be used to compare both categories of the user code and machine code
 * and see if they match.
 */
public enum Category {
    NONE(),
    ASCENDING(),
    DESCENDING(),
    SMALLER(),
    EQUAL(),
    BIGGER(),
    ODD(),
    EVEN(),
    ZERO_REPETITIONS(),
    ONE_REPETITIONS(),
    TWO_REPETITIONS(),
    THREE_REPETITIONS(),
    NO_DIGIT(),
    FIRST_DIGIT(),
    SECOND_DIGIT(),
    THIRD_DIGIT();

    /**
     * Takes an integer and interprets it, then returns the Category specified to it, only works in equality. Only for equality
     * @param x integer to be interpreted
     * @return the category that corresponds to it
     */
    public static Category getEqualityCategory(int x) {
        if (x < 0) {
            return SMALLER;
        } else if (x > 0) {
            return BIGGER;
        }
        return EQUAL;
    }

    /**
     * Takes a double and returns its representation of a Category. Only for parity.
     * @param x double to be interpreted
     * @return category that this double refers to (interpreted by the developer as such)
     */
    public static Category getParityCategory(double x) {
        return x == 0 ? EVEN : ODD;
    }

    /**
     * Takes a long and interprets it, then returns the corresponding Category. Only for repetitions.
     * @param x long to be interpreted
     * @return Category that is interpreted with this long
     */
    public static Category getRepetitionCategory(long x) {
        return switch ((int) x) {
            case 0 -> ZERO_REPETITIONS;
            case 1 -> ONE_REPETITIONS;
            case 2 -> TWO_REPETITIONS;
            case 3 -> THREE_REPETITIONS;
            default -> null;
        };
    }

    /**
     * Takes an integer and interprets it, then returns the corresponding category. Only for extremums.
     * @param x integer to be interpreted
     * @return category the developer decided it corresponds to
     */
    public static Category getExtremumCategory(int x) {
        return switch (x) {
            case -1 -> NO_DIGIT;
            case 0 -> FIRST_DIGIT;
            case 1 -> SECOND_DIGIT;
            case 2 -> THIRD_DIGIT;
            default -> null;
        };
    }
}
