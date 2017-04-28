package ecoo.ws.common.command;

import au.com.bytecode.opencsv.CSVWriter;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class DownloadRowDataCommand {

    public void execute(HttpServletResponse response, List<List<String>> rowData) throws IOException {
        Assert.notNull(rowData, "The rowData is required.");

        response.setContentType("text/plain");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + DateTime.now().toString("yyyyMMddHHmmss") + ".txt";
        response.setHeader(headerKey, headerValue);

        CSVWriter csvWriter = null;
        try {
            csvWriter = new CSVWriter(response.getWriter(), '|', CSVWriter.NO_QUOTE_CHARACTER
                    , CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            for (final List<String> line : rowData) {
                csvWriter.writeNext(line.toArray(new String[line.size()]));
            }
        } finally {
            if (csvWriter != null) {
                csvWriter.flush();
                csvWriter.close();
            }
        }
    }
}