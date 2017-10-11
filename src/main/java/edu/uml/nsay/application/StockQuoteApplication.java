package edu.uml.nsay.application;

import edu.uml.nsay.model.StockQuote;
import edu.uml.nsay.services.*;
import edu.uml.nsay.services.StockServiceException;
import org.apache.http.annotation.Immutable;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;
import java.util.ArrayList;


/**
 * This driver class has a main method that calls the factory class and
 * outputs stock information (symbol, date and price). The user first input
 * four arguments and then it'll output the stock information from start to
 * end date for each interval.
 *
 * For this class to work, the user must first input four arguments (Stock symbol,
 * Start Date, End Date and Interval).
 *
 * Each argument is hard coded in the BasicStockService class so the user must
 * follow it's given data:
 *
 * - stock symbol - must use given hard coded symbol, "AMZN"
 * - start date - the beginning of the period to look for stock in for the provided symbol must match
 *                given hard coded date. e.g. 2015-02-03 00:00:01
 * - end date - the end time of the period to look for stock in for the provided symbol must be within
 *              2 days after start date. e.g. 2015-02-05 00:00:01
 * - interval - how often during the specified period to look for quotes must be DAY, HALFDAY or WEEK
 *
 *
 * e.g. for args: AMZN "2015-02-03 00:00:01" "2015-02-05 00:00:01" DAY
 *
 * [ AMZN 2015-02-07 00:00:01 $342.00 ]
 * [ [ AMZN 2015-02-03 00:00:01 $363.00 ] ,
 * [ AMZN 2015-02-04 00:00:01 $433.00 ] ,
 * [ AMZN 2015-02-05 00:00:01 $782.00 ] ]
 *
 *
 * @author  Narith Say.
 * @version 10/02/2017
 *
 */
@Immutable
public final class StockQuoteApplication {
    // fields of this class
    private final StockService stockService;

    /**
     * Create a new Application.
     *
     * @param stockService the StockService this application instance should use for
     *                     stock queries.
     */
    public StockQuoteApplication(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Gets a list of StockQuote instances for the specified date range
     *
     * @param symbol        symbol for the company issuing the stock
     * @param startRange    beginning of the date range
     * @param endRange      end of the date range
     * @param interval      time elapsed between stockQuote instances
     * @return a StockQuote instance per interval in the specified date range
     * @throws StockServiceException
     */
    public List<StockQuote> getStockHistory(String symbol, DateTime startRange, DateTime endRange,
                                            Interval interval) throws StockServiceException {

        // set up variables for getting and storing StockQuote instances
        List<StockQuote> returnValue = new ArrayList();
        returnValue.addAll(stockService.getQuote(symbol, startRange, endRange, interval));
        return returnValue;
    }

    /**
     * A main method which enables program execution
     *
     * @param args an array that should contain a valid stock symbol, start date, end date and interval
     * @throws StockServiceException
     */
    public static void main(String[] args) throws StockServiceException {
        StockService databaseService = StockServiceFactory.createNewStockService("database");
        System.out.println(databaseService.getQuote(args[0]));

        DateTimeFormatter formatter = StockQuote.getDateFormatter();
        DateTime startRange = DateTime.parse(args[1], formatter);
        DateTime endRange = DateTime.parse(args[2], formatter);

        Interval interval = IntervalEnum.valueOf(args[3]);
        System.out.println(databaseService.getQuote(args[0], startRange, endRange));
        System.out.println(databaseService.getQuote(args[0], startRange, endRange, interval));
    }
}
