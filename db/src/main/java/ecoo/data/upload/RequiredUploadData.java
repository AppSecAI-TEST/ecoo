package ecoo.data.upload;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface RequiredUploadData<M extends UploadData> {

    /**
     * Returns the comparator user to sort the require field row data.
     *
     * @return the comparator
     */
    Comparator<M> getRequiredFieldRowComparator();

    /**
     * Returns the {@link Map} representation of the required fields keyed by the name of the
     * column.
     *
     * @return the
     * @see RequiredField
     * @see UploadData
     */
    Map<String, RequiredField<M>> getRequiredFieldMap();

    /**
     * Returns a {@link Collection} of the required fields that are defined in the XML mapping file
     * as required.
     *
     * @return Collection
     * @see RequiredField
     * @see UploadData
     */
    Collection<RequiredField<M>> getRequiredFields();
}
