package edu.uml.nsay.services;

import edu.uml.nsay.model.Person;

import java.util.List;

/**
 * This interface is a service for reading Person records and list of stocks.
 *
 * @author Narith Say
 */
public interface PersonService {
    /**
     * Get a list of all people
     *
     * @return a list of Person instances
     * @throws PersonServiceException if a service can not read or write the requested data
     *                                or otherwise perform the requested operation.
     */
    List<Person> getPersons() throws PersonServiceException;

    /**
     * Add a new person or update the data of an existing person
     *
     * @param person a person object to either update or create
     * @throws PersonServiceException if a service can not read or write the requested data
     *                                    or otherwise perform the requested operation.
     */
    void addOrUpdatePerson(Person person) throws PersonServiceException;

    /**
     * Get a list of all of the stocks a person is interested in
     *
     * @return a list of Strings representing stock symbols
     * @throws PersonServiceException if a service can not read or write the requested data
     *                                    or otherwise perform the requested operation.
     */
    List<String> getStockSymbols(Person person) throws PersonServiceException;

    /**
     * Assign a stock to a person
     *
     * @param stockSymbol  The stockSymbol to assign
     * @param person The person to whom the stockSymbol is assigned
     * @throws PersonServiceException if a service can not read or write the requested data
     *                                    or otherwise perform the requested operation.
     */
    public void addStockToPerson(String stockSymbol, Person person) throws PersonServiceException;
}
