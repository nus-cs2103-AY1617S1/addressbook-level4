package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;
import com.joestelmach.natty.*;
import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 * Represents a Task's due date in SuperbTodo.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DueDate {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task's due date should only contain numbers";

    public final String value;

    /**
     * Validates given date number.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public DueDate(String date) throws IllegalValueException {
        assert date != null;
        date = date.trim();
        Parser parser = new Parser();
    	List<DateGroup> dateParser = parser.parse(date);
        if (!isValidDate(dateParser)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        String dateString = dateParser.get(0).getDates().toString();
        this.value = dateString.substring(1, dateString.length()-1);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(List<DateGroup> test) {
    	if (!test.isEmpty()) {
    		return true;
    	} else {
    		return false;
    	}
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDate // instanceof handles nulls
                && this.value.equals(((DueDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
