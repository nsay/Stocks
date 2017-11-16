package edu.uml.nsay.services;

import edu.uml.nsay.model.database.PersonDAO;
import edu.uml.nsay.model.database.StockSymbolDAO;
import edu.uml.nsay.util.DatabaseInitializationException;
import edu.uml.nsay.util.DatabaseUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the DatabasePersonService class.
 *
 * @author Narith Say
 */
public final class DatabasePersonServiceTest {
    // private fields of this class
    private static final String firstName = "John";
    private static final String lastName = "Doe";
    private static PersonDAO person;
    private static StockSymbolDAO stockSymbol;
    private PersonService personService;

    /**
     * Sets up logic common to each test
     * @throws DatabaseInitializationException
     */
    @Before
    public void setUp() throws DatabaseInitializationException {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
        personService = ServiceFactory.getPersonService();
        stockSymbol = new StockSymbolDAO("AAPL");
        person = new PersonDAO();
        person.setFirstName(firstName);
        person.setLastName(lastName);
    }

    /**
     * Test that person service is available
     */
    @Test
    public void testGetInstance() {
        assertNotNull("Person service is not available", personService);
    }

    /**
     * Test that getPersons returns person objects
     * @throws PersonServiceException
     */
    @Test
    public void testGetPersonsPositive() throws PersonServiceException {
        List<PersonDAO> personList = personService.getPersons();
        personList = personService.getPersons();
        assertTrue("Value returned from getPersons contains objects other than persons",
                personList.get(0) instanceof PersonDAO);
    }

    /**
     * Test that getPersons does not return an empty List
     * @throws PersonServiceException
     */
    @Test
    public void testGetPersonsNegative() throws PersonServiceException {
        List<PersonDAO> personList = personService.getPersons();
        assertFalse("getPersons returns an empty List", personList.isEmpty());
    }

    /**
     * Test that object passed to addOrUpdatePerson is included in list of persons
     * @throws PersonServiceException
     */
    @Test
    public void testAddOrUpdatePersonPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        List<PersonDAO> personList = personService.getPersons();
        boolean found = false;
        for (PersonDAO person : personList) {
            if (    person.getLastName().equals(lastName)
                    &&
                    person.getFirstName().equals(firstName)) {
                found = true;
                break;
            }
        }
        assertTrue("Person object passed to addOrUpdatePerson was not found in personService", found);
    }

    /**
     * Test that PersonService instance person list was smaller before AddorUpdatePerson was called
     * @throws PersonServiceException
     */
    @Test
    public void testAddOrUpdatePersonNegative() throws PersonServiceException {
        List<PersonDAO> personList = personService.getPersons();
        int listSize1 = personList.size();
        personService.addOrUpdatePerson(person);
        personList = personService.getPersons();
        int listSize2 = personList.size();
        assertFalse("PersonService instance person list was not smaller before addOrUpdatePerson was called", listSize1 >= listSize2);
    }

    /**
     * Test that objects passed to addstockToPerson are included in their respective database lists
     * @throws PersonServiceException
     */
    @Test
    public void testAddStockToPersonPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        boolean foundSymbol = false;
        List<StockSymbolDAO> stockSymbols = personService.getStockSymbols(person);
        for (StockSymbolDAO stockSymbol : stockSymbols) {
            if (stockSymbol.equals(this.stockSymbol)) {
                foundSymbol = true;
                break;
            }
        }
        assertTrue("StockSymbol object passed to addStockSymbolToPerson was not found in personService", foundSymbol);
    }

    /**
     * Test that person's stock list was smaller before addStockToPerson was called
     * @throws PersonServiceException
     */
    @Test
    public void testAddStockToPersonNegative() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        List<StockSymbolDAO> stockSymbols = personService.getStockSymbols(person);
        int listSize1 = stockSymbols.size();
        personService.addStockToPerson(stockSymbol, person);
        stockSymbols = personService.getStockSymbols(person);
        int listSize2 = stockSymbols.size();
        assertFalse("Person's stock list was not smaller before addStockUpdatePerson was called", listSize1 >= listSize2);
    }

    /**
     * Test that multiple calls to getSymbol returns the same values
     * @throws PersonServiceException
     */
    @Test
    public void testGetStockSymbolsConsistencyPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        List<StockSymbolDAO> stockSymbols = personService.getStockSymbols(person);
        List<StockSymbolDAO> stockSymbols2 = personService.getStockSymbols(person);
        for (StockSymbolDAO stockSymbol : stockSymbols) {
            boolean removed = stockSymbols2.remove(stockSymbol);
            assertTrue("getSymbol does not return the same values", removed);
        }
        assertTrue("getSymbol does not return the same values", stockSymbols2.isEmpty());
    }

    /**
     * Test that multiple calls to getSymbol do not return different values
     * @throws PersonServiceException
     */
    @Test
    public final void testGetStockSymbolsConsistencyNegative() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        List<StockSymbolDAO> stockSymbols = personService.getStockSymbols(person);
        StockSymbolDAO stockSymbol2 = new StockSymbolDAO("PIZZA PIZ");
        for (StockSymbolDAO stockSymbol : stockSymbols) {
            boolean removed = stockSymbols.remove(stockSymbol2);
            assertFalse("getSymbol returns different values", removed);
        }
        assertFalse("getSymbol returns different values", stockSymbols.isEmpty());
    }

    /**
     * Test that getSymbol returns the correct values
     * @throws PersonServiceException
     */
    @Test
    public final void testGetStockSymbolAccuracyPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        List<StockSymbolDAO> stockSymbols = personService.getStockSymbols(person);
        assertTrue("getSymbol does not return the correct value", stockSymbols.get(0).equals(stockSymbol));
    }

    /**
     * Test that getSymbol does not return invalid values
     * @throws PersonServiceException
     */
    @Test
    public final void testGetStockSymbolAccuracyNegative() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        List<StockSymbolDAO> stockSymbols = personService.getStockSymbols(person);
        StockSymbolDAO stockSymbol = stockSymbols.get(0);
        assertFalse("getSymbol returns an invalid value", stockSymbol.getSymbol().equals("chips"));
    }
}
