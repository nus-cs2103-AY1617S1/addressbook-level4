package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The task provided is invalid";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format! \n"
    		+ "Format: DD MM YYYY or MM DD YYYY or DD MM or MM DD\n"
    		+ "Example: \n"
    		+ "1) 31 Oct 2016 or 31 October 2016 \n"
    		+ "2) Oct 1 2016 or October 31 2016 \n"
    		+ "3) 31 Oct or 31 October \n"
    		+ "4) Oct 31 or October 31";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";

}
