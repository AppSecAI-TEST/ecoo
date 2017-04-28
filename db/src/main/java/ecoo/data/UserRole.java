package ecoo.data;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "user_role")
public class UserRole extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = 833328233049734246L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer primaryId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role")
    private String role;

    public UserRole() {

    }

    public UserRole(Integer userId, String role) {
        Assert.notNull(userId);
        Assert.notNull(role);
        this.userId = userId;
        this.role = role;
    }

    /**
     * Returns the unique identifier of the object.
     *
     * @return The primary key value.
     */
    @Override
    public Integer getPrimaryId() {
        return primaryId;
    }

    /**
     * Set the unique identifier for the object.
     *
     * @param primaryId The primary key to set.
     */
    @Override
    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "primaryId=" + primaryId +
                ", userId=" + userId +
                ", role='" + role + '\'' +
                '}';
    }
}