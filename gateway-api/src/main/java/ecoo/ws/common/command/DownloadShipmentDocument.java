package ecoo.ws.common.command;

import ecoo.data.ShipmentDocument;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

/**
 * @author Justin Rundle
 * @since June 2017
 */
public class DownloadShipmentDocument {

    public void execute(HttpServletResponse response, ShipmentDocument shipmentDocument) throws IOException {
        OutputStream outStream = null;
        try {
            final String base64String = shipmentDocument.getData();
            response.setContentType(shipmentDocument.getMimeType());

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"",
                    shipmentDocument.getFileName());
            response.setHeader(headerKey, headerValue);

            outStream = response.getOutputStream();
            outStream.write(Base64.getDecoder().decode(base64String));

        } finally {
            if (outStream != null) outStream.close();
        }
    }
}