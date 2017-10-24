package edu.uml.nsay.app;

import edu.uml.nsay.model.StockQuery;
import edu.uml.nsay.model.StockQuote;
import edu.uml.nsay.model.database.StockSymbolDAO;
import edu.uml.nsay.model.xml.Stocks;
import edu.uml.nsay.services.DatabaseStockService;
import edu.uml.nsay.services.ServiceFactory;
import edu.uml.nsay.services.StockService;
import edu.uml.nsay.services.StockServiceException;
import edu.uml.nsay.util.Interval;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

/**
 * A simple application that shows the StockService in action.
 *
 * It doesn't print out any stock information. This application
 * confirms that StockService is working with the correct arguments.
 *
 * Example args format: AMZN "2015-02-10 00:00:01" "2015-02-13 00:00:01"
 *
 * @author Narith Say
 */
public class BasicStockApplication {

    private StockService stockService;
    private static String xmlInstance ="<stocks>\n" +
            "    <stock symbol=\"VNET\" price=\"110.10\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"AGTK\" price=\"120.10\" time=\"2015-02-10 00:00:01\"/>\n" +
            "</stocks>";

    /**
     * An enumeration that indicates how the program terminates (ends)
     */
    private enum ProgramTerminationStatusEnum {

        NORMAL(0),
        ABNORMAL(-1);

        // when the program exits, this value will be reported to underlying OS
        private int statusCode;

        /**
         * Create a new  ProgramTerminationStatusEnum
         *
         * @param statusCodeValue the value to return the OS. A value of 0
         *                        indicates success or normal termination.
         *                        non 0 numbers indicate abnormal termination.
         */
        private ProgramTerminationStatusEnum(int statusCodeValue) {
            this.statusCode = statusCodeValue;
        }

        /**
         * @return The value sent to OS when the program ends.
         */
        private int getStatusCode() {
            return statusCode;
        }
    }

    /**
     * Create a new Application.
     *
     * @param stockService the StockService this application instance should use for
     *                     stock queries.
     */
    public BasicStockApplication(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Given a stockQuery get back a the info about the stock to display to th user.
     *
     * @param stockQuery the stock to get data for.
     * @return a String with the stock data in it.
     * @throws StockServiceException If data about the stock can't be retrieved. This is a
     *                               fatal error.
     */
    public String displayStockQuotes(StockQuery stockQuery) throws StockServiceException {
        StringBuilder stringBuilder = new StringBuilder();

        List<StockQuote> stockQuotes =
                stockService.getQuote(stockQuery.getSymbol(),
                        stockQuery.getFrom(),
                        stockQuery.getUntil(),
                        Interval.DAY); // get one quote for each day in the from until date range.

        stringBuilder.append("Stock quotes for: " + stockQuery.getSymbol() + "\n");
        for (StockQuote stockQuote : stockQuotes) {
            stringBuilder.append(stockQuote.toString());
        }

        return stringBuilder.toString();
    }

    /**
     * Terminate the application.
     *
     * @param statusCode        an enum value that indicates if the program terminated ok or not.
     * @param diagnosticMessage A message to display to the user when the program ends.
     *                          This should be an error message in the case of abnormal termination
     */
    private static void exit(ProgramTerminationStatusEnum statusCode, String diagnosticMessage) {
        if (statusCode == ProgramTerminationStatusEnum.NORMAL) {
            System.out.println(diagnosticMessage);
        } else if (statusCode == ProgramTerminationStatusEnum.ABNORMAL) {
            System.err.println(diagnosticMessage);
        } else {
            throw new IllegalStateException("Unknown ProgramTerminationStatusEnum.");
        }
        System.exit(statusCode.getStatusCode());
    }

    /**
     * Run the StockTicker application.
     *
     * @param args one or more stock symbols
     * @throws StockServiceException
     * @throws JAXBException
     */
    public static void main(String[] args) throws StockServiceException, JAXBException {

        // be optimistic init to positive values
        ProgramTerminationStatusEnum exitStatus = ProgramTerminationStatusEnum.NORMAL;
        String programTerminationMessage = "Normal program termination.";

        // convert from XML to Java
        JAXBContext jaxbContext = JAXBContext.newInstance(Stocks.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Stocks quotes = (Stocks) unmarshaller.unmarshal(new StringReader(xmlInstance));
        System.out.println(quotes.toString());

        // convert from Java to XML
        JAXBContext context = JAXBContext.newInstance(Stocks.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(quotes, System.out);

       //Retrieves XML data in the form of XML domain objects, which are converted to database access object
        //and stored in the database configuration defined in the hibernate xml file
        try {
            List<Stocks.Stock> xmlStocks = quotes.getStock();
            DatabaseStockService service = (DatabaseStockService) ServiceFactory.getStockService();
            for (Stocks.Stock stock : xmlStocks) {
                service.addOrUpdateQuote(new Timestamp(Long.parseLong(stock.getTime())),
                        new BigDecimal(stock.getPrice()), new StockSymbolDAO(stock.getSymbol()));
            }
        }catch (NumberFormatException e) {
            exitStatus = ProgramTerminationStatusEnum.ABNORMAL;
            programTerminationMessage = "Invalid date: " + e.getMessage();
        }

        if (args.length != 3) {
            exit(ProgramTerminationStatusEnum.ABNORMAL,
                    "Please supply 3 arguments a stock symbol, a start date \"yyyy-MM-dd 00:00:01\" and end date \"yyyy-MM-dd 00:00:01\"");
        }
        try {

            StockQuery stockQuery = new StockQuery(args[0], args[1], args[2]);
            StockService stockService = ServiceFactory.getStockService();
            BasicStockApplication basicStockQuoteApplication =
                    new BasicStockApplication(stockService);
            basicStockQuoteApplication.displayStockQuotes(stockQuery);

        } catch (ParseException e) {
            exitStatus = ProgramTerminationStatusEnum.ABNORMAL;
            programTerminationMessage = "Invalid date data: " + e.getMessage();
        } catch (StockServiceException e) {
            exitStatus = ProgramTerminationStatusEnum.ABNORMAL;
            programTerminationMessage = "StockService failed: " + e.getMessage();
        }  catch (Throwable t) {
            exitStatus = ProgramTerminationStatusEnum.ABNORMAL;
            programTerminationMessage = "General application error: " + t.getMessage();
        }

        exit(exitStatus, programTerminationMessage);
        System.out.println("Oops could not parse a date");
    }
}

