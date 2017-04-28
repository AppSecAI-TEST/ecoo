package ecoo.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

/**
 * All data read from the database will be wrapped by a BaseModel class. More so the
 * BaseModel class is used to wrap the data into a POJO (Plain Old Java Object).
 * <T> T the data type of the primary key.
 *
 * @author Justin Rundle
 */
public abstract class BaseModel<T> implements Serializable {

    private static final long serialVersionUID = 1084383628554677231L;

    /**
     * Returns the unique identifier of the object.
     *
     * @return The primary key value.
     */
    public abstract T getPrimaryId();

    /**
     * Set the unique identifier for the object.
     *
     * @param primaryId The primary key to set.
     */
    public abstract void setPrimaryId(T primaryId);

    /**
     * Returns the {@link Boolean} representation if the model has been persisted.
     *
     * @return true if unsaved
     */
    @JsonIgnore
    public final boolean isNew() {
        return Objects.isNull(getPrimaryId());
    }


    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return <code>true</code> if this object is the same as the obj argument; <code>false</code>
     * otherwise.
     * @see java.util.Hashtable
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseModel<?>)) {
            return false;
        }

        BaseModel<?> otherModel = (BaseModel<?>) obj;
        if (getPrimaryId() != null && otherModel.getPrimaryId() != null) {
            return Objects.equals(otherModel.getPrimaryId(), getPrimaryId());
        } else {
            return super.equals(obj);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        if (getPrimaryId() != null) {
            return Objects.hashCode(getPrimaryId());
        } else {
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
