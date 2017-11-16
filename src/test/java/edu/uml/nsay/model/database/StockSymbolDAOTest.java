package edu.uml.nsay.model.database;

import org.apache.http.annotation.Immutable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the StockSymbolDAO class.
 *
 * @author Narith Say
 */
@Immutable
public final class StockSymbolDAOTest {
    // fields of this class
    private static final String symbol = "LOVE";
    private static StockSymbolDAO stockSymbol;
    private static final int id = 44;

    /**
     * Sets up the logic common to each test
     */
    @Before
    public final void setUp() {
        stockSymbol = new StockSymbolDAO(symbol);
        stockSymbol.setId(id);
    }

    /**
     * Test that getId retrieves the value that was passed to the set method of the same instance
     */
    @Test
    public final void testGetIdPositive() {
        assertTrue("getId retrieves a value other than the one passed to the set method of the same object",
                stockSymbol.getId() == id);
    }

    /**
     * Test that getId retrieves the value that was passed to the set method of the same instance
     */
    @Test
    public final void testGetIdNegative() {
        assertFalse("getId retrieves a value other than the one passed to the set method of the same object",
                stockSymbol.getId() == 25);
    }

    /**
     * Test that setId changes the Id
     */
    @Test
    public final void setIdPositive() {
        StockSymbolDAO stockSymbol2 = new StockSymbolDAO(symbol);
        stockSymbol2.setId(stockSymbol.getId());
        boolean idMatches = false;
        if (stockSymbol.getId() == stockSymbol2.getId()) idMatches = true;
        stockSymbol2.setId(34);
        assertTrue("setId does not change the Id", idMatches && (stockSymbol.getId() != stockSymbol2.getId()));
    }

    /**
     * Test that setId changes the Id
     */
    @Test
    public final void testSetIdNegative() {
        StockSymbolDAO stockSymbol2 = new StockSymbolDAO(symbol);
        stockSymbol2.setId(34);
        assertFalse("setId does not change the Id", (stockSymbol.getId() == stockSymbol2.getId()));
    }

    /**
     * Test that getSymbol retrieves the value that was passed to the set method of the same instance
     */
    @Test
    public final void testGetSymbolPositive() {
        assertTrue("getSymbol retrieves a value other than the one passed to the set method of the same object",
                stockSymbol.getSymbol().equals(symbol));
    }

    /**
     * Test that getSymbol retrieves the value that was passed to the set method of the same instance
     */
    @Test
    public final void testGetSymbolNegative() {
        assertFalse("getSymbol retrieves a value other than the one passed to the set method of the same object",
                stockSymbol.getSymbol().equals("CHEE"));
    }

    /**
     * Test that setSymbol changes the symbol
     */
    @Test
    public final void testSetSymbolPositive() {
        StockSymbolDAO stockSymbol2 = new StockSymbolDAO(symbol);
        boolean stockSymbolMatches = false;
        if (stockSymbol.getSymbol().equals(stockSymbol2.getSymbol())) stockSymbolMatches = true;
        stockSymbol2.setSymbol("CHEE");
        assertTrue("setSymbol does not change the symbol",
                stockSymbolMatches && (!stockSymbol.getSymbol().equals(stockSymbol2.getSymbol())));
    }

    /**
     * Test that setSymbol changes the symbol
     */
    @Test
    public final void testSetSymbolNegative() {
        StockSymbolDAO stockSymbol2 = new StockSymbolDAO(symbol);
        stockSymbol2.setSymbol("CHEE");
        assertFalse("setSymbol does not change the symbol", stockSymbol.getSymbol().equals(stockSymbol2.getSymbol()));
    }
}

