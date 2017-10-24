package edu.uml.nsay.services;

import edu.uml.nsay.model.StockQuote;
import edu.uml.nsay.util.Interval;

import java.util.Calendar;
import java.util.List;

/**
 * This API describes how to get stock data from an external resource.
 *
 * @author Narith Say
 */
public interface StockService {


    /**
     * Return the current price for a share of stock  for the given symbol
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @return a BigDecimal instance
     * @throws StockServiceException if using the service generates an exception.
     */
    StockQuote getQuote(String symbol) throws StockServiceException;

    /**
     * Get a historical list of stock quotes for the provide symbol
     *
     * @param symbol the stock symbol to search for
     * @param from   the date of the first stock quote
     * @param until  the date of the last stock quote
     * @return a list of StockQuote instances
     * @throws   StockServiceException if using the service generates an exception.
     */
    List<StockQuote> getQuote(String symbol, Calendar from, Calendar until) throws StockServiceException;

    /**
     * Get a historical list of stock quotes for the provides symbol in an interval
     *
     * @param symbol the stock symbol to search for
     * @param from   the date of the first stock quote
     * @param until  the date of the last stock quote
     * @param interval the number of stockquotes to get per a 24 hour period.
     * @return a list of StockQuote instances
     * @throws   StockServiceException if using the service generates an exception.
     */
    List<StockQuote> getQuote(String symbol, Calendar from, Calendar until, Interval interval) throws StockServiceException;

}
