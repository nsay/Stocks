package edu.uml.nsay.util;

import org.apache.http.annotation.Immutable;

/**
 * This interface requires that implementing classes define methods for getting the unit of time
 * and amount of units for an enum.
 *
 * @author Narith Say
 */
@Immutable
public interface Interval {

    /**
     * @return the amount of the unit desired
     */
    int amount();
}
