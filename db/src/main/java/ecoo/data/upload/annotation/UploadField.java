package ecoo.data.upload.annotation;

import ecoo.data.upload.RequiredField;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UploadField {

    /**
     * This attribute will be used to set the field id corresponding to the xml file.
     *
     * @return fieldId
     */
    int fieldId();

    /**
     * The attribute used to determine the parser associated to this field.
     *
     * @return fieldType
     */
    RequiredField.FieldType fieldType() default RequiredField.FieldType.Basic;
}
