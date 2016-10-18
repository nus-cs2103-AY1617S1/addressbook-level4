package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

import java.time.LocalTime;

/**
 * Represents a task's start time in Simply.
 * Guarantees: immutable; is valid as declared in {@link #isValidStart(String)}
 */
public class Start {

    public static final String MESSAGE_START_CONSTRAINTS = "Task start time can be entered in 24hour or 12hour format.";
    public static final String START_VALIDATION_REGEX = "([01]\\d{1}[0-5]\\d{1})|" +
    													"([2][0-3][0-5]\\d{1})|" +
    													"([1-9](?:pm|am|PM|AM))|" + 
    													"(1[0-2](?:pm|am|PM|AM))|" +
    													"([1-9]\\.[0-5]{1}\\d{1}(?:pm|am))|" +
    													"(1[0-2]\\.[0-5]{1}\\d{1}(?:pm|am))|" +
    													"(no start)";

    public final String value;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time string is invalid.
     */
    public Start(String start) throws IllegalValueException {
        //assert start != null;
    	if (start == null) 
    		start = "default"; 
        start = start.trim();
        if (!isValidStart(start)) {
            throw new IllegalValueException(MESSAGE_START_CONSTRAINTS);
        }
        if (start == "default")
    		this.value = LocalTime.now().getHour() +""+ LocalTime.now().getMinute();
        else
        	this.value = start;
    }

    /**
     * Returns if a given string is a valid task start time.
     */
    public static boolean isValidStart(String test) {
    	if (test.matches(START_VALIDATION_REGEX) || test == "default")
    		return true;
    	else
    		return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Start // instanceof handles nulls
                && this.value.equals(((Start) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
