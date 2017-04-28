package com.sqlserver.ssrs.reportingservice2005.client.impl;

import com.sqlserver.ssrs.reportingservice2005.*;
import com.sqlserver.ssrs.reportingservice2005.client.ReportingExecutionClientService;
import com.sqlserver.ssrs.reportingservice2005.util.ReportAuthenticator;
import com.sun.xml.ws.developer.WSBindingProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Justin Rundle
 * @since January 2017
 */
public class ReportingExecutionClientServiceImpl implements ReportingExecutionClientService {

    private final Logger log = LoggerFactory.getLogger(ReportingExecutionClientServiceImpl.class);

    private ReportExecutionServiceSoap reportExecutionServiceSoap;

    public ReportingExecutionClientServiceImpl(String wsdlLocation, String username, String password) throws MalformedURLException {
        Authenticator.setDefault(new ReportAuthenticator(username, password));

        final URL baseUrl = ReportExecutionService.class.getResource(".");
        final URL url = new URL(baseUrl, wsdlLocation);
        final QName reportExecutionService = new QName("http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices", "ReportExecutionService");
        this.reportExecutionServiceSoap = new ReportExecutionService(url, reportExecutionService).getReportExecutionServiceSoap();

        log.info("wsdlLocation: {}", wsdlLocation);
        log.info(String.format("{username:%s, password:%s}", username, password));
    }

    /**
     * Execute the given SSRS report.
     *
     * @param report The report name with the folder path.
     * @return The content of the report.
     */
    @Override
    public byte[] execute(String report) {
        return execute(report, null);
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
        final Map<String, String> reportParameters = new HashMap<>();
        reportParameters.put(name, value);

        return execute(report, reportParameters);
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
        final ExecutionInfo info = this.reportExecutionServiceSoap.loadReport(report, null);
        final String executionId = info.getExecutionID();
        log.info("Execution ID:{} {}", info.getExecutionID(), info.toString());

        final ExecutionHeader execHeader = new ExecutionHeader();
        execHeader.setExecutionID(executionId);

        // Set ExecutionID to ReportExecutionServiceSoap ExecutionHeader
        ((WSBindingProvider) this.reportExecutionServiceSoap).setOutboundHeaders(execHeader);

        final ArrayOfParameterValue arrayOfParameterValue = new ArrayOfParameterValue();
        final List<ParameterValue> parameters = arrayOfParameterValue.getParameterValue();

        if (reportParameters != null && !reportParameters.isEmpty()) {
            for (final String name : reportParameters.keySet()) {
                final String value = reportParameters.get(name);
                ParameterValue parameterValue = new ParameterValue();
                parameterValue.setName(name);
                parameterValue.setValue(value);
                parameters.add(parameterValue);
            }
        }

        reportExecutionServiceSoap.setExecutionParameters(arrayOfParameterValue, "en-us”");

        final Holder<byte[]> result = new Holder<>();
        final Holder<String> extension = new Holder<>();
        final Holder<String> mimeType = new Holder<>();
        final Holder<String> encoding = new Holder<>();
        final Holder<ArrayOfWarning> warnings = new Holder<>();
        final Holder<ArrayOfString> streamIds = new Holder<>();

        log.info("Executing report:", report, reportParameters);
        this.reportExecutionServiceSoap.render("PDF", null, result, extension, mimeType, encoding, warnings, streamIds);
        return result.value;
    }
}
