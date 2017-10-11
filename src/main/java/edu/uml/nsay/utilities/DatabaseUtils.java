package edu.uml.nsay.utilities;

import com.ibatis.common.jdbc.ScriptRunner;
import org.apache.http.annotation.Immutable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class that contains database related utility methods.
 *
 * @author Narith Say
 */
@Immutable
public final class DatabaseUtils {

    // Database URL and location
    private static final String DB_URL = "jdbc:mysql://localhost:3306/stocks";
    public static final String DB_LOCATION = "src/main/sql/stocks_db_initialization.sql";

    // Database credentials
    // ONLY FOR THIS USE. REMOVE Later
    private static final String USER = "monty";
    private static final String PASS = "mpython1";

    /**
     * Obtain connection to local database
     *
     * @return a connection to a database
     * @throws DatabaseConnectionException
     */
    public static Connection getConnection() throws DatabaseConnectionException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseConnectionException("Could not connection to database." + e.getMessage(), e);
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
}
