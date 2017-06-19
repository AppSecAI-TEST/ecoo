package ecoo.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ecoo.security.UserAuthority;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Justin Rundle
 * @since April 2017
 */

@Entity
@Table(name = "user_acc")
@Document(type = "ecoo.user", indexName = "ecoo.user", shards = 1, replicas = 0)
public class User extends BaseModel<Integer> implements UserDetails, CredentialsContainer {

    private static final long serialVersionUID = -6052051635817263810L;

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Audited
    private Integer primaryId;

    @Column(name = "title")
    @Audited
    private String title;

    @Column(name = "first_name")
    @Audited
    private String firstName;

    @Column(name = "last_name")
    @Audited
    private String lastName;

    @Column(name = "display_name")
    @Audited
    private String displayName;

    @Column(name = "primary_email_address")
    @Audited
    private String primaryEmailAddress;

    @Column(name = "mobile_no")
    @Audited
    private String mobileNumber;

    @Column(name = "prefer_comm_type")
    @Audited
    private String preferredCommunicationType;

    @Column(name = "username")
    @Audited
    private String username;

    @Column(name = "password")
    @Audited
    private String password;

    @Column(name = "personal_ref_type")
    @Audited
    private String personalReferenceType;

    @Column(name = "personal_ref_value")
    @Audited
    private String personalReferenceValue;

    @Column(name = "account_non_expired")
    @Audited
    private boolean accountNonExpired;

    @Column(name = "account_non_locked")
    @Audited
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    @Audited
    private boolean credentialsNonExpired;

    @Column(name = "enabled")
    @Audited
    private boolean enabled;

    @Column(name = "reserved")
    @Audited
    private boolean reserved;

    @Column(name = "password_expired")
    @Audited
    private boolean passwordExpired;

    @Column(name = "activation_serial_no")
    @Audited
    private String activationSerialNumber;

    @Column(name = "last_login_time")
    @Audited
    private Date lastLoginTime;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @Audited
    private Company company;

    @Column(name = "designation")
    @Audited
    private String designation;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @Cascade({CascadeType.ALL})
    private Set<UserRole> userRoles;

    @Transient
    private Collection<GrantedAuthority> authorities;

    @Column(name = "status")
    @Audited
    private String status;

    @Transient
    private Set<String> groupIdentities = new HashSet<>();

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Constructs a new user object.
     *
     * @param primaryId The user pk.
     */
    public User(final Integer primaryId) {
        this.primaryId = primaryId;
    }

    /**
     * Constructs a new user object.
     *
     * @param username The username.
     */
    public User(String username) {
        this.username = username;
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.ecoo.db.model.BaseModel#getPrimaryId()
     */
    @Override
    public final Integer getPrimaryId() {
        return primaryId;
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.ecoo.db.model.BaseModel#setPrimaryId(java.lang.Object)
     */
    @Override
    public final void setPrimaryId(final Integer primaryId) {
        this.primaryId = primaryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPrimaryEmailAddress() {
        return primaryEmailAddress;
    }

    public void setPrimaryEmailAddress(String primaryEmailAddress) {
        this.primaryEmailAddress = primaryEmailAddress;
    }

    public String getPreferredCommunicationType() {
        return preferredCommunicationType;
    }

    public void setPreferredCommunicationType(String preferredCommunicationType) {
        this.preferredCommunicationType = preferredCommunicationType;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * Returns the username.
     *
     * @return The username.
     */
    public final String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username The username to set.
     */
    public final void setUsername(final String username) {
        this.username = username;
    }


    /**
     * Returns the password.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password The password to set.
     */
    public final void setPassword(final String password) {
        this.password = password;
    }

    public String getPersonalReferenceType() {
        return personalReferenceType;
    }

    public void setPersonalReferenceType(String personalReferenceType) {
        this.personalReferenceType = personalReferenceType;
    }

    public String getPersonalReferenceValue() {
        return personalReferenceValue;
    }

    public void setPersonalReferenceValue(String personalReferenceValue) {
        this.personalReferenceValue = personalReferenceValue;
    }

    /**
     * Returns the reserved flag.
     *
     * @return The reserved flag.
     */
    public final boolean isReserved() {
        return reserved;
    }

    /**
     * Sets the reserved flag.
     *
     * @param reserved The reserved flag to set.
     */
    public final void setReserved(final boolean reserved) {
        this.reserved = reserved;
    }


    /**
     * Returns the lastLoginTime.
     *
     * @return The lastLoginTime.
     */
    @JsonIgnore
    public final Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * Sets the lastLoginTime.
     *
     * @param lastLoginTime The lastLoginTime to set.
     */
    public void setLastLoginTime(final Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /*
         * (non-Javadoc)
         *
         * @see org.springframework.security.core.CredentialsContainer#eraseCredentials()
         */
    @JsonIgnore
    @Override
    public final void eraseCredentials() {
        this.password = null;
    }

    /*
   * (non-Javadoc)
   *
   * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
   */
    @Override
    public final boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * @param accountNonExpired the accountNonExpired to set
     */
    public final void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     */
    @Override
    public final boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * @param accountNonLocked the accountNonLocked to set
     */
    public final void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     */
    @Override
    public final boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * @param credentialsNonExpired the credentialsNonExpired to set
     */
    public final void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
     */
    @Override
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns the boolean representation of the password expired flag.
     *
     * @return The password expired flag.
     */
    public final boolean isPasswordExpired() {
        return passwordExpired;
    }

    /**
     * Sets the password expired flag.
     *
     * @param passwordExpired The password expired flag to set.
     */
    public final void setPasswordExpired(final boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    /**
     * Returns the String representation of the activation serial number for this user.
     *
     * @return The activation serial number.
     */
    public final String getActivationSerialNumber() {
        return activationSerialNumber;
    }

    /**
     * Sets the activation serial number.
     *
     * @param activationSerialNumber The activation serial number to set.
     */
    public final void setActivationSerialNumber(final String activationSerialNumber) {
        this.activationSerialNumber = activationSerialNumber;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    /**
     * Returns the user roles linked to this account.
     *
     * @return the userRoles.
     */
    public final Set<UserRole> getUserRoles() {
        if (userRoles == null) {
            userRoles = new HashSet<>();
        }
        return userRoles;
    }


    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @JsonIgnore
    @Override
    public final Collection<GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = new LinkedList<>();
            for (final UserRole userRole : userRoles) {
                final UserAuthority authority = new UserAuthority();
                authority.setAuthority(Role.valueOf(userRole.getRole()).name());
                authority.setUserDetails(this);
                authorities.add(authority);
            }
        }
        return Collections.unmodifiableCollection(authorities);
    }

    @JsonIgnore
    public Collection<String> getRoles() {
        return getUserRoles().stream().map(UserRole::getRole).collect(Collectors.toSet());
    }

    @JsonIgnore
    public boolean hasRole(Role role) {
        Assert.notNull(role);
        for (final UserRole userRole : userRoles) {
            if (userRole.getRole().equalsIgnoreCase(role.name())) {
                return true;
            }
        }
        return false;
    }

    @JsonIgnore
    public boolean isInStatus(UserStatus userStatus) {
        Assert.notNull(userStatus);
        return userStatus.id().equalsIgnoreCase(this.status);
    }

    @JsonIgnore
    public boolean isCommunicationPreferenceType(CommunicationPreferenceType type) {
        Assert.notNull(type);
        return type.id().equalsIgnoreCase(this.preferredCommunicationType);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<String> getGroupIdentities() {
        return Collections.unmodifiableSet(groupIdentities);
    }

    public void setGroupIdentities(Set<String> groupIdentities) {
        this.groupIdentities = groupIdentities;
    }

    @JsonIgnore
    public void addGroupIdentity(String groupIdentity) {
        this.groupIdentities.add(groupIdentity);
    }

    @JsonIgnore
    public boolean isInRole(Role role) {
        if (role == null) return false;
        for (UserRole userRole : getUserRoles()) {
            if (userRole.getRole().equalsIgnoreCase(role.name())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "primaryId=" + primaryId +
                ", title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", primaryEmailAddress='" + primaryEmailAddress + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", preferredCommunicationType='" + preferredCommunicationType + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", personalReferenceType='" + personalReferenceType + '\'' +
                ", personalReferenceValue='" + personalReferenceValue + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                ", reserved=" + reserved +
                ", passwordExpired=" + passwordExpired +
                ", activationSerialNumber='" + activationSerialNumber + '\'' +
                ", company=" + company +
                ", designation='" + designation + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", userRoles=" + userRoles +
                ", authorities=" + authorities +
                ", groupIdentities=" + groupIdentities +
                ", status='" + status + '\'' +
                '}';
    }
}