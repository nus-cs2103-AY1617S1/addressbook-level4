package seedu.address.model.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;

public abstract class DateTime {
    public final Calendar value;
    protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
    protected static final DateUtil DATE_PARSER = new DateUtil();

    public DateTime(Calendar date) {
        if (date == null) {
            this.value = null;
        } else {
            this.value = Calendar.getInstance();
            this.value.setTime(date.getTime());
        }
    }

    public DateTime(String date) throws IllegalValueException {
        assert date != null;

        if (date == "") {
            this.value = null;
        } else {
            this.value = Calendar.getInstance();
        }
    }

    /**
     * Returns true if a given string is a valid task reminder.
     */
    protected static boolean isValidDate(String test) {
        if (DATE_PARSER.validate(test) || test == "")
            return true;
        else
            return false;
    }

    public Calendar getCalendarValue() {
        return this.value;
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
        return value != null
                && (other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                        && this.value.getTime().equals(((DateTime) other).value.getTime()))); // state
                                                                         // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
