package ecoo.validator;

/**
 * {@link ValidationDelegate}'s are used by components to validate that relevant value.
 *
 * @param <T> - The type of the value that is to be validated.
 * @author Justin Rundle
 * @since April 2017
 */
public interface ValidationDelegate<T> {
    /**
     * This method is called when the relevant value object is to be validated by delegate.
     * Implemetation should ensure that a {@link ValidationFailedException} is throw if validation
     * fails, otherwise the method should just exit.
     *
     * @param value The value to validate.
     * @throws ValidationFailedException - If the validation failed.
     */
    void validate(T value) throws ValidationFailedException;
}
