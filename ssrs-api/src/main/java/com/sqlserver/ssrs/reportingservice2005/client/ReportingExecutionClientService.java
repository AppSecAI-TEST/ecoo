package com.sqlserver.ssrs.reportingservice2005.client;

import java.util.Map;

/**
 * @author Justin Rundle
 * @since January 2017
 */
public interface ReportingExecutionClientService {

    /**
     * Execute the given SSRS report.
     *
     * @param report The report name with the folder path.
     * @return The content of the report.
     */
    byte[] execute(String report);

    /**
     * Execute the given SSRS report.
     *
     * @param report The report name with the folder path.
     * @param name   The parameter name.
     * @param value  The parameter value.
     * @return The content of the report.
     */
    byte[] execute(String report, String name, String value);

    /**
     * Execute the given SSRS report.
     *
     * @param report           The report name with the folder path.
     * @param reportParameters The key/value pair of the report parameters.
     * @return The content of the report.
     */
    byte[] execute(String report, Map<String, String> reportParameters);
}
