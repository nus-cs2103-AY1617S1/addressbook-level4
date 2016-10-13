package seedu.address.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateValidation;

/**
 * Represents a Task's reminder in the Lifekeeper. Guarantees: immutable; is
 * valid as declared in {@link #isValidReminder(String)}
 */
public class Reminder {

    public static final String MESSAGE_REMINDER_CONSTRAINTS = "Task reminder can only be in date format";
    public static final String MESSAGE_REMINDER_INVALID = "reminder time has passed";
    public final String value;

    /**
     * Validates given reminder.
     *
     * @throws IllegalValueException
     *             if given reminder string is invalid.
     */
    public Reminder(String date) throws IllegalValueException {
        assert date != null;
        String time;
        String[] parts;
        try{if (date.contains("today")){
            parts = date.split(" ");
            time = parts[1];
            date = DateValidation.TodayDate();
            date = date + " " + time;
            }
        else if (date.contains("tomorrow")){
            parts = date.split(" ");
            time = parts[1];
            date = DateValidation.TodayDate();
            date = date + " " + time;
            date = DateValidation.TomorrowDate();
        }        if (!isValidReminder(date)||!DateValidation.aftertoday(date)) {
            throw new IllegalValueException(MESSAGE_REMINDER_CONSTRAINTS);
        }
        }catch (ParseException pe){
            throw new IllegalValueException(MESSAGE_REMINDER_INVALID);
        }        

        this.value = date;
    }
        

    /**
     * Returns true if a given string is a valid task reminder.
     */
    public static boolean isValidReminder(String test) {
<<<<<<< HEAD
        if ((DateValidation.validate(test)))
            return true;
        else
            return false;
=======
        return test.matches(REMINDER_VALIDATION_REGEX)|| test == "";
>>>>>>> origin/Floating_Task
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Reminder // instanceof handles nulls
                        && this.value.equals(((Reminder) other).value)); // state
                                                                         // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}