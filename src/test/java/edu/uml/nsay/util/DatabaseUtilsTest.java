package edu.uml.nsay.util;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import org.hibernate.Session;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *  Tests for the DatabaseUtils class
 *
 *  @author Narith Say
 */
public class DatabaseUtilsTest {

    @Test
    public void testGoodInitFile() throws Exception {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
    }

    @Test(expected = DatabaseInitializationException.class)
    public void testBadInitFile() throws Exception {
        DatabaseUtils.initializeDatabase("bogus");
    }

    @Test
    public void testGetConnection() throws Exception{
        Connection connection = DatabaseUtils.getConnection();
        assertNotNull("verify that we can get a connection ok",connection);
    }

    @Test
    public void testGetConnectionWorks() throws Exception{
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        boolean execute = statement.execute("select * from quote");
        assertTrue("verify that we can execute a statement",execute);
    }

    /**
     * Verifies that the instance created by the factory is open
     */
    @Test
    public final void testGetSessionFactoryPositive() {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        assertTrue("getSessionFactory() return value is closed", session.isOpen());
    }

    /**
     * Verifies that the instance created by the factory is not set to "default read-only"
     */
    @Test
    public final void testGetSessionFactoryNegative() {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        assertFalse("getSessionFactory() return value is set to default read-only", session.isDefaultReadOnly());
    }

    /**
     * Verifies that the return value is a valid connection
     * @throws DatabaseConnectionException
     */
    @Test
    public final void testGetConnectionPositive() throws DatabaseConnectionException {
        assertTrue("getConnection() return value is not a valid connection", DatabaseUtils.getConnection() instanceof Connection);
    }

    /**
     * Verifies that the return value is not an invalid object
     * @throws DatabaseConnectionException
     */
    @Test
    public final void testGetConnectionNegative() throws DatabaseConnectionException {
        assertFalse("getConnection() return value is an invalid object", DatabaseUtils.getConnection() instanceof Calendar);
    }

    /**
     * Verifies that the connection can execute a statement
     * @throws DatabaseConnectionException
     * @throws DatabaseInitializationException
     * @throws SQLException
     */
    @Test
    public final void testInitializeDatabasePositive() throws DatabaseConnectionException, DatabaseInitializationException, SQLException {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
        assertTrue("getConnection() cannot execute a statement", DatabaseUtils.getConnection().createStatement().execute("select * from quote"));
    }

    /**
     * Verifies that the connection cannot execute an invalid statement
     * @throws DatabaseConnectionException
     * @throws DatabaseInitializationException
     * @throws SQLException
     */
    @Test
    public final void testInitializeDatabaseNegative() throws DatabaseConnectionException, DatabaseInitializationException, SQLException {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
        boolean doesExecute = true;
        try {
            DatabaseUtils.getConnection().createStatement().execute("SELECT creme from oreo");
        } catch (MySQLSyntaxErrorException e) {
            doesExecute = false;
        }
        assertFalse("getConnection() executes an invalid statement", doesExecute);;
    }
}
