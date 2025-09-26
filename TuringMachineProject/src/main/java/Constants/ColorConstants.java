package Constants;

/**
 * Color codes used throughout the game
 */
public enum ColorConstants {
    RIGHT("#2db563"),
    WRONG("#e3412f");

    private final String colorCode;

    private ColorConstants(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }
}
