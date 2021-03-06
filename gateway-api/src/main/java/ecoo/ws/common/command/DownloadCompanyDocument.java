package ecoo.ws.common.command;

import ecoo.data.CompanyDocument;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public class DownloadCompanyDocument {

    public void execute(HttpServletResponse response, CompanyDocument document) throws IOException {
        final DownloadDocument downloadDocument = new DownloadDocument();
        downloadDocument.execute(response, document.getEncodedImage()
                , document.getMimeType(), document.getFileName());
    }
}