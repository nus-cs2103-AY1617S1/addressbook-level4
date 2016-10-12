package seedu.taskmanager.model.task;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's deadline in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {
    
    public static final String MESSAGE_ADDRESS_CONSTRAINTS = "Task deadline must be in ddmmyy format.";
    public static final String DEADLINE_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
        assert deadline != null;
        if (!isValidDeadline(deadline) || (deadline.length() != 6)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        this.value = mutateToDash(deadline);
    }

    /**
     * Returns true if a given string is a valid task email.
     */
    public static boolean isValidDeadline(String test) {
        return test.matches(DEADLINE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    private String mutateToDash(String deadline) throws IllegalValueException {
    	try{
	    	DateFormat input = new SimpleDateFormat("ddMMyy");
	    	DateFormat output = new SimpleDateFormat("dd-MM-yy");
	    	String result = output.format(input.parse(deadline));
	    	return result;
    	}
    	catch (ParseException e){
    		throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
    	}
    }

}