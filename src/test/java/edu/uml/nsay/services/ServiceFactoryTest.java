package edu.uml.nsay.services;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * JUnit test for <CODE>ServiceFactory</CODE>
 *
 * @author Narith Say
 */
public class ServiceFactoryTest {

    @Test
    public void testGetStockServiceInstance() {
        StockService stockService = ServiceFactory.getStockService();
        assertNotNull(stockService);
    }

    @Test
    public void testGetUserServiceInstance() {
        UserService userService = ServiceFactory.getUserService();
        assertNotNull(userService);
    }
}