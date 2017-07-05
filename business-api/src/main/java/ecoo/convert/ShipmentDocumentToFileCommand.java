package ecoo.convert;

import ecoo.data.ShipmentDocument;

import java.io.*;
import java.util.Base64;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public final class ShipmentDocumentToFileCommand {

    public File execute(ShipmentDocument shipmentDocument, String tempDir) throws IOException {
        final File srcFile = new File(tempDir, shipmentDocument.getFileName());

        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(srcFile));

            final byte[] data = Base64.getDecoder().decode(shipmentDocument.getData());
            out.write(data);

        } finally {
            if (out != null) out.close();
        }

        return srcFile;
    }
}
