package seedu.oneline.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";

    public static final String MESSAGE_EDIT_TAG_ARGS_INVALID_FORMAT = "Arguments not in format #<category> <arguments>";
    public static final String MESSAGE_EDIT_TAG_TAG_NOT_FOUND = "Tag not found";

    public static String getInvalidCommandFormatMessage(String usage) {
        return String.format(MESSAGE_INVALID_COMMAND_FORMAT, usage);
    }
    
}
