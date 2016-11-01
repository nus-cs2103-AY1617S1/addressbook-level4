package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;

//@@author A0146123R
/**
 * Represents a Event's dates Guarantees: is valid as declared in
 * {@link #isValidEventDateFormat(String)}
 */
public class EventDate implements Date {

    public static final String MESSAGE_EVENT_DATE_CONSTRAINTS = "Event date must be a valid date";

    private String date;

    private String startDate;
    private String endDate;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException
     *             if given dates string is invalid.
     */
    public EventDate(String startDate, String endDate) throws IllegalValueException {
        assert startDate != null && endDate != null;
        this.startDate = getValidDate(startDate.trim());
        assert isValidEventDateFormat(this.startDate);
        this.endDate = getValidDate(endDate.trim());
        assert isValidEventDateFormat(this.endDate);
        this.date = this.startDate + " to " + this.endDate;
    }

    /**
     * Get a date that is valid and is in a valid event date format.
     */
    public static String getValidDate(String date) throws IllegalValueException {
        try {
            return DateUtil.parseDate(date);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalValueException(MESSAGE_EVENT_DATE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is in a valid event date format.
     */
    private static boolean isValidEventDateFormat(String test) {
        return DateUtil.isValidDateFormat(test);
    }

    @Override
    public String getValue() {
        return date;
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof EventDate && isSameEventDate((EventDate) other));
    }

    private boolean isSameEventDate(EventDate other) {
        return date.equals(other.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    // @@author A0142325R
    public void updateDate(String start, String end) {
        this.startDate = start;
        this.endDate = end;
        this.date = start + " to " + end;
    }
    // @@author

}
