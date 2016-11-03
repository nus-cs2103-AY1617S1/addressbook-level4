package seedu.dailyplanner.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.dailyplanner.commons.core.Messages;
import seedu.dailyplanner.commons.core.UnmodifiableObservableList;
import seedu.dailyplanner.history.HistoryManager;
import seedu.dailyplanner.logic.parser.nattyParser;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.UniqueTaskList.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address
 * book.
 */
public class DeleteCompletedCommand extends Command {

	public static final String MESSAGE_USAGE = DeleteCommand.COMMAND_WORD
			+ ": Deletes the task identified by the index number used in the last person listing.\n"
			+ "Parameters: INDEX (must be a positive integer)\n" + "Example: " + DeleteCommand.COMMAND_WORD + " 1";

	public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Task: %1$s";

	public DeleteCompletedCommand() {
	}

	@Override
	public CommandResult execute() {

		final Set<String> keywordSet = new HashSet<>(Arrays.asList(new String[] { "complete" }));
		model.updateFilteredPersonListByCompletion(keywordSet);
		UnmodifiableObservableList<ReadOnlyTask> completedList = model.getFilteredPersonList();
		
		int size = completedList.size();
		for (int i = 0; i < size; i++) {
			ReadOnlyTask taskToDelete = completedList.get(0);

			try {
				model.getHistory().stackAddInstruction(taskToDelete);
				model.deletePerson(taskToDelete);

			} catch (PersonNotFoundException pnfe) {
				assert false : "The target task cannot be missing";
			}
		}
		
		model.updateFilteredListToShowAll();
		return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, "all completed"));
	}

}
