package edu.uml.nsay.services;

import edu.uml.nsay.model.database.PersonDAO;
import edu.uml.nsay.model.database.StockSymbolDAO;
import edu.uml.nsay.util.*;
import org.apache.http.annotation.Immutable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the PersonService class.
 *
 * @author Narith Say
 */
@Immutable
@RunWith(Parameterized.class)
public final class PersonServiceTest {
    // fields of this class
    private PersonService personService;
    private static StockSymbolDAO stockSymbol;
    private static PersonDAO person;

    /**
     * Constructs a new StockServiceTest instance with the specified parameter
     * @param personService parameter defined with Parameterized tag
     */
    public PersonServiceTest(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Sets up logic common to each test
     * @throws DatabaseInitializationException
     */
    @Before
    public final void setUp() throws DatabaseInitializationException {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
        stockSymbol = new StockSymbolDAO("AAPL");
        person = new PersonDAO();
        person.setFirstName("John");
        person.setLastName("Doe");
    }

    /**
     * Test that the return value contains valid Person instances
     * @throws PersonServiceException
     */
    @Test
    public final void testGetPersonsPositive() throws PersonServiceException {
        assertTrue("First element of list returned by getPersons() is not a person",
                personService.getPersons().get(0) instanceof PersonDAO);
    }

    /**
     * Test that the return value is not an empty list
     * @throws PersonServiceException
     */
    @Test
    public final void testGetPersonsNegative() throws PersonServiceException {
        assertFalse("Return value of getPersons() is an empty list",
                personService.getPersons().isEmpty());
    }

    /**
     * Test that the first element of list returned by getPersons() equals the parameter value of addOrUpdatePerson() called on same instance
     * @throws PersonServiceException
     */
    @Test
    public final void testAddOrUpdatePersonPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        List<PersonDAO> persons = personService.getPersons();
        assertTrue("First element of list returned by getPersons() does not equal the parameter value of addOrUpdatePerson() called on same instance",
                        persons.get(persons.size() - 1).equals(person));
    }

    /**
     * Test that the first element of list returned by getPersons() contains an element other than that which was not added
     * @throws PersonServiceException
     */
    @Test
    public final void testAddOrUpdatePersonNegative() throws PersonServiceException {
        List<PersonDAO> persons = personService.getPersons();
        assertFalse("First element of list returned by getPersons() equals the parameter value of addOrUpdatePerson() called on same instance",
                persons.get(persons.size() - 1).equals(person));
    }

    /**
     * Test that the return value contains the correct number of elemenets
     * @throws PersonServiceException
     */
    @Test
    public final void testGetStockSymbolsPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        personService.addStockToPerson(new StockSymbolDAO("LPAA"), person);
        assertTrue("The first element getStockSymbols does not equal the correct stock symbol",
                personService.getStockSymbols(person).size() == 2);
    }

    /**
     * Test that the first element of the return value is not an invalid value
     * @throws PersonServiceException
     */
    @Test
    public final void testGetStockSymbolsNegative() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        personService.addStockToPerson((new StockSymbolDAO("LPAA")), person);
        assertFalse("The first element getStockSymbols does not equal the correct stock symbol",
                personService.getStockSymbols(person).size() == 1);
    }

    /**
     * Test that the first element of the return value is the correct stock symbol
     * @throws PersonServiceException
     */
    @Test
    public final void testAddStockToPersonPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        assertTrue("The first element getStockSymbols does not equal the correct stock symbol",
                personService.getStockSymbols(person).get(0).equals(stockSymbol));
    }

    /**
     * Test that the first element of the return value is not an invalid value
     * @throws PersonServiceException
     */
    @Test
    public final void testAddStockToPersonNegative() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        assertFalse("The first element getStockSymbols equals an invalid value",
                personService.getStockSymbols(person).get(0).equals("banana"));
    }

    /**
     * Defines parameters to be used in each test
     * @return collection of parameters
     */
    @Parameterized.Parameters
    public static final Collection<Object[]> instancesToTest() {
        return Arrays.asList(
                new Object[]{ServiceFactory.getPersonService()},
                new Object[]{ServiceFactory.getPersonService()} // placeholder
        );
    }
}
