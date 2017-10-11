package edu.uml.nsay.model;

import org.apache.http.annotation.Immutable;

import java.math.BigDecimal;
import java.text.NumberFormat;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * A container class that contains stock data.
 *
 * @author Narith Say
 * @version 10/02/2017
 */
@Immutable
public final class StockQuote {
    // private fields of this class
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern(DATE_FORMAT);
    private final String symbol;
    private final BigDecimal stockPrice;
    private final DateTime givenDate;

    /**
     * Constructs a new StockQuote instance
     *
     * @param givenDate     given date of the stock
     * @param stockPrice    price of the stock on the given date
     * @param symbol        stock symbol
     */
    public StockQuote(DateTime givenDate, BigDecimal stockPrice, String symbol) {
        // if any parameter values are null, throw exception; otherwise, initialize fields
        if ((givenDate == null) || (stockPrice == null) || (symbol == null)) {
            throw new RuntimeException();
        }
        this.givenDate = new DateTime(givenDate);
        this.stockPrice = stockPrice;
        this.symbol = symbol;
    }

    /**
     * @return stock symbol
     */
    public final String getSymbol() {
        return symbol;
    }

    /**
     * @return stock price
     */
    public final BigDecimal getStockPrice() {
        return stockPrice;
    }

    /**
     * @return given date of stock
     */
    public final DateTime getGivenDate() {
        return givenDate;
    }

    /**
     * @return all StockQuote dates to the date format "yyyy-MM-dd HH:mm:ss"
     */
    public static final DateTimeFormatter getDateFormatter() { return dateFormatter; }

    /**
     * @return a String of the formatted values of the fields of this instance
     */
    @Override
    public String toString() {
        return " [ " + getSymbol() + " " + givenDate.toString(dateFormatter) + " " + NumberFormat.getCurrencyInstance().format(getStockPrice()) + " ] ";
    }
}
