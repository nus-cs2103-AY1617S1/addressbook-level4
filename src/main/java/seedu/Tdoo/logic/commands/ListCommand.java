package seedu.Tdoo.logic.commands;

//@@author A0144061U
/**
 * Lists all tasks in the TodoList to the user.
 */
public class ListCommand extends Command {

	public static final String COMMAND_WORD = "list";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Shows a list of all tasks in the task-list of the given type.\n" + "Parameters: TASK_TYPE\n"
			+ "Example: " + COMMAND_WORD + " all\n" + "Example: " + COMMAND_WORD + " todo\n" + "Example: "
			+ COMMAND_WORD + " event\n" + "Example: " + COMMAND_WORD + " deadline";

	public static final String ALL_MESSAGE_SUCCESS = "Listed all tasks";
	public static final String TODO_MESSAGE_SUCCESS = "Listed todos";
	public static final String EVENT_MESSAGE_SUCCESS = "Listed events";
	public static final String DEADLINE_MESSAGE_SUCCESS = "Listed deadlines";

	private final String dataType;

	public ListCommand(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public CommandResult execute() {
		switch (dataType) {
		case "todo":
			model.updateFilteredTodoListToShowAll();
			return new CommandResult(TODO_MESSAGE_SUCCESS);
		case "event":
			model.updateFilteredEventListToShowAll();
			return new CommandResult(EVENT_MESSAGE_SUCCESS);
		case "deadline":
			model.updateFilteredDeadlineListToShowAll();
			return new CommandResult(DEADLINE_MESSAGE_SUCCESS);
		case "all":
			model.updateFilteredTodoListToShowAll();
			model.updateFilteredEventListToShowAll();
			model.updateFilteredDeadlineListToShowAll();
			return new CommandResult(ALL_MESSAGE_SUCCESS);
		}
		return new CommandResult(MESSAGE_USAGE);
	}
}
