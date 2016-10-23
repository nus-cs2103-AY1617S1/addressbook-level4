package seedu.ggist.model.task;


import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import java.text.SimpleDateFormat;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

/**
 * Represents a Task's taskDate in the GGist.
 * Guarantees: immutable; is valid as declared in right format
 */
public class TaskDate {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date is unreadable and should contains only spaces, letters or digits. \n" 
            + "Example: 22 Nov or Decemeber 22 or tomorrow";
    public static final String DATE_VALIDATION_REGEX = "\\w{3}, \\d{2} \\w{3} \\d{2}";

    public String value;
    public String testValue;

    /**
     * Validates given taskDate.
     *
     * @throws IllegalValueException if given taskDate string is invalid.
     */
    public TaskDate(String date) throws IllegalValueException {
        assert date != null;
        date = date.trim();
        if(date.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) || date.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED)) {
            this.value = date;
            this.testValue = date;
        } else if (!isValidDateFormat(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        } else {
        	this.value = date;
        	String[] tempList = date.split("\\s+");
        	this.testValue = tempList[1] + " " + tempList[2];
        }
    }
    
    public void editDate(String newDate) throws IllegalValueException {
        assert newDate != null;
        newDate = newDate.trim();
        if(!isValidDateFormat(newDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = newDate;
        String[] tempList = newDate.split("\\s+");
        this.testValue = tempList[1] + " " + tempList[2];
    }
    /**
     * Returns if a given string is a valid taskDate.
     */
    public static boolean isValidDateFormat(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
    public String getTestValue() {
    	return testValue;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instance of handles nulls
                && this.value.equals(((TaskDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}