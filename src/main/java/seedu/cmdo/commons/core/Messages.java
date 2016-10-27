package seedu.cmdo.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_DONE_TASKS_LISTED_OVERVIEW = "%1$d done tasks listed!";
    public static final String MESSAGE_EDIT_TASK_IS_DONE_ERROR = "Cannot edit a done task!";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task edited";
	public static final String MESSAGE_INVALID_PRIORITY = "Priority is either high, medium or low. Please try again.";
	public static final String MESSAGE_ENCAPSULATE_DETAIL_WARNING = "Encapsulate your task details in ' '.";
	public static final String MESSAGE_BLANK_DETAIL_WARNING = "Blank task? Did you mean to block out a date? Type help to see usage.";
	public static final String MESSAGE_INVALID_PRIORITY_SPACE = "Did you remember to add a space before the /priority?";
	public static final String MESSAGE_UNDO_LIMIT = "No more actions to undo";
	public static final String MESSAGE_INVALID_TIME_SPACE = "What time do you want to block?";
	public static final String MESSAGE_TIMESLOT_BLOCKED = "Time slot blocked! Here is a list of all your blocked slots.";
	public static final String MESSAGE_CANNOT_DONE = "You can't do a blocked timeslot... Right?";
}
