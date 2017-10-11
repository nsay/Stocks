package edu.uml.nsay.services;

import org.apache.http.annotation.Immutable;

/**
 * This interface is for the IntervalEnum class. It will define methods for getting
 * the unit of time and amount of units for an enum in IntervalEnum.
 *
 * @author Narith Say
 * @version 10/02/2017
 */
@Immutable
public interface Interval {
    /**
     * @return a constant of the Calendar class in IntervalEnum for determining an interval of time
     */
    int unit();

    /**
     * @return the amount of the unit
     */
    int amount();
}
