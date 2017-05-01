package ecoo.data;

import org.springframework.util.Assert;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class ChamberGroupIdentityFactory {
    public static String build(Integer chamberId) {
        Assert.notNull(chamberId, "The variable chamberId cannot be null.");
        return "CHAMBER_" + chamberId;
    }
}