package ecoo.data.upload;

import ecoo.builder.TimeDescriptionBuilder;
import ecoo.data.BaseModel;
import ecoo.data.upload.annotation.UploadField;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "upload")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "upload_type", discriminatorType = DiscriminatorType.STRING)
@SuppressWarnings({"rawtypes"})
public abstract class Upload extends BaseModel<Integer> implements RequiredUploadData, Serializable {

    private static final long serialVersionUID = 4305528112354595272L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer primaryId;

    @Column(name = "file_name")
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "map_id")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private RequiredFieldMapping mapping;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "comment")
    private String comment;

    @Transient
    private CsvFile csvFile;

    @Transient
    private File originalUploadFile;

    @Transient
    private File previewUploadFile;

    @Transient
    private Map<String, RequiredField> requiredFieldMap;

    /**
     * Constructs a new {@link Upload} model object.
     */
    public Upload() {
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.model.bulkimport.RequiredImportData#getRequiredFields()
     */
    @Override
    public final Collection<RequiredField> getRequiredFields() {
        return getRequiredFieldMap().values();
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.model.bulkimport.RequiredImportData#getRequiredFieldMap()
     */
    @Override
    public final Map<String, RequiredField> getRequiredFieldMap() {
        if (requiredFieldMap == null) {
            requiredFieldMap = new LinkedHashMap<>();

            Class<?> dataClass = getUploadType().getDataClass();
            for (Field field : dataClass.getDeclaredFields()) {
                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }

                final UploadField uploadField = field.getAnnotation(UploadField.class);
                if (uploadField == null) {
                    continue;
                }

                Method getter = null;
                Method setter = null;
                for (Method m : dataClass.getMethods()) {
                    if (m.getName().equalsIgnoreCase("get" + field.getName())) {
                        getter = m;
                    } else if (m.getName().equalsIgnoreCase("set" + field.getName())) {
                        setter = m;
                    }

                    if (getter != null && setter != null) {
                        break;
                    }
                }

                if (getter == null) {
                    throw new IllegalArgumentException(String.format("no getter found for field \"%s\".",
                            field.getName()));
                }

                if (setter == null) {
                    throw new IllegalArgumentException(String.format("no setter found for field \"%s\".",
                            field.getName()));
                }
                final Method g = getter;
                final Method s = setter;

                for (RequiredField f : requiredFieldMap.values()) {
                    if (f.getExportOrder() == uploadField.fieldId()) {
                        throw new IllegalArgumentException(String.format("field id \"%s\" assigned "
                                + "to field  \"%s\" already assigned.", f.getExportOrder(), field.getName()));
                    }
                }

                RequiredField requiredField = new RequiredField(column.name(), uploadField.fieldId(),
                        uploadField.fieldType()) {

                    @Override
                    public final String getFieldValueIn(Object data) {
                        try {
                            Object val = g.invoke(data);
                            return val == null ? "" : val.toString();

                        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            return null;

                        }
                    }

                    @Override
                    public final void setFieldValueFor(Object data, String newValue) {
                        try {
                            s.invoke(data, newValue);

                        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();

                        }
                    }
                };
                requiredFieldMap.put(requiredField.getName(), requiredField);
            }
        }
        return requiredFieldMap;
    }

    public boolean isStatus(UploadStatus.Status status) {
        return getStatus() != null && getStatus().equals(status.getPrimaryId());
    }

    /**
     * Returns the {@link String} representation of the time difference between the start time and
     * end time, i.e.: 6d = 6 days.
     *
     * @return the time difference
     */
    public String getElapsedTime() {
        if (this.startTime == null || this.endTime == null) return "";
        return TimeDescriptionBuilder.aTimeDescription()
                .withStartTime(this.startTime)
                .witEvaluationDate(this.endTime)
                .build();
    }

    /**
     * Returns the {@link Boolean} representation if the upload is complete.
     *
     * @return true if complete
     */
    public boolean isCompleted() {
        return isStatus(UploadStatus.Status.UploadSuccessful) || isStatus(UploadStatus.Status.UploadPartial);
    }

    /**
     * Returns the {@link Boolean} representation if the upload has failed.
     *
     * @return true if failed
     */
    public boolean isFailed() {
        return isStatus(UploadStatus.Status.UploadFailed) || isStatus(UploadStatus.Status.ParsingFailed)
                || isStatus(UploadStatus.Status.ImportFailed);
    }

    /**
     * Returns the {@link UploadType} representation of this upload.
     *
     * @return the upload type
     */
    public abstract UploadType.Type getUploadType();

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.model.BaseModel#getPrimaryId()
     */
    @Override
    public Integer getPrimaryId() {
        return primaryId;
    }

    /**
     * @return the uploadFile
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @return the reason
     */
    public String getComment() {
        return comment;
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.model.BaseModel#setPrimaryId(java.lang.Object)
     */
    @Override
    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    /**
     * @param fileName the uploadFile to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the mapping
     */
    public RequiredFieldMapping getMapping() {
        return mapping;
    }

    /**
     * @param mapping the mapping to set
     */
    public void setMapping(RequiredFieldMapping mapping) {
        if (mapping != null && mapping.isNew()) {
            mapping.setUploadTypeId(getUploadType().getPrimaryId());
        }

        if (getCsvFileToUpload() != null) {
            getCsvFileToUpload().setMapping(mapping);
        }

        this.mapping = mapping;
    }

    public CsvFile getCsvFileToUpload() {
        return csvFile;
    }

    public void setCsvFileToUpload(CsvFile csvFile) {
        this.csvFile = csvFile;
    }

    public File getOriginalUploadFile() {
        return originalUploadFile;
    }

    public void setOriginalUploadFile(File originalUploadFile) {
        if (originalUploadFile == null) {
            setFileName(null);
        } else {
            setFileName(originalUploadFile.getName());
        }
        this.originalUploadFile = originalUploadFile;
    }

    public File getPreviewUploadFile() {
        return previewUploadFile;
    }

    public void setPreviewUploadFile(File previewUploadFile) {
        this.previewUploadFile = previewUploadFile;
    }

    @Override
    public String toString() {
        return "Upload{" +
                "primaryId=" + primaryId +
                ", fileName='" + fileName + '\'' +
                ", mapping=" + mapping +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", comment='" + comment + '\'' +
                ", csvFile=" + csvFile +
                ", originalUploadFile=" + originalUploadFile +
                ", previewUploadFile=" + previewUploadFile +
                ", requiredFieldMap=" + requiredFieldMap +
                '}';
    }
}
