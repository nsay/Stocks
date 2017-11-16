package edu.uml.nsay.services;

import edu.uml.nsay.model.database.PersonDAO;
import edu.uml.nsay.model.database.PersonStockDAO;
import edu.uml.nsay.model.database.StockSymbolDAO;
import edu.uml.nsay.util.DatabaseUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a concrete implementation of PersonService that reads data from a database.
 *
 * @author Narith Say
 */
public class DatabasePersonService implements PersonService {
    // fields of this class
    public static final DatabasePersonService INSTANCE = new DatabasePersonService();

    // hides the constructor so that new instances are build through the factory class
    protected DatabasePersonService() {
    }

    /**
     * Gets a list of all persons stored in the "person" database.
     * Changes made by mutating objects in the returned list do not propagate to the database.
     *
     * @return a list of Person instances
     * @throws PersonServiceException if a service can not perform the requested operation
     */
    public final List<PersonDAO> getPersons() throws PersonServiceException {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        List<PersonDAO> persons = null;
        Transaction transaction = null;
        try {
            // assigns all instances of Person within session to Criteria
            // then converts Criteria to a list of the criteria type which is Person.class
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(PersonDAO.class);

            @SuppressWarnings("unchecked") List<PersonDAO> list = criteria.list();
            persons = list;
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
            throw new PersonServiceException("Could not get Person data. " + e.getMessage(), e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
        return persons;
    }

    /**
     * Adds a new person or updates the data of an existing person
     *
     * @param person a person object to either update or create
     * @throws PersonServiceException if a service can not perform the requested operation
     */
    public final void addOrUpdatePerson(PersonDAO person) throws PersonServiceException {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // updates instance of Person if already exists within table
            // or adds as last row of person table
            transaction = session.beginTransaction();
            session.saveOrUpdate(person);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }

    /**
     * Gets a list of all of the stocks a person is interested in
     *
     * @return a list of Strings representing stock symbols
     * @throws PersonServiceException if a service can not perform the requested operation
     */
    public final List<StockSymbolDAO> getStockSymbols(PersonDAO person) throws PersonServiceException {
        Session session =  DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        List<StockSymbolDAO> stockSymbols = null;
        try {
            // assigns from the session to Criteria all instances of PersonStock containing the person parameter value
            // then converts Criteria to a list of the criteria type which is PersonStock.class
            // and gets from each PersonStock instance the associated stock stockSymbol
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(PersonStockDAO.class);
            criteria.add(Restrictions.eq("person", person));

            @SuppressWarnings("unchecked") List<PersonStockDAO> list = criteria.list();
            stockSymbols = new ArrayList<StockSymbolDAO>();
            for (PersonStockDAO personStock : list) {
                stockSymbols.add(personStock.getStockSymbol());
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
        return stockSymbols;
    }

    /**
     * Assigns a stock to a person
     *
     * @param stockSymbol  The stockSymbol to assign
     * @param person The person to whom the stockSymbol is assigned
     * @throws PersonServiceException if a service can not perform the requested operation
     */
    public final void addStockToPerson(StockSymbolDAO stockSymbol, PersonDAO person) throws PersonServiceException {
        Session session =  DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // updates instance of PersonStock if already exists within table
            // or adds as last row of personStock table
            transaction = session.beginTransaction();
            session.saveOrUpdate(stockSymbol);
            PersonStockDAO personStock = new PersonStockDAO();
            personStock.setStockSymbol(stockSymbol);
            personStock.setPerson(person);
            session.saveOrUpdate(stockSymbol);
            session.saveOrUpdate(personStock);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }
}
