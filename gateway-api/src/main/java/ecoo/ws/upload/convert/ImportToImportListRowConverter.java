package ecoo.ws.upload.convert;

import ecoo.data.upload.Upload;
import ecoo.data.upload.UploadStatus;
import ecoo.ws.upload.json.UploadListRow;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Component
public class ImportToImportListRowConverter implements Converter<Upload, UploadListRow> {

    @SuppressWarnings("ConstantConditions")
    @Override
    public UploadListRow convert(Upload anUpload) {
        return new UploadListRow(anUpload.getPrimaryId()
                , anUpload.getFileName()
                , anUpload.getUploadType().getDescription()
                , anUpload.getStatus()
                , UploadStatus.Status.getStatusByPrimaryId(anUpload.getStatus()).getDescription()
                , anUpload.getStartTime()
                , anUpload.getElapsedTime()
                , anUpload.getComment());
    }
}
