package ecoo.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Justin Rundle
 * @since October 2016
 */
public class FileUtils {

    public static String resolveDir(String name) {
        if (StringUtils.isBlank(name)) {
            return name;
        } else {
            if (!name.endsWith(System.getProperty("file.separator"))) {
                name = name + System.getProperty("file.separator");
            }
            return name;
        }
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "ThrowFromFinallyBlock"})
    public static File copy(String src, String dest, boolean delete) throws IOException {
        File srcFile;
        File destFile;
        FileInputStream from = null;
        FileOutputStream to = null;

        try {
            srcFile = new File(src);
            from = new FileInputStream(srcFile);
            destFile = new File(dest);
            to = new FileOutputStream(destFile);
            byte[] e = new byte[4096];

            while (true) {
                int bytes_read;
                if ((bytes_read = from.read(e)) == -1) {
                    to.flush();
                    break;
                }
                to.write(e, 0, bytes_read);
            }
        } finally {
            if (from != null) {
                try {
                    from.close();
                } catch (IOException e) {
                    if (to != null) {
                        to.close();
                    }
                    throw e;
                }
            }

            if (to != null) {
                try {
                    to.close();
                } catch (IOException e2) {
                    throw e2;
                }
            }
        }

        if (delete && srcFile != null) {
            srcFile.delete();
        }

        return destFile;
    }
}
