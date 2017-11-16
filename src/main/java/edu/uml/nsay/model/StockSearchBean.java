package edu.uml.nsay.model;

import edu.uml.nsay.services.ServiceFactory;
import edu.uml.nsay.services.ServiceType;
import edu.uml.nsay.services.StockService;
import edu.uml.nsay.util.HoursInterval;
import edu.uml.nsay.util.Interval;
import edu.uml.nsay.services.StockServiceException;
import org.joda.time.DateTime;

import java.util.List;

/**
 * This class stores information about a stock of a particular symbol, date and price,
 * and implements the JavaBean pattern.
 *
 * @author Narith Say
 */
public class StockSearchBean {
    // private fields of this class
    private static String symbol;
    private static DateTime startRange;
    private static DateTime endRange;
    private static Interval interval;
    private static String quotesStr;

    /**
     * Constructs a new StockSearchBean instance by default
     * in accordance with the JavaBean pattern
     */
    public StockSearchBean() {}

    /**
     * Constructs a new StockSearchBean instance
     */
    public StockSearchBean(String symbol, String startRange, String endRange, String interval) {
        setSymbol(symbol);
        setStartRange(startRange);
        setEndRange(endRange);
        setInterval(interval);
    }

    /**
     * Returns a String representation of a stock symbol
     *
     * @return the symbol that represents the company issuing this stock
     */
    public final String getSymbol() {
        return symbol;
    }

    /**
     * Sets a String representation of a stock symbol with a String argument
     *
     * @param symbol the symbol that represents the company issuing this stock
     */
    public final void setSymbol(String symbol) { this.symbol = symbol; }

    /**
     * Returns a DateTime representation of a startRange
     *
     * @return the starting date that the info for this stock was retrieved
     */
    public final String getStartRange() {
        return startRange.toString(StockQuote.getDateFormatter());
    }

    /**
     * Sets a DateTime representation of a startRange with a String argument
     *
     * @param str the starting date that the info for this stock was retrieved
     */
    public final void setStartRange(String str) { this.startRange = DateTime.parse(str, StockQuote.getDateFormatter()); }

    /**
     * Returns a DateTime representation of an endRange
     *
     * @return the ending date that the info for this stock was retrieved
     */
    public final String getEndRange() {
        return endRange.toString(StockQuote.getDateFormatter());
    }

    /**
     * Sets a DateTime representation of an endRange with a String argument
     *
     * @param str the ending date that the info for this stock was retrieved
     */
    public final void setEndRange(String str) { this.endRange = DateTime.parse(str, StockQuote.getDateFormatter()); }

    /**
     * Returns a String representation of the interval between which quotes are to be retrieved
     *
     * @return the interval between quotes retrieved
     */
    public final String getInterval() {
        return interval.toString();
    }

    /**
     * Sets an Interval representation of an interval between which quotes are to be retrieved
     * with a String argument
     *
     * @param str the interval between quotes retrieved
     */
    public final void setInterval(String str) {
        switch (str) {
            case("DAY"):
                interval = HoursInterval.DAY;
                break;
            case ("WEEK"):
                interval = HoursInterval.WEEK;
                break;
            case ("MONTH"):
                interval = HoursInterval.MONTH;
                break;
            default:
                interval = HoursInterval.DAY;
        }; }

    /**
     * Sets the quote string field of this class
     *
     * @param str a String containing quotes
     */
    public final void SetQuotesStr(String str) {
        quotesStr = str;
    }

    /**
     * Gets the quote string field of this class
     *
     * @return a String containing quotes
     */
    public final String getQuotesStr() {
        return quotesStr;
    }

    /**
     * Generates a String of the set of quotes requested using the getQuote method of a YahooStockService instance
     * after verifying that the arguments to be passed to the getQuote method are valid.
     *
     * @throws StockServiceException
     */
    public void processData(ServiceType type) throws StockServiceException {
        if (this.validateData()) {
            StockService service = ServiceFactory.getStockService(type);
            List<StockQuote> quoteList = service.getQuote(symbol, startRange, endRange, interval);
            StringBuilder builder = new StringBuilder();
            for (StockQuote q : quoteList) {
                builder.append(q.toString());
            }
            quotesStr = builder.toString();
        } else {
            throw new IllegalStateException("Not all fields have been initialized");
        }
    }

    /**
     * Test that the quote properties are valid
     *
     * @return
     */
    public boolean validateData() {
        if ((this.symbol == null) || (this.startRange == null) || (this.endRange == null) || this.interval == null)
            return false;
        return true;
    }
}
