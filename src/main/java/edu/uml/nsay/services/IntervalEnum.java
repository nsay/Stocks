package edu.uml.nsay.services;

import org.apache.http.annotation.Immutable;

import java.util.Calendar;

/**
 * This enum implements the Interval interface. Each enum-type enumerates unit(),
 * which returns a constant of the Calendar class, and the desired amount of each unit.
 *
 * @author Narith Say
 * @version 10/02/2017
 */
@Immutable
public enum IntervalEnum implements Interval {

    // An enumeration for half a day
    HALFDAY("HALFDAY") {
        public final int unit() {
            return (Calendar.HOUR_OF_DAY);
        }
        @Override public final int amount() {
            return 12;
        }
    },

    // An enumeration for a day
    DAY("DAY") {
        public final int unit() {
            return Calendar.DAY_OF_YEAR;
        }
    },

    // An enumeration for a week
    WEEK("WEEK") {
        public final int unit() {
            return Calendar.WEEK_OF_YEAR;
        }
    };

    // private fields of this class
    private final String symbol;

    /**
     * Create a new IntervalEnum instance
     *
     * @param symbol
     */
    IntervalEnum(String symbol) { this.symbol = symbol; }

    /**
     * @return symbol of this instance
     */
    @Override
    public final String toString() { return this.symbol; }

    /**
     * @return a value of 1, until overridden by enumerations
     */
    public int amount() { return 1; }
}
