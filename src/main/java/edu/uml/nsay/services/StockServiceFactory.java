package edu.uml.nsay.services;

import org.apache.http.annotation.Immutable;

/**
 * A factory that returns a StockService instance.
 *
 * @author Narith Say
 */
@Immutable
public final class StockServiceFactory {

    /**
     * Prevent instantiations
     */
    private StockServiceFactory() {}

    /**
     * @return get a StockService instance
     */
    public static final StockService createNewStockService(String type) {
        switch (type) {
            case ("database"):
                return new DatabaseStockService();
            default:
                throw new RuntimeException();
        }
    }
}
