package edu.uml.nsay.model.database;

import org.apache.http.annotation.Immutable;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the QuoteDAO class.
 *
 * @author Narith Say
 */
@Immutable
public final class QuoteDAOTest {
    // private fields of this class
    private static final DateTime time = new DateTime(2015, 12, 25, 18, 0, 0);
    private static final BigDecimal price = new BigDecimal(35.64);
    private static final String symbol = "LOVE";
    private static StockSymbolDAO stockSymbol;
    private static QuoteDAO quote;
    private static final int id = 55;

    /**
     * Sets up the logic common to each test
     */
    @Before
    public final void setUp() {
        stockSymbol = new StockSymbolDAO(symbol);
        quote = new QuoteDAO(time, price, stockSymbol);
        quote.setId(id);
    }

    /**
     * Test that getId retrieves the value passed to the corresponding setter method of the same instance
     */
    @Test
    public final void testGetIdPositive() {
        assertTrue("getId retrieves a value other than the one passed to the set method of the same object",
                quote.getId() == id);
    }

    /**
     * Test that getId retrieves the value passed to the corresponding setter method of the same instance
     */
    @Test
    public final void testGetIdNegative() {
        assertFalse("getId retrieves a value other than the one passed to the set method of the same object",
                quote.getId() == 25);
    }

    /**
     * Test that setId changes the Id
     */
    @Test
    public final void setIdPositive() {
        QuoteDAO quote2 = new QuoteDAO(time, price, stockSymbol);
        quote2.setId(quote.getId());
        boolean idMatches = false;
        if (quote.getId() == quote2.getId()) idMatches = true;
        quote2.setId(34);
        assertTrue("setId does not change the Id", idMatches && (quote.getId() != quote2.getId()));
    }

    /**
     * Test that setId changes the Id
     */
    @Test
    public final void testSetIdNegative() {
        QuoteDAO quote2 = new QuoteDAO(time, price, stockSymbol);
        quote2.setId(34);
        assertFalse("setId does not change the Id", (quote.getId() == quote2.getId()));
    }

    /**
     * Test that getTime retrieves the value passed to the corresponding setter method of the same instance
     */
    @Test
    public final void testGetTimePositive() {
        assertTrue("getTime retrieves a value other than the one passed to the set method of the same object",
                new DateTime(quote.getTime()).equals(time));
    }

    /**
     * Test that getTime retrieves the value passed to the corresponding setter method of the same instance
     */
    @Test
    public final void testGetTimeNegative() {
        assertFalse("getTime retrieves a value other than the one passed to the set method of the same object",
                quote.getTime().equals(DateTime.now()));
    }

    /**
     * Test that setTime changes the time
     */
    @Test
    public final void setTimePositive() {
        QuoteDAO quote2 = new QuoteDAO(time, price, stockSymbol);
        boolean timeMatches = false;
        if (quote.getTime().equals(quote2.getTime())) timeMatches = true;
        quote2.setTime(DateTime.now());
        assertTrue("setTime does not change the time", timeMatches && (!quote.getTime().equals(quote2.getTime())));
    }

    /**
     * Test that setTime changes the time
     */
    @Test
    public final void testSetTimeNegative() {
        QuoteDAO quote2 = new QuoteDAO(time, price, stockSymbol);
        quote2.setTime(time.minusDays(4));
        assertFalse("setTime does not change the time", (quote.getTime().equals(quote2.getTime())));
    }

    /**
     * Test that getPrice retrieves the value passed to the corresponding setter method of the same instance
     */
    @Test
    public final void testGetPricePositive() {
        assertTrue("getPrice retrieves a value other than the one passed to the set method of the same object",
                quote.getPrice().equals(price));
    }

    /**
     * Test that getPrices retrieves the value passed ot the corresponding setter method of the same instance
     */
    @Test
    public final void testGetPriceNegative() {
        assertFalse("getPrice retrieves a value other than the one passed to the set method of the same object",
                quote.getPrice().equals(new BigDecimal(32.65)));
    }

    /**
     * Test that setPrice changes the price
     */
    @Test
    public final void testSetPricePositive() {
        QuoteDAO quote2 = new QuoteDAO(time, price, stockSymbol);
        boolean priceMatches = false;
        if (quote.getPrice().equals(quote2.getPrice())) priceMatches = true;
        quote2.setPrice(new BigDecimal(23.45));
        assertTrue("setPrice does not change the price", priceMatches && (!quote.getPrice().equals(quote2.getPrice())));
    }

    /**
     * Test that setPrice changes the price
     */
    @Test
    public final void testSetPriceNegative() {
        QuoteDAO quote2 = new QuoteDAO(time, price, stockSymbol);
        quote2.setPrice(new BigDecimal(23.45));
        assertFalse("setPrice does not change the Id", (quote.getPrice().equals(quote2.getPrice())));
    }

    /**
     * Test that getSymbol retrieves the value passed to the corresponding setter method of the same object
     */
    @Test
    public final void testGetStockSymbolPositive() {
        assertTrue("getSymbol retrieves a value other than the one passed to the set method of the same object",
                quote.getStockSymbol().equals(stockSymbol));
    }

    /**
     * Test that getSymbol return value does not match a value other than that which was passed into setter method
     */
    @Test
    public final void testGetSetStockSymbolNegative() {
        StockSymbolDAO stockSymbol2 = new StockSymbolDAO("CHEE");
        quote.getStockSymbol().setSymbol(stockSymbol2.getSymbol());
        quote.getStockSymbol().setId(stockSymbol2.getId());
        assertFalse("getSymbol return value matches a value other than that which was passed into setter method",
                stockSymbol2.equals(quote.getStockSymbol()));
    }

    /**
     * Test the setStockSymbol changes the stockSymbol
     */
    @Test
    public final void testSetStockSymbolPositive() {
        QuoteDAO quote2 = new QuoteDAO(time, price, stockSymbol);
        boolean stockSymbolMatches = false;
        if (quote.getStockSymbol().equals(quote2.getStockSymbol())) stockSymbolMatches = true;
        quote2.setStockSymbol(new StockSymbolDAO("CHEE"));
        assertTrue("setStockSymbol does not change the stockSymbol",
                stockSymbolMatches && (!quote.getStockSymbol().equals(quote2.getStockSymbol())));
    }

    /**
     * Test that setStockSymbol changes the stockSymbol
     */
    @Test
    public final void testSetStockSymbolNegative() {
        QuoteDAO quote2 = new QuoteDAO(time, price, stockSymbol);
        quote2.setStockSymbol(new StockSymbolDAO("CHEE"));
        assertFalse("setStockSymbol does not change the stockSymbol",
                (quote.getStockSymbol().equals(quote2.getStockSymbol())));
    }
}
