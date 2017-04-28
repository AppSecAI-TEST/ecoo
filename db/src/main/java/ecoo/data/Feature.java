package ecoo.data;

import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author Justin Rundle
 * @since August 2016
 */
@Entity
@Table(name = "feature")
public final class Feature extends BaseModel<Integer> {

    private static final long serialVersionUID = 7272335525501016490L;

    public enum Type {
        /**
         * The application home directory.
         */
        APP_HOME

        /**
         * The SMTP server host name.
         */
        , SMTP_SERVER


        /**
         * The string representation of the Scheduler web service url.
         */
        , SCHEDULER_API_URL

        /**
         * The string representation of the Kibana UI.
         */
        , KIBANA_URL

        /**
         * The string representation of the SPIV Gateway API URL.
         */
        , GATEWAY_API_URL

        /**
         * The string representation of the workflow web service API URL.
         */
        , WORKFLOW_API_URL

        /**
         * The person responsible for action user related workflow requests.
         */
        , BPM_USER_ASSIGNEE

        /**
         * The person responsible for action incentive related workflow requests.
         */
        , BPM_INCENTIVE_ASSIGNEE

        /**
         * The person responsible for action claim related workflow requests
         */
        , BPM_CLAIM_ASSIGNEE

        /**
         * The company registration name that reflects on the bank file
         */
        , COMPANY_NAME

        /**
         * The company bank account number on the bank file
         */
        , COMPANY_BANK_ACC_NUMBER

        /**
         * The company branch code on the bank file
         */
        , COMPANY_BANK_BRANCH
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Audited
    private Integer primaryId;

    @Column(name = "name")
    @Audited
    private String name;

    @Column(name = "value")
    @Audited
    private String value;

    @Column(name = "descr")
    @Audited
    private String description;

    /**
     * Constructs a new {@link Feature} model object.
     */
    public Feature() {
    }

    public Feature(final String value, final String description) {
        this.value = value;
        this.description = description;
    }

    /*
     * (non-Javadoc)
     *
     * @see ecoo.data.BaseModel#getPrimaryId()
     */
    @Override
    public final Integer getPrimaryId() {
        return primaryId;
    }

    /*
     * (non-Javadoc)
     *
     * @see ecoo.data.BaseModel#setPrimaryId(java.lang.Object)
     */
    @Override
    public final void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public final void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public final String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public final void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public final void setDescription(String description) {
        this.description = description;
    }
}