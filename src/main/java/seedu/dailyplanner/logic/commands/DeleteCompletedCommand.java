package seedu.dailyplanner.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.dailyplanner.commons.core.Messages;
import seedu.dailyplanner.commons.core.UnmodifiableObservableList;
import seedu.dailyplanner.commons.util.StringUtil;
import seedu.dailyplanner.history.HistoryManager;
import seedu.dailyplanner.logic.parser.nattyParser;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the daily planner.
 */
public class DeleteCompletedCommand extends Command {

	public static final String MESSAGE_USAGE = DeleteCommand.COMMAND_WORD
			+ ": Deletes the task identified by the index number used in the last task listing.\n"
			+ "Format: [INDEX] (must be a positive integer)\n" + "Example: " + DeleteCommand.COMMAND_WORD + " 1";

	public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

	public DeleteCompletedCommand() {
	}

	@Override
	public CommandResult execute() {

		final Set<String> keywordSet = new HashSet<>(Arrays.asList(new String[] { "complete" }));
		model.updateFilteredTaskListByCompletion(keywordSet);
		UnmodifiableObservableList<ReadOnlyTask> completedList = model.getFilteredTaskList();
		
		int size = completedList.size();
		for (int i = 0; i < size; i++) {
			ReadOnlyTask taskToDelete = completedList.get(0);

			try {
				model.getHistory().stackAddInstruction(taskToDelete);
				model.deleteTask(taskToDelete);
				model.updatePinBoard();

			} catch (TaskNotFoundException pnfe) {
				assert false : "The target task cannot be missing";
			}
		}
		
		model.updateFilteredListToShowAll();
		model.setLastShowDate(StringUtil.EMPTY_STRING);
		return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, "all completed"));
	}

}
