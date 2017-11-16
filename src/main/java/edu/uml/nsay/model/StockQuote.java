package edu.uml.nsay.model;

import org.apache.http.annotation.Immutable;

import java.math.BigDecimal;
import java.text.NumberFormat;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * This class stores information about a stock of a particular symbol, date and price.
 *
 * @author Narith Say
 */
@Immutable
public final class StockQuote {
    // private fields of this class
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern(DATE_PATTERN);
    private final String symbol;
    private final BigDecimal price;
    private final DateTime time;

    /**
     * Constructs a new StockQuote instance
     *
     * @param time date the stock info was recorded
     * @param price price of the stock on the provided date
     * @param symbol symbol for the company issuing the stock
     */
    public StockQuote(DateTime time, BigDecimal price, String symbol) {
        // if any parameter values are null, throw exception; otherwise, initialize fields
        if ((time == null) || (price == null) || (symbol == null)) {
            throw new RuntimeException();
        }
        this.time = new DateTime(time);
        this.price = price;
        this.symbol = symbol;
    }

    /**
     * @return the symbol that represents the company issuing this stock
     */
    public final String getSymbol() {
        return symbol;
    }

    /**
     * @return the price of one share of this stock
     */
    public final BigDecimal getPrice() {
        return price;
    }

    /**
     * @return the date that the info for this stock was recorded
     */
    public final DateTime getTime() {
        return time;
    }

    /**
     * @return an instance of an object for formatting all StockQuote dates according to the specified date pattern
     */
    public static final DateTimeFormatter getDateFormatter() { return dateFormatter; }

    /**
     * @return a String containing the formatted values of the fields of this instance
     */
    @Override
    public String toString() {
        return " [ " + getSymbol() + " " + time.toString(dateFormatter) + " " + NumberFormat.getCurrencyInstance().format(getPrice()) + " ] ";
    }
}
