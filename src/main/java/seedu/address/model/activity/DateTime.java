package seedu.address.model.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;

//@@author A0125680H
public abstract class DateTime {
    public final Calendar value;
    protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
    protected static final SimpleDateFormat SAVE_DATE_FORMAT = new SimpleDateFormat("d-MM-yyyy h:mm a");
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

        if (date.equals("")) {
            this.value = null;
        } else {
            this.value = Calendar.getInstance();
            this.value.set(Calendar.MILLISECOND, 0);
            this.value.set(Calendar.SECOND, 0);
        }
    }

    /**
     * Returns true if a given string is a valid task reminder.
     */
    protected static boolean isValidDate(String test) {
        if (DATE_PARSER.validate(test) || test.equals("") ||test.contains("today") || test.contains("tomorrow"))
            return true;
        else
            return false;
    }

    public Calendar getCalendarValue() {
        return this.value;
    }
    
    public abstract String forDisplay();

    @Override
    public String toString() {
        if (this.value == null) {
            return "";
        } else {
            return DATE_FORMAT.format(value.getTime());
        }
    }


    public String toSave() {
        if (this.value == null) {
            return new String("");
        } else {
            return SAVE_DATE_FORMAT.format(value.getTime());
        }
    }
    
    @Override
    public boolean equals(Object other) {
        if (value == null || ((DateTime) other).value == null) {
            return !((value == null) ^ ((DateTime) other).value == null);
        } else {
            return other == this // short circuit if same object
                    || (other instanceof DateTime // instanceof handles nulls
                            && this.value.equals(((DateTime) other).value)); // state
                                                                             // check
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
