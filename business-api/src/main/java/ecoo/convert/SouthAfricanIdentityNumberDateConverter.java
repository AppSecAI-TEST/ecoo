package ecoo.convert;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since September 2016
 */
public final class SouthAfricanIdentityNumberDateConverter implements Converter<String, Date> {

    /**
     * South Africa identity number length
     */
    private static final int PROPERTY_SOUTH_AFRICAN_ID_NUMBER_LENGTH = 13;

    /**
     * South Africa identity number date pattern format.
     */
    private static final String PROPERTY_SOUTH_AFRICAN_ID_PATTERN = "yyMMdd";

    /**
     * Year 2000 String *
     */
    private static final String YEAR_2000 = "2000";

    /**
     * Year 21900 String *
     */
    private static final String YEAR_1900 = "1900";

    @Override
    public Date convert(final String southAfricanIdNumber) {
        if (southAfricanIdNumber == null) {
            return null;
        }

        if (southAfricanIdNumber.trim().length() != PROPERTY_SOUTH_AFRICAN_ID_NUMBER_LENGTH) {
            return null;
        }

        SimpleDateFormat twoDigitYearDateFormat = new SimpleDateFormat(PROPERTY_SOUTH_AFRICAN_ID_PATTERN);
        SimpleDateFormat yearDateFormat = new SimpleDateFormat("yyyy");

        int offset = PROPERTY_SOUTH_AFRICAN_ID_PATTERN.length();

        try {
            Date dateOfBirth = twoDigitYearDateFormat.parse(southAfricanIdNumber.substring(0, offset));
            Date startDate = yearDateFormat.parse(YEAR_2000);
            Date endDate = new Date();

            // The following code checks if a date is between the year 2000 and the
            // current date. If
            // this is true then the twoYearDateFormat is set to the current century
            // other wise it is
            // set to the last century this obviously introduces a limitation to the
            // system that only
            // allows people under a hundred years of age to be added. This code
            // will also only last
            // until the year 2099 but then again i doubt this code will be around
            // by then :)
            if (dateOfBirth.after(startDate) && dateOfBirth.before(endDate)) {
                twoDigitYearDateFormat.set2DigitYearStart(yearDateFormat.parse(YEAR_2000));
            } else {
                twoDigitYearDateFormat.set2DigitYearStart(yearDateFormat.parse(YEAR_1900));
            }
            return twoDigitYearDateFormat.parse(southAfricanIdNumber.substring(0, offset));

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
