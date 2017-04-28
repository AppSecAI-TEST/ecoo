package ecoo.service.impl.ssrs;

import com.sqlserver.ssrs.reportingservice2005.client.ReportingExecutionClientService;
import com.sqlserver.ssrs.reportingservice2005.client.impl.ReportingExecutionClientServiceImpl;
import ecoo.service.ReportService;

import java.net.MalformedURLException;
import java.util.Map;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class MicrosoftSQLServerReportingServiceImpl implements ReportService {

    private final static String DEFAULT_WSDL_LOCATION = "http://localhost/ReportServer/reportExecution2005.asmx?wsdl";

    private final ReportingExecutionClientService reportingExecutionClientService;


    public MicrosoftSQLServerReportingServiceImpl(String username, String password) throws MalformedURLException {
        this(DEFAULT_WSDL_LOCATION, username, password);
    }

    public MicrosoftSQLServerReportingServiceImpl(String wsdlLocation, String username, String password) throws MalformedURLException {
        this.reportingExecutionClientService = new ReportingExecutionClientServiceImpl(wsdlLocation
                , username, password);
    }

    /**
     * Execute the given SSRS report.
     *
     * @param report The report name with the folder path.
     * @return The content of the report.
     */
    @Override
    public byte[] execute(String report) {
        return reportingExecutionClientService.execute(report);
    }

    /**
     * Execute the given SSRS report.
     *
     * @param report The report name with the folder path.
     * @param name   The parameter name.
     * @param value  The parameter value.
     * @return The content of the report.
     */
    @Override
    public byte[] execute(String report, String name, String value) {
        return reportingExecutionClientService.execute(report, name, value);
    }

    /**
     * Execute the given SSRS report.
     *
     * @param report           The report name with the folder path.
     * @param reportParameters The key/value pair of the report parameters.
     * @return The content of the report.
     */
    @Override
    public byte[] execute(String report, Map<String, String> reportParameters) {
        return reportingExecutionClientService.execute(report, reportParameters);
    }
}
