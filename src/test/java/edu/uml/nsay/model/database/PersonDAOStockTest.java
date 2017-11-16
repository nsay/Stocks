package edu.uml.nsay.model.database;

import org.apache.http.annotation.Immutable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the PersonStockDAO class.
 *
 * @author Narith Say
 */
@Immutable
public final class PersonDAOStockTest {
    // private fields of this class
    private static final String firstName = "John";
    private static final String lastName = "Doe";
    private static final int id = 10;
    private static PersonDAO person;
    private static StockSymbolDAO stockSymbol;
    private static PersonStockDAO personStock;

    /**
     * Sets up the logic common to each test
     */
    @Before
    public final void setUp() {
        person = new PersonDAO();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setId(id);
        stockSymbol = new StockSymbolDAO("LOVE");
        personStock = new PersonStockDAO();
        personStock.setId(id);
        personStock.setPerson(person);
        personStock.setStockSymbol(stockSymbol);
    }

    /**
     * Test that getPerson return value matches the value passed into the corresponding setter method
     */
    @Test
    public final void testGetSetPersonPositive() {
        assertTrue("getPerson return value does not match the value passed into the setter method", person.equals(personStock.getPerson()));
    }

    /**
     * Test that getPerson return value does not match a value other than that which was passed into setter method
     */
    @Test
    public final void testGetSetPersonNegative() {
        PersonDAO person2 = new PersonDAO("test1", "test2");
        personStock.getPerson().setFirstName(person2.getFirstName());
        personStock.getPerson().setFirstName(person2.getLastName());
        personStock.getPerson().setId(person2.getId());
        assertFalse("getPerson return value matches a value other than that which was passed into setter method",
                person2.equals(personStock.getPerson()));
    }

    /**
     * Test that getSymbol return value matches the value passed into the corresponding setter method
     */
    @Test
    public final void testGetSetStockSymbolPositive() {
        assertTrue("getSymbol return value does not match the value passed into the setter method",
                stockSymbol.equals(personStock.getStockSymbol()));
    }

    /**
     * Test that getSymbol return value does not match a value other than that which was passed into setter method
     */
    @Test
    public final void testGetSetStockSymbolNegative() {
        StockSymbolDAO stockSymbol2 = new StockSymbolDAO("COCO");
        personStock.getStockSymbol().setSymbol(stockSymbol2.getSymbol());
        personStock.getStockSymbol().setId(stockSymbol2.getId());
        assertFalse("getSymbol return value matches a value other than that which was passed into setter method",
                stockSymbol2.equals(personStock.getStockSymbol()));
    }

    /**
     * Test that getId return value matches the value passed into the corresponding setter method
     */
    @Test
    public final void testGetSetIdPositive() {
        assertTrue("getId return value does not match the value passed into the setter method",
                id == personStock.getId());
    }

    /**
     * Test that getId return value does not match a value other than that which was passed into setter method
     */
    @Test
    public final void testGetSetIdNegative() {
        assertFalse("getId return value matches a value other than that which was passed into setter method",
                (id + id *37) == personStock.getId());
    }

    /**
     * Test that same objects are considered equal
     */
    @Test
    public final void testEqualsPositive() {
        PersonStockDAO personStock2 = new PersonStockDAO(person, stockSymbol);
        personStock2.setId(id);
        assertTrue("Same objects are not considered equal",
                personStock.equals(personStock2));
    }

    /**
     * Test that different objects are not considered equal
     */
    @Test
    public final void testEqualsNegative() {
        PersonStockDAO personStock2 = new PersonStockDAO(person, stockSymbol);
        assertFalse("Different objects are considered equal",
                personStock.equals(personStock2));
    }

    /**
     * Test that toString includes the field values of the object
     */
    @Test
    public final void testToStringPositive() {
        assertTrue("toString does not include the field values of the object",
                personStock.toString().equals("PersonStock{" +
                "id=" + personStock.getId() +
                ", person='" + personStock.getPerson() + '\'' +
                ", stockSymbol='" + stockSymbol + '\'' +
                '}'));
    }

    /**
     * Test that toString does not include the inverse of each field value
     */
    @Test
    public final void testToStringNegative() {
        assertFalse("toString includes the inverse of each field value",
                personStock.toString().equals("Person{" +
                        "id=" + new StringBuilder(personStock.getId()).reverse().toString() +
                        ", person='" + new StringBuilder(personStock.getPerson().toString()).reverse().toString() + '\'' +
                        ", stockSymbol='" + new StringBuilder(personStock.getStockSymbol().toString()).reverse().toString() + '\'' +
                        '}'));
    }
}
