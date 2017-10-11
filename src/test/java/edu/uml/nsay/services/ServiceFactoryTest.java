package edu.uml.nsay.services;

import org.apache.http.annotation.Immutable;
import org.junit.Test;
import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the StockServiceFactory class.
 *
 * @author Narith Say
 */
@Immutable
public final class ServiceFactoryTest {

    /**
     * Verifies correct instance of the specified class
     */
    @Test
    public final void testCreateNewStockServicePositive() {
        // compares method return value with expected result
        assertTrue("The value returned from createNewStockService is not an instance of StockService interface",
                StockServiceFactory.createNewStockService("database") instanceof StockService);
    }

    /**
     * Verifies incorrect instance of the specified class
     */
    @Test
    public final void testCreateNewStockServiceNegative() {
        // compares method return value with expected result
        assertFalse("The value returned from createNewStockService is an instance of Calendar class",
                StockServiceFactory.createNewStockService("database") instanceof Calendar);
    }
}
