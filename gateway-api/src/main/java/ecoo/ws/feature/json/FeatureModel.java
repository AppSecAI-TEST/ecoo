package ecoo.ws.feature.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * @author Justin Rundle
 *@since April 2017
 */
public class FeatureModel {

    @NotNull
    private String systemManagerId;

    @NotNull
    private String smtpServer;

    @NotNull
    private String outgoingEmail;

    @NotNull
    private String outgoingEmailName;

    @NotNull
    private String nonProductionEmail;

    @NotNull
    private String scheduledJobStatsEmail;

    @NotNull
    private String fiscalYearStartDate;

    @NotNull
    private String activeDirectoryUrl;

    @NotNull
    private String b2bWsUrl;

    @NotNull
    private String interfaceSchedulerUrl;

    @NotNull
    private String kibanaUrl;

    @JsonCreator
    public FeatureModel(@JsonProperty("systemManagerId") String systemManagerId
            , @JsonProperty("smtpServer") String smtpServer
            , @JsonProperty("outgoingEmail") String outgoingEmail
            , @JsonProperty("outgoingEmailName") String outgoingEmailName
            , @JsonProperty("nonProductionEmail") String nonProductionEmail
            , @JsonProperty("scheduledJobStatsEmail") String scheduledJobStatsEmail
            , @JsonProperty("activeDirectoryUrl") String activeDirectoryUrl
            , @JsonProperty("b2bWsUrl") String b2bWsUrl
            , @JsonProperty("interfaceSchedulerUrl") String interfaceSchedulerUrl
            , @JsonProperty("kibanaUrl") String kibanaUrl
            , @JsonProperty("fiscalYearStartDate") String fiscalYearStartDate) {
        this.systemManagerId = systemManagerId;
        this.smtpServer = smtpServer;
        this.outgoingEmail = outgoingEmail;
        this.outgoingEmailName = outgoingEmailName;
        this.nonProductionEmail = nonProductionEmail;
        this.scheduledJobStatsEmail = scheduledJobStatsEmail;
        this.activeDirectoryUrl = activeDirectoryUrl;
        this.b2bWsUrl = b2bWsUrl;
        this.interfaceSchedulerUrl = interfaceSchedulerUrl;
        this.kibanaUrl = kibanaUrl;
        this.fiscalYearStartDate = fiscalYearStartDate;
    }

    public String getSystemManagerId() {
        return systemManagerId;
    }

    public void setSystemManagerId(String systemManagerId) {
        this.systemManagerId = systemManagerId;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getOutgoingEmail() {
        return outgoingEmail;
    }

    public void setOutgoingEmail(String outgoingEmail) {
        this.outgoingEmail = outgoingEmail;
    }

    public String getOutgoingEmailName() {
        return outgoingEmailName;
    }

    public void setOutgoingEmailName(String outgoingEmailName) {
        this.outgoingEmailName = outgoingEmailName;
    }

    public String getNonProductionEmail() {
        return nonProductionEmail;
    }

    public void setNonProductionEmail(String nonProductionEmail) {
        this.nonProductionEmail = nonProductionEmail;
    }

    public String getScheduledJobStatsEmail() {
        return scheduledJobStatsEmail;
    }

    public void setScheduledJobStatsEmail(String scheduledJobStatsEmail) {
        this.scheduledJobStatsEmail = scheduledJobStatsEmail;
    }

    public String getFiscalYearStartDate() {
        return fiscalYearStartDate;
    }

    public void setFiscalYearStartDate(String fiscalYearStartDate) {
        this.fiscalYearStartDate = fiscalYearStartDate;
    }

    public String getActiveDirectoryUrl() {
        return activeDirectoryUrl;
    }

    public void setActiveDirectoryUrl(String activeDirectoryUrl) {
        this.activeDirectoryUrl = activeDirectoryUrl;
    }

    public String getB2bWsUrl() {
        return b2bWsUrl;
    }

    public void setB2bWsUrl(String b2bWsUrl) {
        this.b2bWsUrl = b2bWsUrl;
    }

    public String getInterfaceSchedulerUrl() {
        return interfaceSchedulerUrl;
    }

    public void setInterfaceSchedulerUrl(String interfaceSchedulerUrl) {
        this.interfaceSchedulerUrl = interfaceSchedulerUrl;
    }

    public String getKibanaUrl() {
        return kibanaUrl;
    }

    public void setKibanaUrl(String kibanaUrl) {
        this.kibanaUrl = kibanaUrl;
    }
}
