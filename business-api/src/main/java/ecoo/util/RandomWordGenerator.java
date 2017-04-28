package ecoo.util;

import org.apache.commons.lang.RandomStringUtils;

/**
 * @author Justin Rundle
 * @since September 2016
 */
public final class RandomWordGenerator {

    public String[] generate(final int numberOfWords, final int minimumLengthOfWord) {
        final String[] randomStrings = new String[numberOfWords];
        for (int i = 0; i < numberOfWords; i++) {
            randomStrings[i] = RandomStringUtils.random(minimumLengthOfWord, true, true);
        }
        return randomStrings;
    }
}
