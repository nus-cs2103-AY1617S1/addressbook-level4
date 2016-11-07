package seedu.lifekeeper.model.activity.event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.commons.util.DateUtil;
import seedu.lifekeeper.model.activity.DateTime;

//@@author A0131813R
public class StartTime extends DateTime {

    public static final String MESSAGE_STARTTIME_CONSTRAINTS = "Event's start time should only contain valid date";
    public static final String MESSAGE_STARTTIME_INVALID = "Event has already started";
    public String RecurringMessage;

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
        String[] recur;
        if (date != "") {
            if (date.contains("every")) {
                this.recurring = true;
                RecurringMessage = date;
                recur = date.split(" ", 2);
                if (recur.length == 1)
                    throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
                date = recur[1];
            }
            this.value= DateUtil.setDate(date);
            if(recurring && this.value.before(Calendar.getInstance()))
                this.value.add(Calendar.DAY_OF_MONTH, 7);
        }

    }
    
    public StartTime(Calendar date, boolean isRecurring, String recurringMessage) {
        super(date);
        this.recurring = isRecurring;
        this.RecurringMessage = recurringMessage;
    }
}
