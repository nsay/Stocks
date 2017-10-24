package edu.uml.nsay.services;


import edu.uml.nsay.model.User;
import edu.uml.nsay.model.database.PersonDAO;
import edu.uml.nsay.model.database.PersonStockDAO;
import edu.uml.nsay.model.database.StockSymbolDAO;
import edu.uml.nsay.util.DatabaseUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

/**
 * This class is a concrete implementation of UserService that reads data from a database.
 *
 * @author Narith Say
 */
public class DatabaseUserService implements UserService {

    /**
     * Add a user to the system.
     *
     * @param user the person to add.
     * @throws UserServiceException if there is a problem creating the person record.
     * @throws DuplicateUserNameException if the user name is not unique.
     */
    @Override
    public void addPerson(User user) throws UserServiceException, DuplicateUserNameException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = DatabaseUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            PersonDAO personDAO = new PersonDAO();
            personDAO.setUserName(user.getUserName());
            session.saveOrUpdate(personDAO);
            transaction.commit();
        } catch (ConstraintViolationException e) {
            throw new DuplicateUserNameException(user.getUserName() + " already exists");
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
            throw new UserServiceException(e.getMessage(), e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                // if we get there there's an error to deal with
                transaction.rollback();  //  close transaction
            }
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Each person can have 0 or more stocks associated with them. This methods adds that association.
     *
     * @param symbol the symbol to add
     * @param user   the user name to add the symbol to
     * @throws UnknownStockSymbolException if the stock symbol does not exist in the supported list
     *                                     of symbols.
     * @throws UnknownUserException        if the specified user can't be found.
     * @throws UserServiceException        if there was a general problem with the service.
     */
    @Override
    public void associateStockWithPerson(String symbol, User user)
            throws UnknownStockSymbolException, UnknownUserException, UserServiceException {

        PersonDAO personDAO = DatabaseUtils.findUniqueResultBy("username", user.getUserName(), PersonDAO.class, true);
        if (personDAO == null) {
            throw new UnknownUserException("No User record found with username of " + user.getUserName());
        }
        StockSymbolDAO stockSymbolDAO = DatabaseUtils.findUniqueResultBy("symbol", symbol, StockSymbolDAO.class, true);
        if (stockSymbolDAO == null) {
            throw new UnknownStockSymbolException("No Stock Symbol record for: " + symbol);
        }
        PersonStockDAO personStocksDAO = new PersonStockDAO();
        personStocksDAO.setPerson(personDAO);
        //  personStocksDAO.setPersonByPersonId(stockSymbolDAO);
        Session session = DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(personStocksDAO);
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
