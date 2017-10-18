package edu.uml.nsay.services;

import org.apache.http.annotation.Immutable;

/**
 * @author Narith Say
 */
@Immutable
public final class PersonServiceException extends Exception {

    /**
     * @param message describes the exception
     */
    public PersonServiceException(String message) {
        super(message);
    }

    /**
     * @param message describes the exception
     * @param cause   if this exception is caused by another exception
     */
    public PersonServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
