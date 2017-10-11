package edu.uml.nsay.utilities;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import org.apache.http.annotation.Immutable;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the DatabaseUtils class.
 * @author Narith Say
 */
@Immutable
public final class DatabaseUtilsTest {

    @Test
    public void testGetConnection() throws Exception{
        Connection connection = DatabaseUtils.getConnection();
        assertNotNull("verify that we can get a connection ok",connection);
    }

    @Test
    public void testGetConnectionWorks() throws Exception{
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        boolean execute = statement.execute("select * from quotes");
        assertTrue("verify that we can execute a statement",execute);
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
        DatabaseUtils.initializeDatabase(DatabaseUtils.DB_LOCATION);
        assertTrue("getConnection() cannot execute a statement", DatabaseUtils.getConnection().createStatement().execute("select * from quotes"));
    }

    /**
     * Verifies that the connection cannot execute an invalid statement
     * @throws DatabaseConnectionException
     * @throws DatabaseInitializationException
     * @throws SQLException
     */
    @Test
    public final void testInitializeDatabaseNegative() throws DatabaseConnectionException, DatabaseInitializationException, SQLException {
        DatabaseUtils.initializeDatabase(DatabaseUtils.DB_LOCATION);
        boolean doesExecute = true;
        try {
            DatabaseUtils.getConnection().createStatement().execute("SELECT john from FCBK");
        } catch (MySQLSyntaxErrorException e) {
            doesExecute = false;
        }
        assertFalse("getConnection() executes an invalid statement", doesExecute);
    }
}