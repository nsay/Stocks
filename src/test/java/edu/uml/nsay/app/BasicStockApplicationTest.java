package edu.uml.nsay.app;

import edu.uml.nsay.model.StockQuery;
import edu.uml.nsay.model.StockQuote;
import edu.uml.nsay.services.StockService;
import edu.uml.nsay.services.StockServiceException;
import edu.uml.nsay.util.Interval;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for BasicStockApplication
 *
 * @author Narith Say
 */
public class BasicStockApplicationTest {

    private BasicStockApplication basicStockQuoteApplication;
    private StockService stockServiceMock;

    @Before
    public void setUp() {
        stockServiceMock = mock(StockService.class);
    }

    @Test
    public void testValidConstruction() {
        basicStockQuoteApplication = new BasicStockApplication(stockServiceMock);
        assertNotNull("Basic construction works");
    }

    @Test
    public void testDisplayResults() throws ParseException, StockServiceException {
        basicStockQuoteApplication = new BasicStockApplication(stockServiceMock);
        String symbol = "APPL";
        String from = "2011-10-29 12:12:12";    //yyyy-MM-dd HH:mm:ss
        String until = "2014-11-29 12:12:12";
        StockQuery stockQuery = new StockQuery(symbol, from, until);

        List<StockQuote> stockQuotes = new ArrayList<>();
        StockQuote stockQuoteFromDate = new StockQuote(new BigDecimal(100), stockQuery.getFrom().getTime(), stockQuery.getSymbol());
        stockQuotes.add(stockQuoteFromDate);
        StockQuote stockQuoteUntilDate = new StockQuote(new BigDecimal(100), stockQuery.getUntil().getTime(), stockQuery.getSymbol());
        stockQuotes.add(stockQuoteUntilDate);

        when(stockServiceMock.getQuote(any(String.class),
                any(Calendar.class),
                any(Calendar.class),
                any(Interval.class))).thenReturn(stockQuotes);

        String output = basicStockQuoteApplication.displayStockQuotes(stockQuery);
        assertTrue("make sure symbol appears in output", output.contains(symbol));
        assertTrue("make sure from date appears in output", output.contains(from));
        assertTrue("make sure until date in output", output.contains(until));

    }

    /**
     * Verifies that the main function, when passed an invalid argument, generates a
     * NullPointerException or NumberFormatException
     *
     * @throws StockServiceException
     * @throws JAXBException
     */
    @Test(expected = NullPointerException.class)
    public void testMainNegative() throws StockServiceException, JAXBException {
        BasicStockApplication.main(null);
    }
}