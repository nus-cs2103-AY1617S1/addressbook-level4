package seedu.unburden.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_INVALID_DATE = "Invalid Date Entered";
    public static final String MESSAGE_INVALID_START_TIME = "Invalid Start Time Entered";
    public static final String MESSAGE_INVALID_END_TIME = "Invalid End Time Entered";
    public static final String MESSAGE_STARTTIME_AFTER_ENDTIME = "Start Time cannot be after End Time";
    public static final String MESSAGE_CANNOT_ADD_ENDTIME_WITH_NO_DATE = "Sorry! You cannot add end time to a task with no date!";
    public static final String MESSAGE_CANNOT_ADD_STARTTIME_WITH_NO_ENDTIME = "Sorry! You cannot add start time to a task with no end time!";
    public static final String MESSAGE_CANNOT_REMOVE_ENDTIME_WHEN_THERE_IS_STARTTIME = "Sorry! You cannot remove end time from a task with start time!";
    public static final String MESSAGE_CANNOT_REMOVE_DATE_WHEN_THERE_IS_STARTTIME_AND_ENDTIME = "Sorry! You cannot remove date from a task with satrt time and end time!";
    
}
