package seedu.toDoList.model.task;

import java.time.LocalDate;
import java.util.ArrayList;

import seedu.toDoList.commons.exceptions.IllegalValueException;
import seedu.toDoList.commons.util.DateUtil;

//@@author A0146123R
/**
 * Represents a Task's deadline. Empty String "" means no deadline (floating
 * task) Guarantees: is valid as declared in
 * {@link #isValidDeadlineFormat(String)}
 */
public class Deadline implements Date {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Deadline must be a valid date";

    private String date;

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException
     *             if given deadline string is invalid.
     */
    public Deadline(String date) throws IllegalValueException {
        assert date != null;
        this.date = getValidDate(date);
        assert isValidDeadlineFormat(this.date);
    }

    /**
     * Get a date that is valid and is in valid deadline format.
     */
    public static String getValidDate(String date) throws IllegalValueException {
        if (DateUtil.isEmptyDate(date)) {
            return date;
        }
        try {
            return DateUtil.parseDate(date);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is in a valid deadline format.
     */
    public static boolean isValidDeadlineFormat(String test) {
        return DateUtil.isEmptyDate(test) || DateUtil.isValidDateFormat(test);
    }

    @Override
    public String getValue() {
        return date;
    }

    @Override
    public String toString() {
        return date;
    }

    // @@author A0142325R

    /**
     * Override the updateRecurringDate method in Date interface. Change the
     * current date to "numOfDays" after the current value. Update this.date
     * value
     */
    @Override
    public void updateRecurringDate(long numOfDays) {
        LocalDate deadlineDate = getLocalDate().get(0);
        String upToDateDeadline = DateUtil.getFormattedDateString(deadlineDate.plusDays(numOfDays));
        updateDate(upToDateDeadline);

    }

    /**
     * Override getLocalDate in interface Date. Return date in LocalDate type as
     * an arrayList.
     */
    @Override
    public ArrayList<LocalDate> getLocalDate() {
        assert date != null;
        ArrayList<LocalDate> dateArray = new ArrayList<LocalDate>();
        dateArray.add(LocalDate.parse(date.substring(0, 10), DateUtil.getGermanFormatter()));
        return dateArray;
    }

    /**
     * Override the updateDate in interface Date. Update the date attribute of
     * this Deadline object.
     */
    @Override
    public void updateDate(String... deadline) {
        assert deadline.length == 1;
        assert getUpdateDeadline(deadline) != null;
        this.date = getUpdateDeadline(deadline);
    }

    /**
     * get updated deadline string with time value attached from the input
     * string
     * 
     * @param deadline
     *            with the up-to-date date value excluding time value
     * @return updated complete deadline string with time value attached if
     *         possible
     */
    private String getUpdateDeadline(String... deadline) {
        if (DateUtil.isDateFormat(date)) {
            return deadline[0];
        } else if (DateUtil.isDateTimeFormat(date)) {
            return deadline[0] + date.substring(10);
        } else {
            assert false;
            return null;
        }
    }

    // @@author A0146123R
    @Override
    public boolean equals(Object other) {
        return other == this || ( other instanceof Deadline && isSameDeadline((Deadline) other) );
    }

    private boolean isSameDeadline(Deadline other) {
        return date.equals(other.date);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}