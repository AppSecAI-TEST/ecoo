package ecoo.ws.common.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class DownloadFileCommand {

    private static final Logger LOG = LoggerFactory.getLogger(DownloadFileCommand.class);

    public void execute(HttpServletResponse response, ServletContext context, String fileName) throws IOException {
        final File downloadFile = new File(fileName);
        FileInputStream inputStream = null;
        OutputStream outStream = null;
        try {
            inputStream = new FileInputStream(downloadFile);

            // get MIME type of the file
            String mimeType = context.getMimeType(fileName);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            LOG.info("{} MIME type {}", fileName, mimeType);

            // set content attributes for the response
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            // set headers for the response
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"",
                    downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            // get output stream of the response
            outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;

            // write bytes read from the input stream into the output stream
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

        } finally {
            if (inputStream != null) inputStream.close();
            if (outStream != null) outStream.close();
        }
    }
}