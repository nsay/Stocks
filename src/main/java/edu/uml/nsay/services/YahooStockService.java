package edu.uml.nsay.services;

import edu.uml.nsay.model.StockQuote;
import edu.uml.nsay.util.Interval;
import org.apache.http.annotation.Immutable;

import org.joda.time.DateTime;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class defines methods for getting stock quotes from a web-based API, and implements the StockService interface.
 *
 * @author Narith Say
 */
@Immutable
public class YahooStockService implements StockService {
    // fields of this class
    public static final YahooStockService INSTANCE = new YahooStockService();

    // hides the constructor so that new instances are build through the factory class
    protected YahooStockService() {
    }

    /**
     * Gets today's StockQuote instance for the given symbol
     *
     * @param symbol the symbol for the company issuing the stock
     * @return a StockQuote instance
     * @throws StockServiceException
     */
    public final StockQuote getQuote(String symbol) throws StockServiceException {
        StockQuote stockQuote = null;
        try {
            Stock stock = YahooFinance.get(symbol);
            stockQuote = new StockQuote(new DateTime(stock.getQuote().getLastTradeTime()), stock.getQuote().getPrice(), stock.getQuote().getSymbol());
        } catch (IOException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuote == null) {
            throw new StockServiceException("No instances of " + symbol + " found in the selected range");
        }
        return stockQuote;
    }

    /**
     * Gets the List of StockQuote instances for the given symbol and date range
     *
     * @param symbol abbreviation for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange end of the date range
     * @return a List of StockQuote instances
     * @throws StockServiceException
     */
    public final List<StockQuote> getQuote(String symbol, DateTime startRange, DateTime endRange) throws StockServiceException {
        List<StockQuote> stockQuotes = new ArrayList<StockQuote>();
        try {
            Stock stock = YahooFinance.get(symbol, startRange.toGregorianCalendar(), endRange.toGregorianCalendar());
            List<HistoricalQuote> quotes = stock.getHistory();
            stockQuotes = quoteListAdapter(quotes);
        } catch (IOException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("No instances of " + symbol + " found in the selected range");
        }
        return stockQuotes;
    }

    /**
     * Gets the List of StockQuote instances for the given symbol, date range and interval
     *
     * @param symbol abbreviation for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange end of the date range
     * @param interval time elapsed between stockQuote instances
     * @return a List of StockQuote instances
     * @throws StockServiceException
     */
    public final List<StockQuote> getQuote(String symbol, DateTime startRange, DateTime endRange, Interval interval) throws StockServiceException {
        List<StockQuote> stockQuotes = new ArrayList<StockQuote>();
        yahoofinance.histquotes.Interval quoteInterval = intervalAdapter(interval);
        try {
            Stock stock = YahooFinance.get(symbol, startRange.toGregorianCalendar(), endRange.toGregorianCalendar(), quoteInterval);
            List<HistoricalQuote> quotes = stock.getHistory();
            stockQuotes = quoteListAdapter(quotes);
        } catch (IOException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("No instances of " + symbol + " found in the selected range");
        }
        return stockQuotes;
    }

    /**
     * Adapts an instance of the Stock class from the yahoofinance package to a StockQuote
     *
     * @param stock object from class defined in yahoofinance package
     * @return {@code StockQuote}
     */
    private static final StockQuote quoteAdapter(Stock stock) {
        return new StockQuote(new DateTime(stock.getQuote().getLastTradeTime()), stock.getQuote().getPrice(), stock.getQuote().getSymbol());
    }

    /**
     * Adapts a list of instances of the HistoricalQuote class from the yahoofinance package to a list of StockQuote
     *
     * @param quotes list of objects from class defined in yahoofinance package
     * @return {@code StockQuote}
     */
    private static final List<StockQuote> quoteListAdapter(List<HistoricalQuote> quotes) {
        List<StockQuote> stockQuotes = new ArrayList<StockQuote>();
        for (HistoricalQuote q : quotes) {
            stockQuotes.add(new StockQuote(new DateTime(q.getDate()), q.getAdjClose(), q.getSymbol()));
            quotes.iterator().next();
        }
        return stockQuotes;
    }

    /**
     * Adapts an instance of the local Interval class to an instance of the Interval class defined in yahoofinance package
     *
     * @param interval from local class
     * @return interval from class defined in yahoofinance package
     */
    private static final yahoofinance.histquotes.Interval intervalAdapter(Interval interval) {
        switch(interval.amount()) {
            case (24): return yahoofinance.histquotes.Interval.DAILY;
            case (168): return yahoofinance.histquotes.Interval.WEEKLY;
            case (672): return yahoofinance.histquotes.Interval.MONTHLY;
            default: return yahoofinance.histquotes.Interval.DAILY;
        }
    }
}
