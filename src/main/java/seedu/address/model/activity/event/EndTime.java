package seedu.address.model.activity.event;

import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.DateValidation;
import seedu.address.model.activity.DateTime;
//@@author A0131813R
public class EndTime extends DateTime {

    public static final String MESSAGE_ENDTIME_CONSTRAINTS = "Event's start time should only contain valid date";
    public static final String MESSAGE_ENDTIME_INVALID = "Event has already ended";
    public static final String MESSAGE_ENDTIME_NOTVALID = "Event end time is before start time";

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
        super(Calendar.getInstance());
        Date eventDate = null;
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        }
        Date startdate = DATE_PARSER.EventDateConvert(starttime);
        if (!date.equals(""))
            eventDate = DATE_PARSER.EventDateConvert(date);
        else {
            eventDate = DATE_PARSER.EndDateTime(startdate);
        }
            
        if (eventDate.before(startdate))
            throw new IllegalValueException(MESSAGE_ENDTIME_NOTVALID);
        if (DateUtil.hasPassed(eventDate)) {
            throw new IllegalValueException(MESSAGE_ENDTIME_INVALID);
        }
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        }

        this.value.setTime(eventDate);
        this.value.set(Calendar.MILLISECOND, 0);
        this.value.set(Calendar.SECOND, 0);
    }

    public EndTime(String date) throws IllegalValueException {
        super(date);
        Date taskDate;

        if (!date.equals("")) {
            taskDate = DATE_PARSER.EventDateConvert(date);

            if (taskDate.equals(null)) {
                assert false : "Date should not be null";
            } else if (DateUtil.hasPassed(taskDate)) {
                throw new IllegalValueException(MESSAGE_ENDTIME_INVALID);
            }

            if (!isValidDate(date)) {
                throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
            }
            this.value.setTime(taskDate);
            this.value.set(Calendar.MILLISECOND, 0);
            this.value.set(Calendar.SECOND, 0);
        }
    }

	public String forDisplay() {
        if (this.value.equals(null)) {
            return "End:\t\t\t-";
        } else {
            return "End:\t\t\t".concat(this.toString());
        }
    }
}
