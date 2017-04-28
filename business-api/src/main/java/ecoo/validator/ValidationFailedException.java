package ecoo.validator;


/**
 * A {@link ValidationFailedException} is thrown when the validation failed.
 *
 * @author Justin Rundle
 * @since April 2017
 */
public class ValidationFailedException extends RuntimeException {

    private static final long serialVersionUID = 7005494099498830669L;

    /**
     * Create a new instance of {@link ValidationFailedException}.
     *
     * @param message
     */
    public ValidationFailedException(String message) {
        super(message);
    }
}
