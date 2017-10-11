package edu.uml.nsay.model;

import org.joda.time.DateTime;
import org.apache.http.annotation.Immutable;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the StockQuote class.
 *
 * @author Narith Say
 */
@Immutable
public final class StockQuoteTest {
    // fields of this class
    private String symbol;
    private BigDecimal stockPrice;
    private DateTime givenDate;
    private StockQuote stockQuote;

    /**
     * Sets up for each test
     */
    @Before
    public final void setUp() {
        // initialize field variables
        symbol = "APPL";
        stockPrice = new BigDecimal(100);
        givenDate = DateTime.now();
        stockQuote = new StockQuote(givenDate, stockPrice, symbol);
    }

    /**
     * Verifies RuntimeException for incorrect StockQuote Date
     */
    @Test(expected = RuntimeException.class)
    public final void testStockQuoteGivenDate() {
        // pass a null Date argument into StockQuote constructor
        DateTime nullDate = null;
        new StockQuote(nullDate, stockPrice, symbol);
    }

    /**
     * Verifies RuntimeException for incorrect StockQuote Price
     */
    @Test(expected = RuntimeException.class)
    public final void testStockQuoteStockPrice() {
        // pass a null BigDecimal argument into StockQuote constructor
        BigDecimal nullNumber = null;
        new StockQuote(givenDate, nullNumber, symbol);
    }

    /**
     * Verifies RuntimeException for incorrect StockQuote Symbol
     */
    @Test(expected = RuntimeException.class)
    public final void testStockQuoteSymbol() {
        // pass a null BigDecimal argument into StockQuote constructor
        String nullString = null;
        new StockQuote(givenDate, stockPrice, nullString);
    }

    /**
     * Verifies correct stock symbol
     */
    @Test
    public final void testgetSymbolPositive() {
        // compares method return value with expected result
        assertTrue("Value returned from getSymbol() equals parameter string",
                stockQuote.getSymbol().equals(symbol));
    }

    /**
     * Verifies incorrect stock symbol
     */
    @Test
    public final void testgetSymbolNegative() {
        // compares method return value with unexpected result
        assertFalse("Value returned from getSymbol() equals lowercase-converted parameter string",
                stockQuote.getSymbol().equals(symbol.toLowerCase()));
    }

    /**
     * Verifies correct stock price
     */
    @Test
    public final void testGetStockPricePositive() {
        // compares method return value with expected result
        assertTrue("Value returned from getStockPrice() equals the parameter value",
                stockQuote.getStockPrice() == stockPrice);
    }

    /**
     * Verifies incorrect stock price
     */
    @Test
    public final void testGetStockPriceNegative() {
        // compares method return value with unexpected result
        assertFalse("Value returned from getStockPrice() equals the parameter value with decimal moved left by one point",
                stockQuote.getStockPrice() == stockPrice.movePointLeft(1));
    }

    /**
     * Verifies correct stock date
     */
    @Test
    public final void testGetGivenDatePositive() {
        // compares method return value with expected result
        assertTrue("Value returned from getGivenDate() equals parameter date",
                stockQuote.getGivenDate().equals(givenDate));
    }

    /**
     * Verifies incorrect stock date
     */
    @Test
    public final void testGetGivenDateNegative() {
        // compares method return value with unexpected result
        DateTime dateNotRecorded = new DateTime(givenDate).plusDays(1);
        assertFalse("Value returned from getGivenDate() equals day after parameter date",
                stockQuote.getGivenDate().equals(dateNotRecorded));
    }
}
