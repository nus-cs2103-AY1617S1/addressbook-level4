package seedu.oneline.model.task;

import seedu.oneline.commons.exceptions.IllegalValueException;

import java.util.Date;
import java.util.List;

import com.joestelmach.natty.*;

public class TaskTime {

    public static final String MESSAGE_TASK_TIME_CONSTRAINTS =
            "Task time should ...";

    public final Date value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public TaskTime(String time) throws IllegalValueException {
        assert time != null;
        time = time.trim();

        Parser parser = new Parser(); // use the natty parser
        List<DateGroup> dates = parser.parse(time);

        if (!isValidTaskTime(dates)) {
            throw new IllegalValueException(MESSAGE_TASK_TIME_CONSTRAINTS);
        }
        
        Date date = dates.get(0).getDates().get(0);
        this.value = date;
    }

    /**
     * Returns if the time supplied is valid by checking the result of parser.parse
     * 
     * @param test the list of dategroups under test
     * @return true if list contains a valid date
     * 
     * Pre-condition: test is the result of applying natty's Parser.parse(time) 
     * where time is the time in question
     */
    public static boolean isValidTaskTime(List<DateGroup> test) {
        return !(test.isEmpty() || test.get(0).getDates().isEmpty());
    }

    /**
     * Returns the default time value
     */
    public static TaskTime getDefault() {
        try {
            return new TaskTime("");
        } catch (IllegalValueException e) {
            assert false; // This function should return a correct value!
        }
        return null;
    }
    
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskTime // instanceof handles nulls
                && this.value.equals(((TaskTime) other).value)); // state check TODO
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    /**
     * Serialize field for storage
     */
    public String serialize() {
        return value;
    }
    
    /**
     * Deserialize from storage
     */
    public static TaskTime deserialize(String args) throws IllegalValueException {
        return new TaskTime(args);
    }
    
}
