package seedu.task.logic.commands;
/*
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;
import seedu.taskcommons.core.EventsCenter;
import seedu.taskcommons.core.Messages;
import seedu.taskcommons.core.UnmodifiableObservableList;
*/
/**
 * Selects a Task/Event identified using it's last displayed index from the Task
 * book.
 * @author Yee Heng
 */

//@@author A0125534L
public abstract class SelectCommand extends Command {

	public int targetIndex;

	//private boolean isTask;


	public static final String COMMAND_WORD = "select";
	

	public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
			+ "Selects an existing task/event from the TaskBook.\n\n"
			+ "Selects a task at the specified INDEX in the most recent task listing.\n"
			+ "Parameters: SELECT_TYPE + INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + "/t"
			+ " 1\n\n" + "Selects a event at the specified INDEX in the most recent event listing.\n"
			+ "Parameters: SELECT_TYPE + INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + "/e"
			+ " 1";
	

    @Override
    public abstract CommandResult execute();
}
		

