package ecoo.ws.common.command;

import ecoo.data.CompanyDocument;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public class DownloadCompanyDocument {

    public void execute(HttpServletResponse response, CompanyDocument companyDocument) throws IOException {
        OutputStream outStream = null;
        try {
            final String base64String = companyDocument.getEncodedImage();
            response.setContentType(companyDocument.getMimeType());

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"",
                    companyDocument.getFileName());
            response.setHeader(headerKey, headerValue);

            outStream = response.getOutputStream();
            outStream.write(Base64.getDecoder().decode(base64String));

        } finally {
            if (outStream != null) outStream.close();
        }
    }
}