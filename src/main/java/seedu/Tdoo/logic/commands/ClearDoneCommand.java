package seedu.Tdoo.logic.commands;

/**
 * Clears the TaskList.
 */
public class ClearDoneCommand extends Command {

	public static final String COMMAND_WORD = "clear_done";
	public static final String INVALID_TYPE = "Invalid data type";
	public static final String ALL_MESSAGE_SUCCESS = "All done tasks have been cleared!";
	public static final String TODO_MESSAGE_SUCCESS = "All done todos has been cleared!";
	public static final String EVENT_MESSAGE_SUCCESS = "All done events has been cleared!";
	public static final String DEADLINE_MESSAGE_SUCCESS = "All done deadline has been cleared!";
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clear all done tasks with given data type.\n"
			+ "Parameters: TASK_TYPE\n" + "Example: " + COMMAND_WORD + " all\n" + "Example: " + COMMAND_WORD + " todo\n"
			+ "Example: " + COMMAND_WORD + " event\n" + "Example: " + COMMAND_WORD + " deadline";

	private String dataType;

	public ClearDoneCommand(String args) {
		this.dataType = args.trim();
	}

	// @@author A0144061U
	@Override
	public CommandResult execute() {
		assert model != null;
		switch (dataType) {
		case "all":
			model.removeDoneData();
			return new CommandResult(ALL_MESSAGE_SUCCESS);
		case "todo":
			model.removeDoneTodoData();
			return new CommandResult(TODO_MESSAGE_SUCCESS);
		case "event":
			model.removeDoneEventData();
			return new CommandResult(EVENT_MESSAGE_SUCCESS);
		case "deadline":
			model.removeDoneDeadlineData();
			return new CommandResult(DEADLINE_MESSAGE_SUCCESS);
		}
		return new CommandResult(INVALID_TYPE);
	}
}