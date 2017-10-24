package edu.uml.nsay.services;

import edu.uml.nsay.model.StockQuote;
import edu.uml.nsay.model.database.QuoteDAO;
import edu.uml.nsay.model.database.StockSymbolDAO;
import edu.uml.nsay.util.DatabaseUtils;
import edu.uml.nsay.util.Interval;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * An implementation of the StockService interface that gets
 * stock data from a database.
 *
 * @author Narith Say
 */
public class DatabaseStockService implements StockService {

    /**
     * Return the current price for a share of stock  for the given symbol
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @return a  <CODE>BigDecimal</CODE> instance
     * @throws StockServiceException if using the service generates an exception.
     *                               If this happens, trying the service may work, depending on the actual cause of the
     *                               error.
     */
    @Override
    public StockQuote getQuote(String symbol) throws StockServiceException {
        StockQuote stockQuote;

        StockSymbolDAO stockSymbol = DatabaseUtils.findUniqueResultBy("symbol", symbol, StockSymbolDAO.class, true);
        List<QuoteDAO> quotes = DatabaseUtils.findResultsBy("stockSymbol", stockSymbol, QuoteDAO.class, true);

        if (quotes.isEmpty()) {
            throw new StockServiceException("Could not find any stock quotes for: " + symbol);
        }

        QuoteDAO quoteDAO = quotes.get(0);
        // pojo conversion
        long time = quoteDAO.getTime().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        java.util.Date quoteTime = calendar.getTime();
        stockQuote = new StockQuote(quoteDAO.getPrice(), quoteTime, stockSymbol.getSymbol());

        return stockQuote;
    }

    /**
     * Get a historical list of stock quotes for the provided symbol
     *
     * @param symbol   the stock symbol to search for
     * @param from     the date of the first stock quote
     * @param until    the date of the last stock quote
     * @return a list of StockQuote instances
     * @throws StockServiceException if using the service generates an exception.
     *                               If this happens, trying the service may work, depending on the actual cause of the
     *                               error.
     */
    @SuppressWarnings("Duplicates")
    @Override
    public List<StockQuote> getQuote(String symbol, Calendar from, Calendar until)
            throws StockServiceException {
        List<StockQuote> stockQuotes = null;
        Session session = null;
        Transaction transaction = null;

        try {
            StockSymbolDAO stockSymbol = DatabaseUtils.findUniqueResultBy("symbol", symbol, StockSymbolDAO.class, true);
            session = DatabaseUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Timestamp fromTimeStamp = new Timestamp(from.getTimeInMillis());
            Timestamp untilTimestamp = new Timestamp(until.getTimeInMillis());
            Criteria criteria = session.createCriteria(QuoteDAO.class);
            criteria.add(Restrictions.eq("stockSymbol", stockSymbol));
            criteria.add(Restrictions.between("time", fromTimeStamp,untilTimestamp));
            List<QuoteDAO> quoteDAOs = (List<QuoteDAO>) criteria.list();
            transaction.commit();
            stockQuotes = new ArrayList<>(quoteDAOs.size());
            // do within interval here.
            for (QuoteDAO quoteDAO : quoteDAOs) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(quoteDAO.getTime().getTime());
                stockQuotes.add(new StockQuote(quoteDAO.getPrice(),
                        calendar.getTime(),
                        quoteDAO.getStockSymbol().getSymbol()));
            }
        } catch (HibernateException e)  {
            if (transaction != null) {
                transaction.rollback();
            }
            if (session != null) {
                session.close();
            }
            session  = null;

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return stockQuotes;
    }

    /**
     * Get a historical list of stock quotes for the provided symbol in an interval
     *
     * @param symbol   the stock symbol to search for
     * @param from     the date of the first stock quote
     * @param until    the date of the last stock quote
     * @param interval the number of stockquotes to get per a 24 hour period.
     * @return a list of StockQuote instances
     * @throws StockServiceException if using the service generates an exception.
     *                               If this happens, trying the service may work, depending on the actual cause of the
     *                               error.
     */
    @SuppressWarnings("Duplicates")
    @Override
    public List<StockQuote> getQuote(String symbol, Calendar from, Calendar until, Interval interval)
            throws StockServiceException {
        List<StockQuote> stockQuotes = null;
        Session session = null;
        Transaction transaction = null;

        try {
            StockSymbolDAO stockSymbol = DatabaseUtils.findUniqueResultBy("symbol", symbol, StockSymbolDAO.class, true);
            session = DatabaseUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Timestamp fromTimeStamp = new Timestamp(from.getTimeInMillis());
            Timestamp untilTimestamp = new Timestamp(until.getTimeInMillis());
            Criteria criteria = session.createCriteria(QuoteDAO.class);
            criteria.add(Restrictions.eq("stockSymbol", stockSymbol));
            criteria.add(Restrictions.between("time", fromTimeStamp,untilTimestamp));
            List<QuoteDAO> quoteDAOs = (List<QuoteDAO>) criteria.list();
            transaction.commit();
            stockQuotes = new ArrayList<>(quoteDAOs.size());
            // do within interval here.
            for (QuoteDAO quoteDAO : quoteDAOs) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(quoteDAO.getTime().getTime());
                stockQuotes.add(new StockQuote(quoteDAO.getPrice(),
                        calendar.getTime(),
                        quoteDAO.getStockSymbol().getSymbol()));
            }
        } catch (HibernateException e)  {
            if (transaction != null) {
                transaction.rollback();
            }
            if (session != null) {
                session.close();
            }
            session  = null;

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return stockQuotes;
    }

    /**
     * Adds a new quote or updates the data of an existing quote
     *
     * @param time a {@code Timestamp} object representing the time of the quote
     * @param price the price of the quote
     * @param stockSymbol the symbol of the quote
     * @throws StockServiceException if a service can not perform the requested operation
     */
    public final void addOrUpdateQuote(Timestamp time, BigDecimal price, StockSymbolDAO stockSymbol) throws StockServiceException {
        Session session =  DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // updates instance of StockQuote if already exists within table
            // or adds as last row of personStock table
            transaction = session.beginTransaction();
            session.saveOrUpdate(stockSymbol);
            QuoteDAO dbQuote = new QuoteDAO(time, price, stockSymbol);
            session.saveOrUpdate(dbQuote);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
                throw new RuntimeException(e.getMessage());
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }

    /**
     * Returns true of the currentStockQuote has a date that is later by the time
     * specified in the interval value from the previousStockQuote time.
     *
     * @param endDate   the end time
     * @param interval  the period of time that must exist between previousStockQuote and currentStockQuote
     *                  in order for this method to return true.
     * @param startDate the starting date
     * @return
     */
    private boolean isInterval(java.util.Date endDate, Interval interval, java.util.Date startDate) {
        Calendar startDatePlusInterval = Calendar.getInstance();
        startDatePlusInterval.setTime(startDate);
        startDatePlusInterval.add(Calendar.MINUTE, interval.getMinutes());
        return endDate.after(startDatePlusInterval.getTime());
    }
}
