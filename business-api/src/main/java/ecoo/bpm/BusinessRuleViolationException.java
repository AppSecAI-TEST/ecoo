package ecoo.bpm;

/**
 * @author Justin Rundle
 * @since January 2017
 */
public class BusinessRuleViolationException extends RuntimeException {

    private static final long serialVersionUID = -6862472081861358929L;

    public BusinessRuleViolationException(String message) {
        super(message);
    }
}
