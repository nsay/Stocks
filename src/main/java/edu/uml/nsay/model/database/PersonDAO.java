package edu.uml.nsay.model.database;

import edu.uml.nsay.model.database.DatabaseAccessObject;

import javax.persistence.*;

/**
 * This class models the person database table
 *
 * @author Narith Say
 */

@Entity
@Table(name="person")
public class PersonDAO implements DatabaseAccessObject {
    // private fields of this class
    private int id;
    private String firstName;
    private String lastName;

    /**
     * Constructs a Person that needs to be initialized
     */
    public PersonDAO() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Constructs a valid Person
     *
     * @param firstName sets the first name of this Person object
     * @param lastName sets the last name of this Person object
     */
    public PersonDAO(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }

    /**
     * Gets the unique ID for this Person instance
     *
     * @return an integer value representing the unique ID for this Person
     */
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID for this Person instance.
     * This method should not be called by client code. The value is managed internally.
     *
     * @param id an integer value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the first name of this Person instance
     */
    @Basic
    @Column(name = "first_name", nullable = false, insertable = true, updatable = true, length = 256)
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of this Person instance
     *
     * @param firstName a String value
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the last name of this Person
     */
    @Basic
    @Column(name = "last_name", nullable = false, insertable = true, updatable = true, length = 256)
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of this Person instance
     *
     * @param lastName a String value
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonDAO person = (PersonDAO) o;

        if (id != person.id) return false;
        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
