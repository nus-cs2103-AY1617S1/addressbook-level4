package seedu.jimi.commons.core;

import seedu.jimi.commons.util.CommandUtil;

/**
 * Container for user visible messages.
 */
public class Messages {
    public static final String MESSAGE_ALL_AVAIL_CMD = 
            "> All available commands: " + CommandUtil.getInstance().commandsToString();
    public static final String MESSAGE_UNKNOWN_COMMAND = 
            "Unknown command - \"%1$s\" \n"
            + MESSAGE_ALL_AVAIL_CMD;
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d task(s) listed!";
    public static final String MESSAGE_INVALID_DATE = 
            "Date and time are invalid!\n" 
            + "> Tip: try avoiding keywords like `on`, `to` and `due` in your dates and times.\n" 
            + "> You might confuse Jimi!";
    public static final String MESSAGE_START_END_CONSTRAINT = "Start date/time needs to be prior to end date/time!";
}
