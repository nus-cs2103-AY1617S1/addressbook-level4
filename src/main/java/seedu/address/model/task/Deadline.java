package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;

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
    public void updateDate(String deadline) {
        this.date = deadline;
    }
    
    //@@author A0146123R
    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Deadline && isSameDeadline((Deadline) other));
    }

    private boolean isSameDeadline(Deadline other) {
        return date.equals(other.date);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}