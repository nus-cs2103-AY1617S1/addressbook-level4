package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;
import com.joestelmach.natty.*;
import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 * Represents a Task's date and time in SuperbTodo.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DateTime {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task's dateTime should be a valid number representing date and time";

    public final String date_value;
    public final String time_value;

    /**
     * Validates given date time.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public DateTime(String date) throws IllegalValueException {
        assert date != null;
        date = date.trim();
        Parser parser = new Parser();
    	List<DateGroup> dateParser = parser.parse(date);
        if (!isValidDate(dateParser)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.date_value = formatDate(dateParser.get(0).getDates().toString());
        if (date.toLowerCase().equals("today")) {
        	this.time_value = "23:59 Hrs";
        } else {
        	this.time_value = formatTime(dateParser.get(0).getDates().toString());
        }
    }

	private String formatDate(String dateString) {
		String[] dateComponent = dateString.substring(1, dateString.length() - 1).split(" ");
        return dateComponent[2] + " " + dateComponent[1] + " " + dateComponent[5];
	}
	
	private String formatTime(String dateString) {
		String[] dateComponent = dateString.substring(1, dateString.length() - 1).split(" ");
        return dateComponent[3].substring(0, dateComponent[3].length() - 3) + " Hrs";
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
        return date_value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.date_value.equals(((DateTime) other).date_value)); // state check
    }

    @Override
    public int hashCode() {
        return date_value.hashCode();
    }

}
