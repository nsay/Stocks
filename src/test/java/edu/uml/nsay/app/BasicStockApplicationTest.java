package edu.uml.nsay.app;

import edu.uml.nsay.model.StockQuote;
import edu.uml.nsay.services.*;
import edu.uml.nsay.util.DatabaseInitializationException;
import edu.uml.nsay.util.DatabaseUtils;
import edu.uml.nsay.util.HoursInterval;
import edu.uml.nsay.services.StockServiceException;
import org.apache.http.annotation.Immutable;
import org.joda.time.DateTime;
import org.junit.*;
import org.mockito.Mockito;

import javax.xml.bind.JAXBException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the BasicStockApplication class.
 *
 * @author Narith Say
 */
@Immutable
public final class BasicStockApplicationTest {
    // fields of this class
    private StockService stockService;
    private DateTime startRange;
    private DateTime endRange;
    private String symbol;
    private BigDecimal price;
    private BasicStockApplication basicStockApplication;
    private HoursInterval interval;
    private static final int NUMBER_OF_DAYS = 100;

    /**
     * Sets up the logic common to each test
     *
     * @throws StockServiceException
     */
    @Before
    public final void setUp() throws DatabaseInitializationException, StockServiceException {
        // initialize database
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);

        // mock the external dependency
        stockService = Mockito.mock(StockService.class);

        // create the data we expect the service to return
        symbol = "GOOG";
        startRange = DateTime.now().minusDays(NUMBER_OF_DAYS);
        endRange = DateTime.now();
        price = new BigDecimal(100);
        interval = HoursInterval.WEEK;

        // tell the mock service to return a StockQuote with a specific price and symbol when getQuote() is called
        when(stockService.getQuote(any(String.class)))
                .thenReturn(new StockQuote(DateTime.now(), price, symbol));

        // create the StickTicker instance to test
        basicStockApplication = new BasicStockApplication(stockService);
    }

    /**
     * Test that the return value has the correct date recorded
     *
     * @throws StockServiceException
     */
    @Test
    public final void testGetStockHistoryDatePositive() throws StockServiceException {
        // compares method return values with expected results
        List<StockQuote> stockHistory = basicStockApplication.getStockHistory(symbol, startRange, endRange, interval);
        DateTime time = new DateTime(startRange);
        for (StockQuote quote : stockHistory) {
            assertTrue("Date recorded returned from return value of getStockHistory() does not equal the parameter string",
                    quote.getTime().equals(time));
            time.plusDays(interval.amount());
        }
    }

    /**
     * Test that the return value as an incorrect date recorded
     *
     * @throws StockServiceException
     */
    @Test
    public final void testGetStockHistoryDateNegative() throws StockServiceException {
        // compares method return values with unexpected results
        List<StockQuote> stockHistory = basicStockApplication.getStockHistory(symbol, endRange, startRange, interval);
        DateTime time = new DateTime(startRange);
        for (StockQuote quote : stockHistory) {
            assertFalse("Date recorded returned from return value of getStockHistory() does not equal the parameter string",
                    quote.getTime().equals(time));
            time.plusDays(interval.amount());
        }
    }

    /**
     * Test that the return value has the correct stock symbol
     *
     * @throws StockServiceException
     */
    @Test
    public final void testGetStockHistorySymbolPositive() throws StockServiceException {
        // compares method return values with expected results
        List<StockQuote> stockHistory = basicStockApplication.getStockHistory(symbol, startRange, endRange, interval);
        for (StockQuote quote : stockHistory) {
            assertTrue("Symbol returned from return value of getStockHistory() does not equal the parameter string",
                    quote.getSymbol().equals(symbol));
        }
    }

    /**
     * Test that the return value as an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetStockHistorySymbolNegative() throws StockServiceException {
        // compares method return values with unexpected results
        List<StockQuote> stockHistory = basicStockApplication.getStockHistory(symbol, startRange, endRange, interval);
        for (StockQuote quote : stockHistory) {
            assertFalse("Symbol returned from return value of getStockHistory() equals lowercase-converted parameter string",
                    quote.getSymbol().equals(symbol.toLowerCase()));
        }
    }

    /**
     * Test that the main function, when passed an invalid argument, generates a NullPointerException
     *
     * @throws StockServiceException
     * @throws JAXBException
     */
    @Test(expected = NullPointerException.class)
    public void testMainNegative() throws StockServiceException, JAXBException {
        BasicStockApplication.main(null);
    }
}
