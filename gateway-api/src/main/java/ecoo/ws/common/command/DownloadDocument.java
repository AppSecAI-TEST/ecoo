package ecoo.ws.common.command;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public class DownloadDocument {

    public void execute(HttpServletResponse response, String base64String, String mimeType
            , String fileName) throws IOException {
        OutputStream outStream = null;
        try {
            response.setContentType(mimeType);

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", fileName);
            response.setHeader(headerKey, headerValue);

            outStream = response.getOutputStream();
            outStream.write(Base64.getDecoder().decode(base64String));

        } finally {
            if (outStream != null) outStream.close();
        }
    }
}