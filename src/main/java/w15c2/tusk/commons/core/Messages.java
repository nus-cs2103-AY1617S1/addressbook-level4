package w15c2.tusk.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format! \n"
    		+ "Date format: DD MM YYYY / MM DD YYYY / DD MM / MM DD\n"
    		+ "Time format: HH.MM / HH:MM\n"
    		+ "Example: \n"
    		+ "1) 31 Oct, 31 October 2016 \n"
    		+ "2) Today 5:25pm, Tomorrow, Next Tuesday 1.20am";
}
