package seedu.address.model.activity.event;

import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.DateValidation;
import seedu.address.model.activity.DateTime;

public class StartTime extends DateTime {


    public static final String MESSAGE_DUEDATE_CONSTRAINTS = "Event's start time should only contain valid date";
    public static final String MESSAGE_DUEDATE_INVALID = "Event has already started";

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


        if (date != "") {
            if (date.contains("today")) { // allow user to key in "today"
                                          // instead of today's date
                date = DateValidation.FixedTimeToday(date);
            } else if (date.contains("tomorrow")) { // allow user to key in
                                                    // "tomorrow" instead of
                                                    // tomorrow's/ date
                date = DateValidation.FixedTimeTomorrow(date);
            }
            Date taskDate = DATE_PARSER.parseEvent(date);

            if (taskDate == null) {
                assert false : "Date should not be null";
            } else if (DateUtil.hasPassed(taskDate)) {
                throw new IllegalValueException(MESSAGE_DUEDATE_INVALID);
            }

            if (!isValidDate(date)) {
                throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
            }
            this.value.setTime(taskDate);
            this.value.set(Calendar.MILLISECOND, 0);
            this.value.set(Calendar.SECOND, 0);
        }
    }
    
    public String forDisplay() {
        if (this.value == null) {
            return "Start:\t\t\t-";
        } else {
            return "Start:\t\t\t".concat(this.toString());
        }
    }
}
