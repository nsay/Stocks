package edu.uml.nsay.model;

import org.apache.http.annotation.Immutable;
import org.joda.time.DateTime;
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
    private BigDecimal price;
    private DateTime time;
    private StockQuote stockQuote;

    /**
     * Sets up the logic common to each test
     */
    @Before
    public final void setUp() {
        // initialize field variables
        symbol = "APPL";
        price = new BigDecimal(100);
        time = DateTime.now();
        stockQuote = new StockQuote(time, price, symbol);
    }

    /**
     * Test that a RuntimeException is thrown when a StockQuote object is improperly constructed
     */
    @Test(expected = RuntimeException.class)
    public final void testStockQuoteConstructionTime() {
        // pass a null Date argument into StockQuote constructor
        DateTime nullDate = null;
        new StockQuote(nullDate, price, symbol);
    }

    /**
     * Test that a RuntimeException is thrown when a StockQuote object is improperly constructed
     */
    @Test(expected = RuntimeException.class)
    public final void testStockQuoteConstructionPrice() {
        // pass a null BigDecimal argument into StockQuote constructor
        BigDecimal nullNumber = null;
        new StockQuote(time, nullNumber, symbol);
    }

    /**
     * Test that a RuntimeException is thrown when a StockQuote object is improperly constructed
     */
    @Test(expected = RuntimeException.class)
    public final void testStockQuoteConstructionSymbol() {
        // pass a null BigDecimal argument into StockQuote constructor
        String nullString = null;
        new StockQuote(time, price, nullString);
    }

    /**
     * Test that the correct stock symbol is returned
     */
    @Test
    public final void testGetSymbolPositive() {
        // compares method return value with expected result
        assertTrue("Value returned from getSymbol() equals parameter string",
                stockQuote.getSymbol().equals(symbol));
    }

    /**
     * Test that an incorrect stock symbol is returned
     */
    @Test
    public final void testGetSymbolNegative() {
        // compares method return value with unexpected result
        assertFalse("Value returned from getSymbol() equals lowercase-converted parameter string",
                stockQuote.getSymbol().equals(symbol.toLowerCase()));
    }

    /**
     * Test that the correct stock price is returned
     */
    @Test
    public final void testGetPricePositive() {
        // compares method return value with expected result
        assertTrue("Value returned from getPrice() equals the parameter value",
                stockQuote.getPrice() == price);
    }

    /**
     * Test that an incorrect stock price is returned
     */
    @Test
    public final void testGetPriceNegative() {
        // compares method return value with unexpected result
        assertFalse("Value returned from getPrice() equals the parameter value with decimal moved left by one point",
                stockQuote.getPrice() == price.movePointLeft(1));
    }

    /**
     * Test that the correct date recorded is returned
     */
    @Test
    public final void testGetTimePositive() {
        // compares method return value with expected result
        assertTrue("Value returned from getTime() equals parameter date",
                stockQuote.getTime().equals(time));
    }

    /**
     * Test that an incorrect date recorded is returned
     */
    @Test
    public final void testGetTimeNegative() {
        // compares method return value with unexpected result
        DateTime dateNotRecorded = new DateTime(time).plusDays(1);
        assertFalse("Value returned from getTime() equals day after parameter date",
                stockQuote.getTime().equals(dateNotRecorded));
    }
}
