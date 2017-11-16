package edu.uml.nsay.util;

import edu.uml.nsay.services.StockServiceException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import org.apache.http.annotation.Immutable;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

import static edu.uml.nsay.util.DatabaseUtils.initializationFile;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the DatabaseUtils class.
 *
 * @author Narith Say
 */
@Immutable
public final class DatabaseUtilsTest {

    @Before
    public final void setUp() throws DatabaseInitializationException {
        DatabaseUtils.initializeDatabase(initializationFile);
    }
    /**
     * Test that the instance created by the factory is open
     */
    @Test
    public final void testGetSessionFactoryPositive() {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        assertTrue("getSessionFactory() return value is closed", session.isOpen());
    }

    /**
     * Test that the instance created by the factory is not set to "default read-only"
     */
    @Test
    public final void testGetSessionFactoryNegative() {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        assertFalse("getSessionFactory() return value is set to default read-only", session.isDefaultReadOnly());
    }

    /**
     * Test that the return value is a valid connection
     * @throws DatabaseConnectionException
     */
    @Test
    public final void testGetConnectionPositive() throws DatabaseConnectionException {
        assertTrue("getConnection() return value is not a valid connection", DatabaseUtils.getConnection() instanceof Connection);
    }

    /**
     * Test that the return value is not an invalid object
     * @throws DatabaseConnectionException
     */
    @Test
    public final void testGetConnectionNegative() throws DatabaseConnectionException {
        assertFalse("getConnection() return value is an invalid object", DatabaseUtils.getConnection() instanceof Calendar);
    }

    /**
     * Test that the connection can execute a statement
     * @throws DatabaseConnectionException
     * @throws DatabaseInitializationException
     * @throws SQLException
     */
    @Test
    public final void testInitializeDatabasePositive() throws DatabaseConnectionException, DatabaseInitializationException, SQLException {
        DatabaseUtils.initializeDatabase(initializationFile);
        assertTrue("getConnection() cannot execute a statement", DatabaseUtils.getConnection().createStatement().execute("select * from quotes"));
        DatabaseUtils.getConnection().createStatement().executeUpdate("DROP TABLE quotes;");
    }

    /**
     * Test that the connection cannot execute an invalid statement
     * @throws DatabaseConnectionException
     * @throws DatabaseInitializationException
     * @throws SQLException
     */
    @Test
    public final void testInitializeDatabaseNegative() throws DatabaseConnectionException, DatabaseInitializationException, SQLException {
        DatabaseUtils.initializeDatabase(initializationFile);
        boolean doesExecute = true;
        try {
            DatabaseUtils.getConnection().createStatement().execute("SELECT creme from oreo");
        } catch (MySQLSyntaxErrorException e) {
            doesExecute = false;
        }
        assertFalse("getConnection() executes an invalid statement", doesExecute);
        DatabaseUtils.getConnection().createStatement().executeUpdate("DROP TABLE quotes;");
    }


    /**
     * Test that persistXMLData() does not access and retrieve data from a nonexistent file
     * @throws StockServiceException
     * @throws XMLUnmarshalException
     */
    @Test(expected=XMLUnmarshalException.class)
    public final void testPersistXMLDataNegative() throws StockServiceException, XMLUnmarshalException {
        DatabaseUtils.persistXMLData("null.xml");
    }
}