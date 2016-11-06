package seedu.menion.commons.core;

import seedu.menion.model.activity.Activity;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX = "The activity index provided is invalid";
    public static final String MESSAGE_ACTIVITIES_LISTED_OVERVIEW = "%1$d activities listed!";
    public static final String MESSAGE_FILE = "Email.txt"; // Name of file to store User email.
    public static final String MESSAGE_INVALID_TYPE = "Activity type invalid! It should only be: " + Activity.FLOATING_TASK_TYPE + 
                    ", " + Activity.TASK_TYPE + ", " + Activity.EVENT_TYPE;
}
