
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.URL;


/**
 * The Reporting Services Execution Service enables report execution
 * <p>
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 */
@WebServiceClient(name = "ReportExecutionService", targetNamespace = "http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices", wsdlLocation = "file:/C:/code/ecoo/ssrs-api/src/main/resources/reportExecution2005.wsdl")
public class ReportExecutionService extends Service {

    public ReportExecutionService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    /**
     * @return returns ReportExecutionServiceSoap
     */
    @WebEndpoint(name = "ReportExecutionServiceSoap")
    public ReportExecutionServiceSoap getReportExecutionServiceSoap() {
        return super.getPort(new QName("http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices", "ReportExecutionServiceSoap"), ReportExecutionServiceSoap.class);
    }

    /**
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return returns ReportExecutionServiceSoap
     */
    @WebEndpoint(name = "ReportExecutionServiceSoap")
    public ReportExecutionServiceSoap getReportExecutionServiceSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices", "ReportExecutionServiceSoap"), ReportExecutionServiceSoap.class, features);
    }

}
