package tars.model.task;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import tars.commons.core.Messages;
import tars.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's dateTime in tars.
 */
public class DateTime {
    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Task datetime should be spaces or alphanumeric characters";

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public String startDateString;
    public String endDateString;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu HHmm")
            .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter stringFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm");

    /**
     * Default constructor
     */
    public DateTime() {
    }

    /**
     * Validates given task dateTime.
     *
     * @throws DateTimeException
     *             if given dateTime string is invalid.
     * @throws IllegalDateException
     *             end date occurring before start date.
     */
    public DateTime(String startDate, String endDate) throws DateTimeException, IllegalDateException {
        if (endDate != null && endDate.length() > 0) {
            this.endDate = LocalDateTime.parse(endDate, formatter);
            this.endDateString = this.endDate.format(stringFormatter);
        }

        if (startDate != null && startDate.length() > 0) {
            this.startDate = LocalDateTime.parse(startDate, formatter);
            this.startDateString = this.startDate.format(stringFormatter);
            if (this.endDate.isBefore(this.startDate) || this.endDate.isEqual(this.startDate)) {
                throw new IllegalDateException(Messages.MESSAGE_INVALID_END_DATE);
            }
        }
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    @Override
    public String toString() {
        if (this.startDate != null && this.endDate != null) {
            return startDateString + " to " + endDateString;
        } else if (this.endDate != null) {
            return endDateString;
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                        && this.toString().equals(((DateTime) other).toString())); // state
                                                                                   // check
    }

    /**
     * Checks whether the dateTimeQuery falls within the range of the
     * dateTimeSource
     * 
     * @@author A0124333U
     * @param dateTimeSource
     * @param dateTimeQuery
     */
    public static boolean isDateTimeWithinRange(DateTime dateTimeSource, DateTime dateTimeQuery) {
        boolean isTaskDateWithinRange = true;

        // Return false if task is a floating task (i.e. no start or end
        // dateTime
        if (dateTimeSource.getEndDate() == null) {
            return false;
        }

        // Case 1: dateTimeQuery has a range of date (i.e. startDateTime &
        // endDateTime != null)
        if (dateTimeQuery.getStartDate() != null) {

            if (dateTimeSource.getEndDate().isBefore(dateTimeQuery.getStartDate())) {
                return false;
            }
            
            // Case 1a: dateTimeSource has a range of date 
            if (dateTimeSource.getStartDate() != null) {
                if (dateTimeSource.getStartDate().isAfter(dateTimeQuery.getEndDate())) {
                    return false;
                }
            } else {  //Case 1b: dateTimeSource only has a endDateTime
                if (dateTimeSource.getEndDate().isAfter(dateTimeQuery.getEndDate())) {
                    return false;
                }
            }
        } else { // Case 2: dateTimeQuery only has a endDateTime

            // Case 2a: dateTimeSource has a range of date  
            if (dateTimeSource.getStartDate() != null) {
                if (dateTimeQuery.getEndDate().isBefore(dateTimeSource.getStartDate())
                        || dateTimeQuery.getEndDate().isAfter(dateTimeSource.getEndDate())) {
                    return false;
                }
            } else { //Case 2b: dateTimeSource only has a endDateTime
                if (!dateTimeQuery.getEndDate().equals(dateTimeSource.getEndDate())) {
                    return false;
                }
            }
        }

        return isTaskDateWithinRange;
    }

    /**
     * Signals an error caused by end date occurring before start date
     */
    public class IllegalDateException extends IllegalValueException {
        public IllegalDateException(String message) {
            super(message);
        }
    }

}
