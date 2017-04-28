package ecoo.service;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface DocumentService {

    /**
     * Returns the {@link String} representation of the path for the given certificate.
     *
     * @param certificateId The certificate pk.
     * @return The path or directory.
     */
    String certificateOfOriginPath(Integer certificateId);

}
