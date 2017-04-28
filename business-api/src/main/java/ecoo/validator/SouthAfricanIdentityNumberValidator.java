package ecoo.validator;

import org.apache.commons.lang3.StringUtils;

/**
 * This class validates a South African Identity Number using the following algorithm
 * <p>
 * Calculate total A by adding the figures in the odd positions i.e. the first, third, fifth,
 * seventh, ninth and eleventh digits. Calculate total B by taking the even figures of the number as
 * a whole number, and then multiplying that number by 2, and then add the individual figures
 * together. Calculate total C by adding total A to total B. The control-figure can now be
 * determined by subtracting the ones column from figure C from 10. Where the total C is a multiple
 * of 10, the control figure will be 0.
 *
 * @author Justin Rundle
 * @since April 2017
 */
public class SouthAfricanIdentityNumberValidator implements ValidationDelegate<String> {

    /**
     * South Africa identity number length
     */
    private static final int PROPERTY_SOUTH_AFRICAN_ID_NUMBER_LENGTH = 13;

    /**
     * Returns the boolean representation of the result of the validation of a South African
     * identity number. The following method checks the identity number to see if it is valid. It
     * uses an algorithm provided by South African Department of Home Affairs in order to validate
     * the South African identity numbers.
     *
     * @param identityNumber The identity number to validate.
     */
    @Override
    public final void validate(String identityNumber) throws ValidationFailedException {
        if (identityNumber == null) {
            return;
        }

        identityNumber = StringUtils.stripToEmpty(identityNumber);

        if (identityNumber.trim().length() != PROPERTY_SOUTH_AFRICAN_ID_NUMBER_LENGTH) {
            throw new ValidationFailedException("Value is not correct length for a South African identity number.");
        }

        // Get the control digit
        int controlDigit = Integer.parseInt(String.valueOf((identityNumber
                .charAt(identityNumber.length() - 1))));

        // get all odd positioned numbers
        int first = Integer.parseInt(String.valueOf((identityNumber.charAt(0))));
        int third = Integer.parseInt(String.valueOf((identityNumber.charAt(2))));
        int fifth = Integer.parseInt(String.valueOf((identityNumber.charAt(4))));
        int seventh = Integer.parseInt(String.valueOf((identityNumber.charAt(6))));
        int ninth = Integer.parseInt(String.valueOf((identityNumber.charAt(8))));
        int eleventh = Integer.parseInt(String.valueOf((identityNumber.charAt(10))));

        // get all even positioned numbers

        int second = Integer.parseInt(String.valueOf((identityNumber.charAt(1))));
        int fourth = Integer.parseInt(String.valueOf((identityNumber.charAt(3))));
        int sixth = Integer.parseInt(String.valueOf((identityNumber.charAt(5))));
        int eighth = Integer.parseInt(String.valueOf((identityNumber.charAt(7))));
        int tenth = Integer.parseInt(String.valueOf((identityNumber.charAt(9))));
        int twelfth = Integer.parseInt(String.valueOf((identityNumber.charAt(11))));

        // Calculate total A by adding the figures in the odd positions i.e. the first, third,
        // fifth, seventh, ninth and eleventh digits.
        int controlA = first + third + fifth + seventh + ninth + eleventh;
        String temp = String.valueOf(second) + String.valueOf(fourth) + String.valueOf(sixth)
                + String.valueOf(eighth) + String.valueOf(tenth) + String.valueOf(twelfth);

        // Calculate total B by taking the even figures of the number as a whole number, and then
        // multiplying that number by 2, and then add the individual figures together.
        int controlB = Integer.parseInt(temp);
        controlB = controlB * 2;
        temp = String.valueOf(controlB);
        int[] controlBArray = new int[temp.length()];
        for (int x = 0; temp.length() > x; x++) {
            controlBArray[x] = Integer.parseInt(String.valueOf(temp.charAt(x)));
        }

        controlB = 0;
        for (int aControlBArray : controlBArray) {
            controlB += aControlBArray;
        }

        // Calculate total C by adding total A to total B.
        int controlC = controlA + controlB;

        // The control-figure can now be determined by subtracting the ones column from figure C
        // from 10.
        temp = String.valueOf(controlC);
        temp = String.valueOf(temp.charAt(temp.length() - 1));

        int controlD;

        // If the last letter of control C is equal to zero then control D is zero, else control D
        // is equal to 10 - control C.
        if (Integer.parseInt(temp) == 0) {
            controlD = 0;
        } else {
            controlD = 10 - Integer.parseInt(temp);
        }

        // determine whether the final calculated number is equal to the control digit.
        if (controlD != controlDigit) {
            throw new ValidationFailedException("Value is not valid South African identity number.");
        }
    }
}
