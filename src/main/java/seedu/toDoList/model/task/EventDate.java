package seedu.toDoList.model.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import seedu.toDoList.commons.exceptions.IllegalValueException;
import seedu.toDoList.commons.util.DateUtil;

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
        return other == this || ( other instanceof EventDate && isSameEventDate((EventDate) other) );
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

    /**
     * Override the updateRecurringDate in Date interface. Change current date
     * value to "numOfDays" after. Update this.date value
     */
    @Override
    public void updateRecurringDate(long numOfDays) {
        LocalDate startDate = getLocalDate().get(0);
        LocalDate endDate = getLocalDate().get(1);
        String startDateToUpdate = DateUtil.getFormattedDateString(startDate.plusDays(numOfDays));
        String endDateToUpdate = DateUtil.getFormattedDateString(endDate.plusDays(numOfDays));
        updateDate(startDateToUpdate, endDateToUpdate);

    }

    /**
     * Override getLocalDate method in Date interface. return startDate and
     * endDate value in an arrayList of LocalDate
     */
    @Override
    public ArrayList<LocalDate> getLocalDate() {
        assert startDate != null && endDate != null;
        ArrayList<LocalDate> dateArray = new ArrayList<LocalDate>();
        dateArray.add(LocalDate.parse(startDate.substring(0, 10), DateUtil.getGermanFormatter()));
        dateArray.add(LocalDate.parse(endDate.substring(0, 10), DateUtil.getGermanFormatter()));
        return dateArray;
    }

    /**
     * Override the updateDate in Date interface. Update startDate, endDate, and
     * date attribute to the updated version
     */
    @Override
    public void updateDate(String... dateString) {
        assert dateString.length == 2;
        this.startDate = getUpdatedString(startDate, dateString[0]);
        this.endDate = getUpdatedString(endDate, dateString[1]);
        this.date = this.startDate + " to " + this.endDate;

    }

    /**
     * Get updated startTime and endTime string with time value attached if
     * necessary.
     * 
     * @param originalDate
     *            (i.e. startDate, or endDate)
     * @param up-to-date
     *            date value without time value attached
     * @return a string with update-to-date value with time value attached is
     *         necessary.
     */
    private String getUpdatedString(String originalDate, String date) {
        if (DateUtil.isDateFormat(originalDate)) {
            return date;
        } else if (DateUtil.isDateTimeFormat(originalDate)) {
            return date + originalDate.substring(10);
        } else {
            assert false;
            return null;
        }
    }

}
