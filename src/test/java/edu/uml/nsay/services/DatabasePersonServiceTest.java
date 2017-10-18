package edu.uml.nsay.services;

import edu.uml.nsay.model.Person;
import edu.uml.nsay.model.PersonTest;
import edu.uml.nsay.util.DatabaseUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Narith on 10/15/17.
 */
public class DatabasePersonServiceTest {
    private PersonService personService;

    private void initDb() throws Exception {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
    }

    // do not assume db is correct
    @Before
    public void setUp() throws Exception {
        // we could also copy db state here for later restore before initializing
        initDb();
        personService = ServiceFactory.getPersonServiceInstance();
    }

    // clean up after ourselves. (this could also restore db from initial state
    @After
    public void tearDown() throws Exception {
        initDb();
    }

    @Test
    public void testGetInstance() {
        assertNotNull("Make sure personService is available", personService);
    }

    @Test
    public void testGetPerson() throws PersonServiceException {
        List<Person> personList = personService.getPersons();
        assertFalse("Make sure we get some Person objects from service", personList.isEmpty());
    }

    @Test
    public void testAddOrUpdatePerson()throws PersonServiceException {
        Person newPerson = PersonTest.createPerson();
        personService.addOrUpdatePerson(newPerson);
        List<Person> personList = personService.getPersons();
        boolean found = false;
        for (Person person : personList) {
            if (person.getLastName().equals(PersonTest.lastName) && person.getFirstName().equals(PersonTest.firstName)) {
                found = true;
                break;
            }
        }
        assertTrue("Found the person we added", found);
    }

    @Test
    public void testGetStockSymbolsByPerson() throws PersonServiceException {
        Person person = PersonTest.createPerson();
        List<String> stockSymbols = personService.getStockSymbols(person);
        // make the person have all the stock symbols
        for (String stockSymbol : stockSymbols) {
            personService.addStockToPerson(stockSymbol, person);
        }
        List<String> stockSymbolsList = personService.getStockSymbols(person);
        for (String stockSymbol : stockSymbols) {
            boolean removed = stockSymbolsList.remove(stockSymbol);
            assertTrue("Verify that the stock symbols was found on the list", removed);
        }
        // if  stockSymbolsList is empty then we know the lists matched.
        assertTrue("Verify the list of returned stock symbols match what was expected ", stockSymbolsList.isEmpty());

    }


}