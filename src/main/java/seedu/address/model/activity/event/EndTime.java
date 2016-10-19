package seedu.address.model.activity.event;

import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.DateValidation;
import seedu.address.model.activity.DateTime;

public class EndTime extends DateTime {

    public static final String MESSAGE_DUEDATE_CONSTRAINTS = "Event's start time should only contain valid date";
    public static final String MESSAGE_DUEDATE_INVALID = "Event has already started";

    public EndTime(Calendar date) {
        super(date);
    }

    /**
     * Validates given Start Time.
     *
     * @throws IllegalValueException
     *             if given Start time string is invalid.
     */
    public EndTime(String starttime, String date) throws IllegalValueException {
        super(date);
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
        Date taskDate = new Date();
        if (date != "") {
            taskDate = DATE_PARSER.EventDateConvert(date);
        }
        if (date == "") {
            Date startdate = DATE_PARSER.EventDateConvert(starttime);
            taskDate = DATE_PARSER.EndDateTime(startdate);
        }
        if (DateUtil.hasPassed(taskDate)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_INVALID);
        } 

        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
        this.value.setTime(taskDate);
        this.value.set(Calendar.MILLISECOND, 0);
        this.value.set(Calendar.SECOND, 0);
    }

    public String forDisplay() {
        if (this.value == null) {
            return "End:\t\t\t-";
        } else {
            return "End:\t\t\t".concat(this.toString());
        }
    }
}
