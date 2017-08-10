package ecoo.data.upload;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Comparator;

/**
 * @author Justin Rundle
 * @since August 2017
 */
@Entity
@DiscriminatorValue(value = "COO")
public class CertificateOfOriginUpload extends Upload {

    private static final long serialVersionUID = 1225836176948022076L;

    @Transient
    private Comparator<CertificateOfOriginUploadData> comparator;

    /**
     * Constructs a new {@link CertificateOfOriginUpload} upload model.
     */
    public CertificateOfOriginUpload() {
    }

    @Override
    public UploadType.Type getUploadType() {
        return UploadType.Type.CERTIFICATE_OF_ORIGIN;
    }


    @SuppressWarnings("Duplicates")
    @Override
    public Comparator<CertificateOfOriginUploadData> getRequiredFieldRowComparator() {
        if (comparator == null) {
            comparator = (o1, o2) -> {
                int compareVal = 0;
                if (o1.getMarks() != null && o2.getMarks() != null) {
                    compareVal = o1.getMarks().compareTo(o2.getMarks());
                    if (compareVal == 0) {
                        compareVal = o1.getPrimaryId().compareTo(o2.getPrimaryId());
                    }
                } else if (o1.getMarks() == null && o2.getMarks() != null) {
                    compareVal = 1;
                } else if (o1.getMarks() == null && o2.getMarks() != null) {
                    compareVal = -1;
                }
                return compareVal;
            };
        }
        return comparator;
    }
}
