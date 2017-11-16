package edu.uml.nsay.model.database;

import org.apache.http.annotation.Immutable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the PersonDAO class.
 *
 * @author Narith Say
 */
@Immutable
public final class PersonDAOTest {
    // private fields of this class
    private static final String firstName = "John";
    private static final String lastName = "Doe";
    private static final int id = 10;
    private static PersonDAO person;

    /**
     * Sets up the logic common to each test
     */
    @Before
    public final void setUp() {
        person = new PersonDAO();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setId(id);
    }

    /**
     * Test that first name matches the value passed to the set method
     */
    @Test
    public final void testGetSetFirstNamePositive() {
        assertTrue("First name does not match the value passed to the set method", 
                firstName.equals(person.getFirstName()));
    }

    /**
     * Test that first name does not match the inverse of the value passed to the set method
     */
    @Test
    public final void testGetSetFirstNameNegative() {
        assertFalse("First name matches the inverse of the value passed to the set method",
                new StringBuilder(firstName).reverse().toString().equals(person.getFirstName()));
    }

    /**
    * Test that last name matches the value passed to the set method
    */
    @Test
    public final void testGetSetLastNamePositive() {
        assertTrue("Last name does not match the value passed to the set method",
                lastName.equals(person.getLastName()));
    }

    /**
     * Test that last name does not match the inverse of the value passed to the set method
     */
    @Test
    public final void testGetSetLastNameNegative() {
        assertFalse("Last name matches the inverse of the value passed to the set method",
                new StringBuilder(lastName).reverse().toString().equals(person.getFirstName()));
    }

    /**
     * Test that ID matches value passed to set method
     */
    @Test
    public final void testGetIdPositive() {
        assertTrue("ID does not match value passed to set method", id == person.getId());
    }

    /**
     * Test that ID does not match different value from that passed to set method
     */
    @Test
    public final void testGetIdNegative() {
        assertFalse("ID matches a different value from that passed to set method", (id + id * 37) == person.getId());
    }
}
