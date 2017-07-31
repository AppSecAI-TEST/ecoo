package ecoo.ws.common.command;

import ecoo.data.UserDocument;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public class DownloadUserDocument {

    public void execute(HttpServletResponse response, UserDocument document) throws IOException {
        final DownloadDocument downloadDocument = new DownloadDocument();
        downloadDocument.execute(response, document.getEncodedImage()
                , document.getMimeType(), document.getFileName());
    }
}