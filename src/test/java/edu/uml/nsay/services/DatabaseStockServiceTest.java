package edu.uml.nsay.services;

import edu.uml.nsay.model.StockQuote;
import edu.uml.nsay.model.database.StockSymbolDAO;
import edu.uml.nsay.util.*;
import org.apache.http.annotation.Immutable;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the DatabaseStockService class.
 *
 * @author Narith Say
 */
@Immutable
public final class DatabaseStockServiceTest {
    // fields of this class
    private DatabaseStockService databaseStockService;
    private DateTime startRange;
    private DateTime endRange;
    private StockSymbolDAO stockSymbol;
    private HoursInterval interval;
    private static final int NUMBER_OF_DAYS = 100;

    /**
     * Sets up logic common to each test
     *
     * @throws DatabaseInitializationException
     * @throws DatabaseConnectionException
     * @throws SQLException
     * @throws StockServiceException
     */
    @Before
    public final void setUp() throws DatabaseConnectionException, SQLException, DatabaseInitializationException , StockServiceException{
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
        databaseStockService = (DatabaseStockService) ServiceFactory.getStockService(ServiceType.DATABASE);
        startRange = DateTime.now().minusDays(NUMBER_OF_DAYS);
        endRange = DateTime.now();
        stockSymbol = new StockSymbolDAO("AAPL");
        interval = HoursInterval.WEEK;
    }

    /**
     * Test that the return value has the correct stock symbol
     *
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgStockSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote does not equal stock symbol initialized with parameter string",
                databaseStockService.getQuote(stockSymbol.getSymbol()).getSymbol().equals(stockSymbol.getSymbol()));
    }

    /**
     * Test that the return value has an incorrect stock symbol
     *
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgStockSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote equals stock symbol initialized with lowercase-coverted parameter string",
                databaseStockService.getQuote(stockSymbol.getSymbol()).getSymbol().equals(stockSymbol.getSymbol().toLowerCase()));
    }

    /**
     * Test that the return value has the correct date recorded
     *
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgTimePositive() throws StockServiceException {
        assertTrue("Date recorded returned from return value of getQuote does not equal the last element returned by the database query",
            databaseStockService.getQuote(stockSymbol.getSymbol()) instanceof StockQuote);
    }

    /**
     * Test that the return value has an incorrect date recorded
     *
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void testGetQuoteSingleArgTimeNegative() throws StockServiceException {
        databaseStockService.getQuote("BLARG");
    }


    /**
     * Test that the return value has an correct date recorded
     *
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void testGetQuoteTripleArgTimePositive() throws StockServiceException {
        databaseStockService.getQuote(stockSymbol.getSymbol(), endRange, startRange);
    }

    /**
     * Test that the return value has an incorrect date recorded
     *
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void timeNegative() throws StockServiceException {
                databaseStockService.getQuote(stockSymbol.getSymbol(), endRange, startRange, interval);
    }
}
