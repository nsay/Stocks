package edu.uml.nsay.app;

import edu.uml.nsay.model.StockQuote;
import edu.uml.nsay.model.xml.XMLStocksList;
import edu.uml.nsay.services.*;
import edu.uml.nsay.services.StockServiceException;
import edu.uml.nsay.util.Interval;
import org.apache.http.annotation.Immutable;
import org.joda.time.DateTime;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple application that shows the StockService in action.
 *
 * This application confirms that StockService is working with the correct arguments.
 *
 * Example args format: AMZN "2015-02-10 00:01" "2015-02-13 00:01"
 *
 * @author Narith Say
 */
@Immutable
public final class BasicStockApplication {
    // fields of this class
    private final StockService stockService;
    private static String xmlInstance ="<stocks>\n" +
            "    <stock symbol=\"VNET\" price=\"110.10\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"AGTK\" price=\"120.10\" time=\"2015-02-10 00:00:01\"/>\n" +
            "</stocks>";

    /**
     * Constructs a new {@code BasicStockApplication} instance
     *
     * @param stockService used to get actual stock data (external dependency)
     */
    public BasicStockApplication(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Gets a List of StockQuote instances for the specified date range
     *
     * @param symbol  symbol for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange   end of the date range
     * @param interval time elapsed between stockQuote instances
     * @return a List containing one StockQuote instance per interval in the specified date range
     * @throws StockServiceException
     */
    public List<StockQuote> getStockHistory(String symbol, DateTime startRange, DateTime endRange, Interval interval) throws StockServiceException {
        // set up variables for getting and storing {@code StockQuotes}
        List<StockQuote> returnValue = new ArrayList();
        returnValue.addAll(stockService.getQuote(symbol, startRange, endRange, interval));
        return returnValue;
    }

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
     * A main method which enables program execution
     *
     * @param args an array that should contain as elements:
     *             String representations of a valid stock symbol, start date, end date and interval of time
     * @throws StockServiceException
     * @throws JAXBException
     */
    public static void main(String[] args) throws StockServiceException, JAXBException {
        //StockService databaseService = ServiceFactory.getStockService(ServiceType.DATABASE);
        ProgramTerminationStatusEnum exitStatus = ProgramTerminationStatusEnum.NORMAL;
        String programTerminationMessage = "Normal program termination.";


        // convert from XML to Java
        JAXBContext jaxbContext = JAXBContext.newInstance(XMLStocksList.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        XMLStocksList quotes = (XMLStocksList) unmarshaller.unmarshal(new StringReader(xmlInstance));


        // convert from Java to XML
        JAXBContext context = JAXBContext.newInstance(XMLStocksList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);


        if (args.length != 3) {
            exit(ProgramTerminationStatusEnum.ABNORMAL,
                    "Please supply 3 arguments a stock symbol, a start date \"yyyy-MM-dd 00:00:01\" and end date \"yyyy-MM-dd 00:00:01\"");
        }
        try {
            StockService databaseService = ServiceFactory.getStockService(ServiceType.DATABASE);
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
