package edu.uml.nsay.application;

import edu.uml.nsay.model.StockQuote;
import edu.uml.nsay.services.IntervalEnum;
import edu.uml.nsay.services.StockService;
import edu.uml.nsay.services.StockServiceException;
import edu.uml.nsay.utilities.DatabaseInitializationException;
import org.apache.http.annotation.Immutable;
import org.joda.time.DateTime;
import org.junit.*;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Tests for BasicStockQuoteApplication
 *
 * @author Narith Say
 */
@Immutable
public final class StockQuoteApplicationTest {
    // fields of this class
    private StockService stockServiceMock;
    private DateTime startRange;
    private DateTime endRange;
    private String symbol;
    private BigDecimal expectedPrice;
    private StockQuoteApplication stockQuoteApplication;
    private IntervalEnum intervalEnum;
    private static final int NUMBER_OF_DAYS = 15;

    /**
     * Sets up for each test
     *
     * @throws StockServiceException
     */
    @Before
    public final void setUp() throws DatabaseInitializationException, StockServiceException {

        // mock the external dependency
        stockServiceMock = Mockito.mock(StockService.class);

        // create the data we expect the service to return
        symbol = "AAPL";
        startRange = DateTime.now().minusDays(NUMBER_OF_DAYS);
        endRange = DateTime.now();
        expectedPrice = new BigDecimal(100);
        intervalEnum = IntervalEnum.WEEK;

        // tell the mock service to return a StockQuote with a specific price and symbol when getQuote() is called
        when(stockServiceMock.getQuote(any(String.class)))
                .thenReturn(new StockQuote(DateTime.now(), expectedPrice, symbol));

        // create the StickTicker instance to test
        stockQuoteApplication = new StockQuoteApplication(stockServiceMock);
    }


    /**
     * Verifies correct date
     *
     * @throws StockServiceException
     */
    @Test
    public final void testGetStockHistoryDayRecordedPositive() throws StockServiceException {
        // compares method return values with expected results
        List<StockQuote> stockHistory = stockQuoteApplication.getStockHistory(symbol, startRange, endRange, intervalEnum);
        DateTime givenDate = new DateTime(startRange);
        for (StockQuote quote : stockHistory) {
            assertTrue("Date returned from return value of getStockHistory() does not equal the parameter string",
                    quote.getGivenDate().equals(givenDate));
            givenDate.plusDays(intervalEnum.amount());
        }
    }

    /**
     * Verifies incorrect date
     *
     * @throws StockServiceException
     */
    @Test
    public final void testGetStockHistoryDayRecordedNegative() throws StockServiceException {
        // compares method return values with unexpected results
        List<StockQuote> stockHistory = stockQuoteApplication.getStockHistory(symbol, endRange, startRange, intervalEnum);
        DateTime givenDate = new DateTime(startRange);
        for (StockQuote quote : stockHistory) {
            assertFalse("Date recorded returned from return value of getStockHistory() does not equal the parameter string",
                    quote.getGivenDate().equals(givenDate));
            givenDate.plusDays(intervalEnum.amount());
        }
    }

    /**
     * Verifies correct stock symbol
     *
     * @throws StockServiceException
     */
    @Test
    public final void testGetStockHistorysymbolPositive() throws StockServiceException {
        // compares method return values with expected results
        List<StockQuote> stockHistory = stockQuoteApplication.getStockHistory(symbol, startRange, endRange, intervalEnum);
        for (StockQuote quote : stockHistory) {
            assertTrue("Stock symbol returned from return value of getStockHistory() does not equal the parameter string",
                    quote.getSymbol().equals(symbol));
        }
    }

    /**
     * Verifies incorrect stock symbol
     *
     * @throws StockServiceException
     */
    @Test
    public final void testGetStockHistorysymbolNegative() throws StockServiceException {
        // compares method return values with unexpected results
        List<StockQuote> stockHistory = stockQuoteApplication.getStockHistory(symbol, startRange, endRange, intervalEnum);
        for (StockQuote quote : stockHistory) {
            assertFalse("Stock symbol returned from return value of getStockHistory() equals lowercase-converted parameter string",
                    quote.getSymbol().equals(symbol.toLowerCase()));
        }
    }

    /**
     * Verifies Main method with valid arguments
     *
     * @throws StockServiceException
     */
    @Test
    public void testMainPositive() throws StockServiceException {
        String[] args = { "AMZN", "2015-02-03 00:00:01", "2015-02-05 00:00:01", "DAY" };
        try {
            StockQuoteApplication.main(args);
        } catch (NullPointerException e) {
            fail("Passing valid arguments to main generates a NullPointerException");
        }
    }

    /**
     * Verifies Main method with null
     *
     * @throws StockServiceException
     */
    @Test(expected = NullPointerException.class)
    public void testMainNegative() throws StockServiceException {
        StockQuoteApplication.main(null);
    }
}
