package edu.uml.nsay.services;

import edu.uml.nsay.model.database.PersonDAO;

import edu.uml.nsay.model.database.StockSymbolDAO;

import java.util.List;

/**
 * Describes an API for adding a User and a list of the stocks they are interested in.
 *
 * @author Narith Say
 */
public interface PersonService {
    /**
     * Get a list of all people
     *
     * @return a list of Person instances
     * @throws PersonServiceException if a service cannot perform the requested operation
     */
    List<PersonDAO> getPersons() throws PersonServiceException;

    /**
     * Add a new person or update the data of an existing person
     *
     * @param person a person object to either update or create
     * @throws PersonServiceException if a service cannot perform the requested operation
     */
    void addOrUpdatePerson(PersonDAO person) throws PersonServiceException;

    /**
     * Get a list of all of the stocks a person is interested in
     *
     * @return a list of Strings representing stock symbols
     * @throws PersonServiceException if a service cannot perform the requested operation
     */
    List<StockSymbolDAO> getStockSymbols(PersonDAO person) throws PersonServiceException;

    /**
     * Assign a stock to a person
     *
     * @param stockSymbol The stockSymbol to assign
     * @param person The person to whom the stockSymbol is assigned
     * @throws PersonServiceException if a service cannot perform the requested operation
     */
    public void addStockToPerson(StockSymbolDAO stockSymbol, PersonDAO person) throws PersonServiceException;
}
