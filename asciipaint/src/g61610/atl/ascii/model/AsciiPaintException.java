package g61610.atl.ascii.model;

/**
 * This class is an exception class, we will create this exception everytime one of the game rules gets broken.
 */
public class AsciiPaintException extends RuntimeException {
    public AsciiPaintException(String error_message) {
        super(error_message);
    }
}
