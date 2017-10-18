package edu.uml.nsay.services;

import edu.uml.nsay.model.StockQuote;
import edu.uml.nsay.util.Interval;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * An implementation of the StockService that returns hard coded data.
 *
 * @author Narith Say
 */
class SimpleStockService implements StockService {

    /**
     * Return the current price for a share of stock  for the given symbol
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @return a BigDecimal instance
     * @throws StockServiceException if using the service generates an exception.
     */
    @Override
    public StockQuote getQuote(String symbol) {
        return new StockQuote(new BigDecimal(100), Calendar.getInstance().getTime(), symbol);
    }

    /**
     * Get a historical list of stock quotes for the provide symbol
     *
     * @param symbol the stock symbol to search for
     * @param from   the date of the first stock quote
     * @param until  the date of the last stock quote
     * @return a list of StockQuote instances
     * @throws   StockServiceException if using the service generates an exception.
     */
    @Override
    public List<StockQuote> getQuote(String symbol, Calendar from, Calendar until, Interval interval) {
        List<StockQuote> stockQuotes = new ArrayList<>();
        Date aDay = from.getTime();
        while (until.after(aDay)) {
            stockQuotes.add(new StockQuote(new BigDecimal(100), aDay, symbol));
            from.add(Calendar.DAY_OF_YEAR, 1);
            aDay = from.getTime();
        }
        return stockQuotes;
    }
}