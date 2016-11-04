package seedu.address.model.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;

//@@author A0125680H
public abstract class DateTime implements Comparable<DateTime>{
    public Calendar value;
    protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
    protected static final SimpleDateFormat SAVE_DATE_FORMAT = new SimpleDateFormat("d-MM-yyyy h:mm aa");
    protected static final DateUtil DATE_PARSER = new DateUtil();
    public boolean recurring;

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
    
    //@@author A0125680H
    /**
     * Returns true if the stored time is before the current time.
     */
    public boolean isBeforeNow() {
        Date now = Calendar.getInstance().getTime();
        return this.value.getTime().before(now);
    }
    
    //@@author A0125680H
    /**
     * Returns true if the stored time is after the current time.
     */
    public boolean isAfterNow() {
        Date now = Calendar.getInstance().getTime();
        return this.value.getTime().after(now);
    }
    
    //@@author A0125680H
    /**
     * Returns true if the stored time is before the date in the specified DateTime object.
     */
    public boolean isBefore(DateTime other) {
        Date otherDate = other.getCalendarValue().getTime();
        return this.value.getTime().before(otherDate);
    }
    
    //@@author A0125680H
    /**
     * Returns true if the stored time is after the date in the specified DateTime object.
     */
    public boolean isAfter(DateTime other) {
        Date otherDate = Calendar.getInstance().getTime();
        return this.value.getTime().after(otherDate);
    }
    
    /**
     * Comparator for DateTime. Earlier dates are ordered before later dates.
     */
    @Override
    public int compareTo(DateTime other) {
        return this.value.getTime().compareTo(other.value.getTime());
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
