package edu.uml.nsay.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for Person Class
 *
 * @author Narith Say
 */
public class PersonTest {

    public static final String firstName = "John";
    public static final String lastName = "Doe";

    /**
     * Testing helper method for generating Person test data
     *
     * @return a Person object that uses static constants for data.
     */
    public static Person createPerson() {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }

    @Test
    public void testPersonGettersAndSetters() {
        Person person = createPerson();
        int id = 10;
        person.setId(id);
        assertEquals("first name matches", firstName, person.getFirstName());
        assertEquals("last name matches", lastName, person.getLastName());
        assertEquals("id matches", id, person.getId());

    }
}