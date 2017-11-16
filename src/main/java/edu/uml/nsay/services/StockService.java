package edu.uml.nsay.services;

import edu.uml.nsay.model.StockQuote;
import edu.uml.nsay.util.Interval;
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
     * Gets current StockQuote instance for the given symbol
     *
     * @param symbol symbol for the company issuing the stock
     * @return a StockQuote instance
     * @throws StockServiceException
     */
    StockQuote getQuote(String symbol) throws StockServiceException ;

    /**
     * Gets the List of StockQuote instances for the given symbol and date range
     *
     * @param symbol symbol for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange   end of the date range
     * @return a List of StockQuote instances
     * @throws StockServiceException
     */
    List<StockQuote> getQuote(String symbol, DateTime startRange, DateTime endRange) throws StockServiceException ;

    /**
     * Gets the List of StockQuote instances for the given symbol and date range
     *
     * @param symbol symbol for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange   end of the date range
     * @param interval time elapsed between stockQuote instances
     * @return a List of StockQuote instances
     * @throws StockServiceException
     */
    List<StockQuote> getQuote(String symbol, DateTime startRange, DateTime endRange, Interval interval) throws StockServiceException;
}
