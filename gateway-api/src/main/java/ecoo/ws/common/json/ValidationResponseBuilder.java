package ecoo.ws.common.json;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class ValidationResponseBuilder {

    private boolean valid = true;

    private String message = null;

    private ValidationResponseBuilder() {
    }

    public static ValidationResponseBuilder aValidationResponse() {
        return new ValidationResponseBuilder();
    }

    public ValidationResponseBuilder withValid(boolean valid) {
        this.valid = valid;
        return this;
    }

    public ValidationResponseBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public ValidationResponse build() {
        return new ValidationResponse(valid, message);
    }
}
