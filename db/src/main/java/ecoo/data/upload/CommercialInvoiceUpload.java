package ecoo.data.upload;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Comparator;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Entity
@DiscriminatorValue(value = "CI")
public class CommercialInvoiceUpload extends Upload {

    private static final long serialVersionUID = 8126965840615013635L;

    @Transient
    private Comparator<CommercialInvoiceUploadData> comparator;

    /**
     * Constructs a new {@link CommercialInvoiceUpload} upload model.
     */
    public CommercialInvoiceUpload() {
    }

    @Override
    public UploadType.Type getUploadType() {
        return UploadType.Type.COMMERCIAL_INVOICE;
    }


    @Override
    public Comparator<CommercialInvoiceUploadData> getRequiredFieldRowComparator() {
        if (comparator == null) {
            comparator = (o1, o2) -> {
                int compareVal = 0;
                if (o1.getProductCode() != null && o2.getProductCode() != null) {
                    compareVal = o1.getProductCode().compareTo(o2.getProductCode());
                    if (compareVal == 0) {
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
                    }
                } else if (o1.getProductCode() == null && o2.getProductCode() != null) {
                    compareVal = 1;
                } else if (o1.getProductCode() == null && o2.getProductCode() != null) {
                    compareVal = -1;
                }
                return compareVal;
            };
        }
        return comparator;
    }
}
