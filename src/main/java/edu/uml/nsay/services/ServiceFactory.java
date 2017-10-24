package edu.uml.nsay.services;

/**
 * A factory that returns a <CODE>StockService</CODE> instance.
 *
 * @author Narith Say
 */
public class ServiceFactory {

    /**
     * Prevent instantiations
     */
    private ServiceFactory() {}

    /**
     *
     * @return get a <CODE>StockService</CODE> instance
     */
    public static StockService getStockService() {
        return new DatabaseStockService();
    }

    /**
     *
     * @return get a <CODE>UserService</CODE> instance
     */
    public static UserService getUserService() { return new DatabaseUserService(); }
}
