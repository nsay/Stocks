package edu.uml.nsay.util;

import edu.uml.nsay.model.*;
import edu.uml.nsay.model.database.StockSymbolDAO;
import edu.uml.nsay.model.xml.XMLStocks;
import edu.uml.nsay.model.xml.XMLStocksList;
import edu.uml.nsay.services.*;
import com.ibatis.common.jdbc.ScriptRunner;
import org.apache.http.annotation.Immutable;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.joda.time.DateTime;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * A class that contains database related utility methods.
 *
 * @author Narith Say
 */
@Immutable
public final class  DatabaseUtils {
    // fields of this class
    public static final String initializationFile = "src/main/sql/stocks_db_initialization.sql";
    private static String HIBERNATE_CONFIGURATION_FILE = "hibernate.cfg.xml";
    private static String JDBC_DRIVER_CLASS_PROPERTY_KEY = "connection.driver_class";
    private static String DATABASE_USER_NAME = "hibernate.connection.username";
    private static String DATABASE_USER_PASSWORD = "hibernate.connection.password";
    private static String DATABASE_URL = "connection.url";
    private static SessionFactory sessionFactory;
    private static Configuration configuration;

    /**
     * @return SessionFactory for use with database transactions
     */
    public static SessionFactory getSessionFactory() {
        // singleton pattern
        synchronized (DatabasePersonService.class) {
            if (sessionFactory == null) {
                // applies configuration to service registry which instantiates session factory
                Configuration configuration = getConfiguration();
                ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .buildServiceRegistry();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
        }
        return sessionFactory;
    }

    /**
     * Create a new or return an existing database configuration object
     *
     * @return a Hibernate Configuration instance
     */
    private static Configuration getConfiguration() {
        // applies configuration from xml file
        synchronized (DatabaseUtils.class) {
            if (configuration == null) {
                configuration = new Configuration();
                configuration.configure(HIBERNATE_CONFIGURATION_FILE);
            }
        }
        return configuration;
    }

    /**
     * Gets a reference to the same database connection
     *
     * @return a {onnection to a database
     * @throws DatabaseConnectionException
     */
    public static Connection getConnection() throws DatabaseConnectionException {
        Connection connection = null;
        Configuration configuration = getConfiguration();
        try {
            Class.forName(configuration.getProperty(JDBC_DRIVER_CLASS_PROPERTY_KEY));
            String databaseUrl = configuration.getProperty(DATABASE_URL);
            String username = configuration.getProperty(DATABASE_USER_NAME);
            String password = configuration.getProperty(DATABASE_USER_PASSWORD);
            connection = DriverManager.getConnection(databaseUrl, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseConnectionException(e.getMessage(), e);
        }
        return connection;
    }

    /**
     * A utility method that runs a database initialize script
     *
     * @param initializationScript full path to the script to run to create the schema
     * @throws DatabaseInitializationException
     */
    public static void initializeDatabase(String initializationScript) throws DatabaseInitializationException {

        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(connection, false, false);
            InputStream inputStream = new FileInputStream(initializationScript);

            InputStreamReader reader = new InputStreamReader(inputStream);

            runner.runScript(reader);
            reader.close();
            connection.commit();
            connection.close();

        } catch (DatabaseConnectionException | SQLException |IOException e) {
            throw new DatabaseInitializationException("Could not initialize db because of:"
                    + e.getMessage(),e);
        }
    }

    /**
     * Retrieves XML data in the form of XML domain objects, which are converted to database access objects
     * and stored in the database configuration defined in the hibernate xml file
     *
     * @param xmlData a String containing a reference to the file containing the XML data to be persisted to the database
     */
    public static final void persistXMLData(String xmlData) throws XMLUnmarshalException, StockServiceException {
        XMLStocksList quoteList = null;
        quoteList = XMLUtils.unmarshal(xmlData);
        List<XMLStocks> xmlQuotes = quoteList.getStock();
        DatabaseStockService service = (DatabaseStockService) ServiceFactory.getStockService(ServiceType.DATABASE);
        for (XMLStocks quote : xmlQuotes) {
            service.addOrUpdateQuote(DateTime.parse(quote.getTime(), StockQuote.getDateFormatter()), new BigDecimal(quote.getPrice()), new StockSymbolDAO(quote.getSymbol()));
        }
    }
}
