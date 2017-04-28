package ecoo.util;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class BusinessRuleViolationException extends RuntimeException {

    private static final long serialVersionUID = -3627361219981541481L;

    public BusinessRuleViolationException(String message) {
        super(message);
    }
}
