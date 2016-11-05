package seedu.address.model.activity.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.DateValidation;
import seedu.address.model.activity.DateTime;
//@@author A0131813R
/**
 * Represents a Task's DueDate in the Lifekeeper. Guarantees: immutable; is
 * valid as declared in {@link #isValidDueDate(String)}
 */
public class DueDate extends DateTime {

    public static final String MESSAGE_DUEDATE_CONSTRAINTS = "Task's DueDate should only contain valid date";
    public static final String MESSAGE_DUEDATE_INVALID = "Deadline is over";
    protected static final SimpleDateFormat DASHBOARD_DATE_FORMATTER = new SimpleDateFormat("EEE, MMM d");

    public DueDate(Calendar date) {
        super(date);
    }
    
    /**
     * Validates given Due Date.
     *
     * @throws IllegalValueException
     *             if given due date string is invalid.
     */
    public DueDate(String date) throws IllegalValueException {
        super(date);
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
        
        if (!date.equals("")) {

            Date taskDate = DATE_PARSER.DueDateConvert(date);

            if (taskDate == null) {
                assert false : "Date should not be null";
            } /*else if (DateUtil.hasPassed(taskDate)) {
                throw new IllegalValueException(MESSAGE_DUEDATE_INVALID);
            }*/
            this.value.setTime(taskDate);
            this.value.set(Calendar.MILLISECOND, 0);
            this.value.set(Calendar.SECOND, 0);
        }
    }
    
    public String forDisplay() {
        if (this.value == null) {
            return "";
        } else {
            return "Due on ".concat(this.toString());
        }
    }
    
    /**
     * Function to output date in concise form for Dashboard.
     * @return
     */
    //@@ author A0125284H
    public String forDashboardDisplay() {
    	if (this.value == null) {
    		return "";
    	} else {
    		return (DASHBOARD_DATE_FORMATTER.format(this.getCalendarValue().getTime()));
    	}
    }
}
