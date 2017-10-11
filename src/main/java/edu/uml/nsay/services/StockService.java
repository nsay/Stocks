package edu.uml.nsay.services;

import edu.uml.nsay.model.StockQuote;
import org.apache.http.annotation.Immutable;

import org.joda.time.DateTime;
import java.util.List;

/**
 * This API describes how to get stock data from an external resource.
 *
 * @author Narith Say
 */
@Immutable
public interface StockService {

    /**
     * Return the current price for a share of stock  for the given symbol
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @return a StockQuote instance
     * @throws StockServiceException
     */
    StockQuote getQuote(String symbol) throws StockServiceException ;

    /**
     * Get a historical list of stock quotes for the provide symbol
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @param startRange beginning of the date range
     * @param endRange end of the date range
     * @return a StockQuote instance
     * @throws StockServiceException
     */
    List<StockQuote> getQuote(String symbol, DateTime startRange, DateTime endRange) throws StockServiceException ;

    /**
     * Get a historical list of stock quotes in an interval for the provide symbol
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @param startRange beginning of the date range
     * @param endRange end of the date range
     * @param interval time between each StockQuote instances
     * @return a StockQuote instance
     * @throws StockServiceException
     */
    List<StockQuote> getQuote(String symbol, DateTime startRange, DateTime endRange, Interval interval) throws StockServiceException;
}
