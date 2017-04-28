package ecoo.data.upload;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class HibernateUtils {

    /**
     * Returns the {@link Integer} representation of the given string value. If the string value is null or an empty string the null is returned.
     *
     * @param val The value to evaluate.
     * @return The integer value of null.
     */
    public static Integer parseInt(String val) {
        if (StringUtils.isBlank(val)) {
            return null;
        }
        return Integer.parseInt(val);
    }

    public static Double parseDouble(String val) {
        if (StringUtils.isBlank(val)) {
            return null;
        }
        return Double.parseDouble(val);
    }
}
