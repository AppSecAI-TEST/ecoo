package ecoo.ws.upload.convert;

import ecoo.data.upload.*;
import ecoo.ws.upload.json.NewUploadRequest;
import io.jsonwebtoken.lang.Assert;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public class NewUploadRequestToImportConverter implements Converter<NewUploadRequest, Upload> {

    @Override
    public Upload convert(NewUploadRequest newUploadRequest) {
        validateMapping(newUploadRequest.getRequiredField());

        final File csvFileToUpload = new File(newUploadRequest.getPreviewCsvFile().getAbsolutePath());
        if (!csvFileToUpload.exists()) {
            throw new IllegalArgumentException(String.format("File %s doest not exist.", csvFileToUpload.getAbsolutePath()));
        }

        final UploadType.Type uploadType = UploadType.Type.getTypeByPrimaryId(newUploadRequest.getUploadTypeId());

        final Upload anImport = createUpload(uploadType);
        anImport.setShipmentId(newUploadRequest.getShipmentId());

        anImport.setStatus(UploadStatus.Status.Ready.getPrimaryId());
        anImport.setOriginalUploadFile(csvFileToUpload);
        try {
            anImport.setCsvFileToUpload(new CsvFile(csvFileToUpload));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        anImport.setMapping(newUploadRequest.getRequiredField());

        return anImport;
    }

    private Upload createUpload(UploadType.Type uploadType) {
        switch (uploadType) {
            case COMMERCIAL_INVOICE:
                return new CommercialInvoiceUpload();
            case CERTIFICATE_OF_ORIGIN:
                return new CertificateOfOriginUpload();
            default:
                throw new DataIntegrityViolationException(String.format("No upload class "
                        + "defined for upload type \"%s\".", uploadType.name()));
        }
    }


    private void validateMapping(RequiredFieldMapping requiredField) {
        Assert.notNull(requiredField);
        final Map<String, Integer> columnsCountMap = new HashMap<>();
        for (RequiredFieldMappingItem requiredFieldMappingItem : requiredField.getMappingItems()) {
            final String columnName = requiredFieldMappingItem.getTableColumnName();
            if (!columnsCountMap.containsKey(columnName)) {
                columnsCountMap.put(columnName, 0);
            }
            int count = columnsCountMap.get(columnName);
            int newCount = count + 1;

            columnsCountMap.put(columnName, newCount);
        }

        for (final String columnName : columnsCountMap.keySet()) {
            if (columnsCountMap.get(columnName) > 1) {
                throw new DataIntegrityViolationException(String.format("Column %s contains multiple mappings. Each " +
                        "column in the upload file can only by mapping once.", columnName));
            }
        }
    }
}
