package edu.uml.nsay.services;

import edu.uml.nsay.model.Person;
import edu.uml.nsay.model.PersonStock;
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
    /**
     * Get a list of all people
     *
     * @return a list of Person instances
     * @throws PersonServiceException if a service can not perform the requested operation
     */

    public final List<Person> getPersons() throws PersonServiceException {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        List<Person> persons = null;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Person.class);

            /**
             * NOTE criteria.list(); generates unchecked warning so SuppressWarnings
             * is used - HOWEVER, this about the only @SuppressWarnings I think it is OK
             * to suppress them - in almost all other cases they should be fixed not suppressed
             */
            @SuppressWarnings("unchecked") List<Person> list = criteria.list();
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
     * Add a new person or update an existing Person's data
     *
     * @param person a person object to either update or create
     * @throws PersonServiceException if a service can not perform the requested operation
     */
    public final void addOrUpdatePerson(Person person) throws PersonServiceException {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // updates instance of Person if already exists within table
            // or adds as last row of people table
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
     * Get a list of all a person's stocks.
     *
     * @return a list of Strings representing stock symbols
     * @throws PersonServiceException if a service can not perform the requested operation
     */
    public final List<String> getStockSymbols(Person person) throws PersonServiceException {
        Session session =  DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        List<String> stocks = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(PersonStock.class);
            criteria.add(Restrictions.eq("person", person));

            /**
             * NOTE criteria.list(); generates unchecked warning so SuppressWarnings
             * is used - HOWEVER, this about the only @SuppressWarnings I think it is OK
             * to suppress them - in almost all other cases they should be fixed not suppressed
             */
            @SuppressWarnings("unchecked") List<PersonStock> list = criteria.list();
            for (PersonStock personStock : list) {
                stocks.add(personStock.getStockSymbol());
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
        return stocks;
    }

    /**
     * Assigns a stock to a person
     *
     * @param stockSymbol  The stockSymbol to assign
     * @param person The person to whom the stockSymbol is assigned
     * @throws PersonServiceException if a service can not perform the requested operation
     */
    public final void addStockToPerson(String stockSymbol, Person person) throws PersonServiceException {
        Session session =  DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // updates instance of PersonStock if already exists within table
            // or adds as last row of people table
            transaction = session.beginTransaction();
            PersonStock personStock = new PersonStock();
            personStock.setStockSymbol(stockSymbol);
            personStock.setPerson(person);
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
