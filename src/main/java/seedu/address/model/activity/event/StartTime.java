package seedu.address.model.activity.event;

import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.DateValidation;
import seedu.address.model.activity.DateTime;
//@@author A0131813R
public class StartTime extends DateTime {


    public static final String MESSAGE_STARTTIME_CONSTRAINTS = "Event's start time should only contain valid date";
    public static final String MESSAGE_STARTTIME_INVALID = "Event has already started";

    public StartTime(Calendar date) {
        super(date);
    }
    
    /**
     * Validates given Start Time.
     *
     * @throws IllegalValueException
     *             if given Start time string is invalid.
     */
    public StartTime(String date) throws IllegalValueException {
        super(date);
        Date taskDate;

        if (!date.equals("")) {
            taskDate = DATE_PARSER.EventDateConvert(date);

            if (taskDate == null) {
                assert false : "Date should not be null";
            } else if (DateUtil.hasPassed(taskDate)) {
                throw new IllegalValueException(MESSAGE_STARTTIME_INVALID);
            }

            if (!isValidDate(date)) {
                throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
            }
            this.value.setTime(taskDate);
            this.value.set(Calendar.MILLISECOND, 0);
            this.value.set(Calendar.SECOND, 0);
        }
    }
    
    public String forDisplay() {
        if (this.value == null) {
            return "Start:\t\t-";
        } else {
            return "Start:\t\t".concat(this.toString());
        }
    }
}
