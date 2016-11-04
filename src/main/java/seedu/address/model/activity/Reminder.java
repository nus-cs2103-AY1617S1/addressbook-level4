package seedu.address.model.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.DateValidation;

/**
 * Represents a Task's reminder in the Lifekeeper. Guarantees: immutable; is
 * valid as declared in {@link #isValidReminder(String)}
 */
// @@author A0131813R
public class Reminder extends DateTime {

    public static final String MESSAGE_REMINDER_CONSTRAINTS = "Task reminder can only be in date format";
    public static final String MESSAGE_REMINDER_INVALID = "reminder time has passed";
    public String RecurringMessage;

    public Reminder(Calendar date) {
        super(date);
    }

    /**
     * Validates given reminder.
     *
     * @throws IllegalValueException
     *             if given reminder string is invalid.
     */
    public Reminder(String date) throws IllegalValueException {
        super(date);
        String[] recur;
        if (date != "") {
            if (date.contains("every")) {
                this.recurring = true;
                RecurringMessage = date;
                recur = date.split(" ", 2);
                if (recur.length == 1)
                    throw new IllegalValueException(MESSAGE_REMINDER_CONSTRAINTS);
                date = recur[1];
            }
            if (!DateUtil.isValidDate(date)) {
                throw new IllegalValueException(MESSAGE_REMINDER_CONSTRAINTS);
            }
            this.value= DateUtil.setDate(date);
        }
    }

    /**
     * Advances the reminder by a week (7 days) if it is recurring.
     */
    public void resetTime() {
        if (this.recurring) {
            this.value.add(Calendar.DAY_OF_MONTH, 7);
        }
    }

    public String forDisplay() {
        if (this.value == null) {
            return "Reminder:\t-";
        } else {
            if (!recurring) {
                return "Reminder:\t".concat(this.toString());
            }
            else {
                SimpleDateFormat sdfRecurr = new SimpleDateFormat("EEEE, h:mm aa");
                return "Reminder:\t".concat("Every " + sdfRecurr.format(this.value.getTime()));
            }
        }
    }

}