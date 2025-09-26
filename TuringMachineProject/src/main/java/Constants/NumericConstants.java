package Constants;

/**
 * Numeric constants used for the JavaFX view, for resizing purposes.
 */
public enum NumericConstants {
    LABEL_WIDTH(60),
    LABEL_HEIGHT(52),
    LABEL_BORDER_WIDTH(2),
    ERROR_TEXT_DISPLAY_TIME(3),
    CODE_SIZE(3);

    private final int units;

    NumericConstants(int units) {
        this.units = units;
    }

    public int getValue() {
        return units;
    }
}
