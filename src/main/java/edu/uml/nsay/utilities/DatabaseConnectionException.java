package edu.uml.nsay.utilities;

import org.apache.http.annotation.Immutable;

/**
 * This class is used to signal a problem connecting to a database.
 *
 * @author Narith Say
 */
@Immutable
public final class DatabaseConnectionException  extends Exception {

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  Note that the detail message associated with
     * {@code cause} is not automatically incorporated in
     * this exception's detail message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.4
     */
    public DatabaseConnectionException(String message, Throwable cause) { super(message, cause); }
}
