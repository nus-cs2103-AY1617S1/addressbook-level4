package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.DateValidation;

/**
 * Represents a Task's DueDate in the Lifekeeper. Guarantees: immutable; is
 * valid as declared in {@link #isValidDueDate(String)}
 */
public class DueDate {

    public static final String MESSAGE_DUEDATE_CONSTRAINTS = "Task's DueDate should only contain valid date";
    public static final String MESSAGE_DUEDATE_INVALID = "reminder time has passed";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
    public static DateUtil DATE_PARSER;
    public final Calendar value;

    /**
     * Validates given Due Date.
     *
     * @throws IllegalValueException
     *             if given due date string is invalid.
     */
    public DueDate(String date) throws IllegalValueException {
        assert date != null;
        this.value = Calendar.getInstance();
        DATE_PARSER = new DateUtil();
        
        if (!isValidDueDate(date)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }

        if (date != "") {
            //try {
                if (date.equalsIgnoreCase("today")) { // allow user to key in "today" instead of today's date
                    this.value.setTime(Calendar.getInstance().getTime());
                } else if (date.equalsIgnoreCase("tomorrow")) { // allow user to key in "tomorrow" instead of tomorrow's/ date
                    this.value.setTime(Calendar.getInstance().getTime());
                    value.add(Calendar.DAY_OF_MONTH, 1);
                }
                /*if (!DateValidation.aftertoday(date)) {// check if the time is
                                                      // future
                                                      // time
                    System.out.println("IM HERE");
                    throw new IllegalValueException(MESSAGE_DUEDATE_INVALID);
                }
            } catch (ParseException pe) {
                System.out.println("IM ACTUALLY HERE");
                throw new IllegalValueException(MESSAGE_DUEDATE_INVALID);
            }*/

            Date taskDate = DATE_PARSER.parseDate(date);
            
            if (taskDate == null) {
                assert false : "Date should not be null";
            }
            
            this.value.setTime(taskDate);
        }
    }

    /**
     * Returns true if a given string is a valid task reminder.
     */
    public static boolean isValidDueDate(String test) {
        if (DATE_PARSER.validate(test) || test == "")
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        if (this.value == null) {
            return "";
        } else {
            return DATE_FORMAT.format(value.getTime());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDate // instanceof handles nulls
                        && this.value.equals(((DueDate) other).value)); // state
                                                                        // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
