package seedu.savvytasker.model.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

//@@author A0139915W
/**
 * A read-only immutable interface for a Task in the TaskList.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {
    
    int getId();
    int getGroupId();
    boolean isMarked();
    boolean isArchived();
    String getTaskName();
    Date getStartDateTime();
    Date getEndDateTime();
    String getLocation();
    PriorityLevel getPriority();
    RecurrenceType getRecurringType();
    int getNumberOfRecurrence();
    String getCategory();
    String getDescription();

    /**
     * Returns true if both tasks have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return getId() == other.getId();
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Id: ")
                .append(getId())
                .append(" Task Name: ")
                .append(getTaskName())
                .append(" Archived: ")
                .append(isArchived());
        if (getStartDateTime() != null) {
            builder.append(" Start: ")
                    .append(getStartDateTime());
        }
        if (getEndDateTime() != null) {
            builder.append(" End: ")
                    .append(getEndDateTime());
        }
        if (getLocation() != null && !getLocation().isEmpty()) {
            builder.append(" Location: ")
                    .append(getLocation());
        }
        builder.append(" Priority: ")
                .append(getPriority());
        if (getCategory() != null && !getCategory().isEmpty()) {
            builder.append(" Category: ")
                    .append(getCategory());
        }
        if (getDescription() != null && !getDescription().isEmpty()) {
            builder.append(" Description: ")
                    .append(getDescription());
        }
        builder.append(" Archived: ")
        .append(isArchived());
        return builder.toString();
    }


    /**
     * Formats the task as text, showing all task details, formatted for the UI.
     */
    default String getTextForUi() {
        final StringBuilder builder = new StringBuilder();
        if (getStartDateTime() != null || getEndDateTime() != null) {
            builder.append(generateDateTime(getStartDateTime(), getEndDateTime()))
                    .append("\n");
        }
        if (getLocation() != null && !getLocation().isEmpty()) {
            builder.append(" Location: ")
                    .append(getLocation())
                    .append("\n");
        }
        if (getCategory() != null && !getCategory().isEmpty()) {
            builder.append(" Category: ")
                    .append(getCategory())
                    .append("\n");
        }
        if (getDescription() != null && !getDescription().isEmpty()) {
            builder.append(" Description: ")
                    .append(getDescription())
                    .append("\n");
        }
        return builder.toString();
    }
    //@@author

    //@@author A0138431L
    static final String EMPTY_FIELD = " ";

    static String DATE_PATTERN = "dd MMM yy";
 	static String TIME_PATTERN = "hh:mm a";
 	
 	// String format for deadline tasks dateTime format
 	static String DEADLINE_FORMAT = "by %1$s, %2$s";
 	
 	// String format for event tasks dateTime format
 	static String EVENT_DIFF_START_END_DATE_FORMAT = "%1$s, %2$s to %3$s, %4$s";
 	static String EVENT_SAME_START_END_DATE_FORMAT = "%1$s, %2$s to %3$s";

    static Date lastDayOfSelectedWeek = new Date();
        
    /**
	 * Generates the DateTime Format for all tasks with time.
	 * 
	 * @param task the task to have its DateTime Format generated
	 * 
	 * @return DateTime Format (e.g. (31 Oct 16, 10:00PM)
	 **/
	default String generateDateTime(Date start, Date end) {
		String dateTimeFormat;
		//Floating Task
		if(start == null && end == null) {
			dateTimeFormat = EMPTY_FIELD;
		} 
		//Deadline Task
		else if(start == null && end != null) {
			dateTimeFormat = generateDeadlineDateTime(end);
		//Event Task
		}else {
			dateTimeFormat = generateEventDateTime(start, end);
		}		
		
		return dateTimeFormat;
		
	}
	
	/**
	 * Generates the dateTimeFormat for deadline tasks
	 * 
	 * @param task the task to have its dateTimeFormat generated
	 * 
	 * @return dateTimeFormat (e.g. 30 Oct 16, 10:00PM)
	 */
	default String generateDeadlineDateTime(Date end) {
		
		String dateTimeFormat;
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);
		SimpleDateFormat timeFormatter = new SimpleDateFormat(TIME_PATTERN);
		
		String taskEndDateFormat = dateFormatter.format(end.getTime());
		String taskEndTimeFormat = timeFormatter.format(end.getTime());
		
		dateTimeFormat = String.format(DEADLINE_FORMAT, taskEndDateFormat, taskEndTimeFormat);
				
		return dateTimeFormat;
		
	}
	
	/**
	 * Generates the dateTimeFormat for ranged tasks
	 * 
	 * @param task the task to have its dateTimeFormat generated
	 * 
	 * @return dateTimeFormat (e.g. 30 Oct 16, 8:00AM to 9:00PM)
	 */
	default String generateEventDateTime(Date start, Date end) {
		
		String dateTimeFormat;
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);
		SimpleDateFormat timeFormatter = new SimpleDateFormat(TIME_PATTERN);
		
		String taskStartDateFormat = dateFormatter.format(start.getTime());
		String taskStartTimeFormat = timeFormatter.format(start.getTime());
		
		String taskEndDateFormat = dateFormatter.format(end.getTime());
		String taskEndTimeFormat = timeFormatter.format(end.getTime());
		
		if(DateUtils.isSameDay(start, end) == false) {
			
			dateTimeFormat = String.format(EVENT_DIFF_START_END_DATE_FORMAT, taskStartDateFormat, taskStartTimeFormat, taskEndDateFormat, taskEndTimeFormat);
		
		} else {

			dateTimeFormat = String.format(EVENT_SAME_START_END_DATE_FORMAT, taskEndDateFormat, taskStartTimeFormat, taskEndTimeFormat);
				
		}

		return dateTimeFormat;
		
	}
}
//@@author
