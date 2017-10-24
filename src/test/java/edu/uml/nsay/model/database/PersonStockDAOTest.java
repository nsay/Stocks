package edu.uml.nsay.model.database;

import edu.uml.nsay.util.DatabaseUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for PersonStockDAO class
 */
public class PersonStockDAOTest {

    @Test
    public void testRead() {
        PersonStockDAO personStocksDAO = DatabaseUtils.findUniqueResultBy("id", 1, PersonStockDAO.class, true);
        assertTrue("first PersonStocksDAO found", personStocksDAO.getId() == 1);
    }

}