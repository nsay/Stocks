package edu.uml.nsay.model.database;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class models the User database table
 *
 * @author Narith Say
 */
@Entity
@Table(name = "person", schema = "", catalog = "stocks")
public class PersonDAO implements DatabasesAccessObject{

    private int id;
    private String userName;

    /**
     * Constructs a {@code PersonDAO} that needs to be initialized
     */
    public PersonDAO() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Constructs a valid {@code PersonDAO}
     * @param id sets the id of this PersonDAO object
     * @param userName sets the userName of this PersonDAO object
     */
    public PersonDAO(int id, String userName) {
        setId(id);
        setUserName(userName);
    }

    /**
     * Primary Key - Unique ID for a particular row in the person table.
     *
     * @return an integer value
     */
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    /**
     * Set the unique ID for a particular row in the person table.
     * This method should not be called by client code. The value is managed in internally.
     *
     * @param id a unique value.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the user name of this User instance
     */
    @Basic
    @Column(name = "user_name", nullable = false, insertable = true, updatable = true, length = 256)
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name of this User instance
     *
     * @param userName a String value
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonDAO personDAO = (PersonDAO) o;

        if (id != personDAO.id) return false;
        if (userName != null ? !userName.equals(personDAO.userName) : personDAO.userName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }
}
