package ecoo.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Justin Rundle
 * @since September 2016
 */
public final class HashGenerator {

    private static final String CHARSET = "UTF-8";

    /**
     * Returns the converted message digest value in base 16 (hex).
     *
     * @param algorithm The standard name of the digest algorithm.
     * @param word      The word to has.
     * @return The converted message digest value in base 16 (hex).
     * @throws NoSuchAlgorithmException If the hash algorithm does not exist.
     */
    public String digest(final String algorithm, final String word) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        byte[] hash = messageDigest.digest(word.getBytes(CHARSET));

        //converting byte array to Hexadecimal
        final StringBuilder sb = new StringBuilder(2 * hash.length);
        for (final byte b : hash) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
