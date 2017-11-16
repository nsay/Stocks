package edu.uml.nsay.services;

import org.apache.http.annotation.Immutable;

/**
 * A factory that returns a StockService instance.
 *
 * @author Narith Say
 */
@Immutable
public final class ServiceFactory {

    /**
     * Hides the constructor because this class contains only static methods
     */
    private ServiceFactory() {
    }

    /**
     * Constructs a new StockService instance
     *
     * @return an object implementing the StockService interface
     */
    public static final StockService getStockService(ServiceType type) throws StockServiceException {
        if (type.equals(ServiceType.DATABASE)) {
            return new DatabaseStockService();
        } else if (type.equals(ServiceType.YAHOO)) {
            return new YahooStockService();
        } else {
            throw new StockServiceException("Argument specifies an invalid ServiceType");
        }
    }

    /**
     * Constructs a new PersonService instance
     *
     * @return an object implementing the PersonService interface
     */
    public static final PersonService getPersonService() {
        return new DatabasePersonService();
    }
}
