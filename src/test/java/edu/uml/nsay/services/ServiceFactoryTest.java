package edu.uml.nsay.services;

import org.apache.http.annotation.Immutable;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the ServiceFactory class.
 *
 * @author Narith Say
 */
@Immutable
public final class ServiceFactoryTest {

    /**
     * Test that the return value is an instance of the specified class
     * @throws StockServiceException
     */
    @Test
    public final void testCreateDatabaseStockServicePositive() throws StockServiceException {
        // compares method return value with expected result
        assertTrue("The value returned from getStockService is not an instance of StockService interface",
                ServiceFactory.getStockService(ServiceType.DATABASE) instanceof StockService);
    }

    /**
     * Test that the return value is not an instance of the specified class
     * @throws StockServiceException
     */
    @Test
    public final void testCreateDatabaseStockServiceNegative() throws StockServiceException {
        // compares method return value with expected result
        assertFalse("The value returned from getStockService is an instance of Calendar class",
                ServiceFactory.getStockService(ServiceType.DATABASE) instanceof Calendar);
    }


    /**
     * Test that the return value is an instance of the specified class
     * @throws StockServiceException
     */
    @Test
    public final void testCreateYAHOOStockServicePositive() throws StockServiceException {
        // compares method return value with expected result
        assertTrue("The value returned from getStockService is not an instance of StockService interface",
                ServiceFactory.getStockService(ServiceType.YAHOO) instanceof StockService);
    }

    /**
     * Test that the return value is not an instance of the specified class
     * @throws StockServiceException
     */
    @Test
    public final void testCreateYAHOOStockServiceNegative() throws StockServiceException {
        // compares method return value with expected result
        assertFalse("The value returned from getStockService is an instance of Calendar class",
                ServiceFactory.getStockService(ServiceType.YAHOO) instanceof Calendar);
    }

    /**
     * Test that the return value is an instance of the specified class
     */
    @Test
    public final void testCreatePersonServicePositive() {
        // compares method return value with expected result
        assertTrue("The value returned from getPersonService() is not an instance of StockService interface",
                ServiceFactory.getPersonService() instanceof PersonService);
    }

    /**
     * Test that the return value is not an instance of the specified class
     */
    @Test
    public final void testCreatePersonServiceNegative() {
        // compares method return value with expected result
        assertFalse("The value returned from getPersonService() is an instance of Calendar class",
                ServiceFactory.getPersonService() instanceof Calendar);
    }
}
