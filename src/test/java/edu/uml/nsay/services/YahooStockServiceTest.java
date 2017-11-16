package edu.uml.nsay.services;

import edu.uml.nsay.model.StockQuote;
import edu.uml.nsay.util.HoursInterval;
import edu.uml.nsay.util.Interval;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the YahooStockService class.
 *
 * @author Narith Say
 */
public class YahooStockServiceTest {
    // private fields of this class
    private static final String symbol = "AAPL";
    private static DateTime startRange;
    private static DateTime endRange;
    private static StockService service;
    private static final int NUMBER_OF_DAYS = 100;
    private static Interval interval;

    /**
     * Sets up the logic common to all tests of this class
     * @throws StockServiceException
     */
    @Before
    public final void setUp() throws StockServiceException {
        service = ServiceFactory.getStockService(ServiceType.DATABASE);
        startRange = DateTime.now().minusDays(NUMBER_OF_DAYS).withTimeAtStartOfDay();
        endRange = DateTime.now().withTimeAtStartOfDay();
        interval = HoursInterval.DAY;
    }

    /**
     * Test that the symbol returned from getQuote is the same as the symbol passed as an argument
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgStockSymbolPositive() throws StockServiceException {
        assertTrue("Symbol returned from getQuote is not same as symbol passed as argument", service.getQuote(symbol).getSymbol().equals(symbol));
    }

    /**
     * Test that the symbol returned from getQuote is not the same as an empty string
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgStockSymbolNegative() throws StockServiceException {
        assertFalse("Symbol returned from getQuote is the same as an invalid symbol", service.getQuote(symbol).getSymbol().equals(""));
    }

    /**
     * Test that the time returned from getQuote is not after the current time
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgTimePositive() throws StockServiceException {
        assertTrue("Time returned from getQuote is after the current time", !service.getQuote(symbol).getTime().isAfter(DateTime.now()));
    }

    /**
     * Test that the time returned from getQuote is not the same as a future time
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgTimeNegative() throws StockServiceException { assertFalse("Time returned from getQuote is the same as a future time", service.getQuote(symbol).getTime().getMillis() == (DateTime.now().getMillis() + 1000));
    }

    /**
     * Test that the symbols returned from getQuote are the same as the symbol passed as an argument
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void testGetQuoteTripleArgStockSymbolPositive() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, startRange, endRange);
        for (StockQuote q : qList) {
            assertTrue("Symbol returned from getQuote is not same as symbol passed as argument", q.getSymbol().equals(symbol));
        }
    }

    /**
     * Test that the symbols returned from getQuote are not the same as an empty string
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void testGetQuoteTripleArgStockSymbolNegative() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, startRange, endRange);
        for (StockQuote q : qList) {
            assertFalse("Symbol returned from getQuote is the same as an invalid symbol", q.getSymbol().equals(""));
        }
    }

    /**
     * Test that the times returned from getQuote are within the parameter range
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void testGetQuoteTripleArgTimePositive() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, startRange, endRange);
        for (StockQuote q : qList) {
            assertTrue("Time returned from getQuote is not within parameter range", !q.getTime().isBefore(startRange) || !q.getTime().isAfter(endRange));
        }
    }

    /**
     * Test that the times returned from getQuote are within the parameter range
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void testGetQuoteTripleArgTimeNegative() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, startRange, endRange);
        for (StockQuote q : qList) {
            assertFalse("Time returned from getQuote is not within parameter range", q.getTime().isBefore(startRange) || q.getTime().isAfter(endRange));
        }
    }

    /**
     * Test that the symbols returned from getQuote are the same as the symbol passed as an argument
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void testGetQuoteQuadArgStockSymbolPositive() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, startRange, endRange, interval);
        System.out.println(interval.toString());
        for (StockQuote q : qList) {
            assertTrue("Symbol returned from getQuote is not same as symbol passed as argument", q.getSymbol().equals(symbol));
        }
    }

    /**
     * Test that the symbols returned from getQuote are not the same as an empty string
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void testGetQuoteQuadArgStockSymbolNegative() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, startRange, endRange, interval);
        for (StockQuote q : qList) {
            assertFalse("Symbol returned from getQuote is the same as an invalid symbol", q.getSymbol().equals(""));
        }
    }

    /**
     * Test that the times returned from getQuote are within the parameter range
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void testGetQuoteQuadArgTimePositive() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, startRange, endRange, interval);
        for (StockQuote q : qList) {
            assertTrue("Time returned from getQuote is not within parameter range", !q.getTime().isBefore(startRange) && !q.getTime().isAfter(endRange));
        }
    }

    /**
     * Test that the times returned from getQuote are within the parameter range
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void testGetQuoteQuadArgTimeNegative() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, startRange, endRange, interval);
        for (StockQuote q : qList) {
            assertFalse("Time returned from getQuote is not within parameter range", q.getTime().isBefore(startRange) || q.getTime().isAfter(endRange));
        }
    }
}
