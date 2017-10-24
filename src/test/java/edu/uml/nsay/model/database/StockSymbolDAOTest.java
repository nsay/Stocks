package edu.uml.nsay.model.database;

import edu.uml.nsay.util.DatabaseUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * JUnit test for StockSymbolDAO class
 *
 * @author Narith Say
 */
public class StockSymbolDAOTest {

    @Test
    public void testRead() {
        StockSymbolDAO stockSymbolDAO = DatabaseUtils.findUniqueResultBy("symbol", "APPL",
                StockSymbolDAO.class, true);
        assertTrue("APPL StockSymbolDAO found", stockSymbolDAO.getId() == 1);
    }
}
