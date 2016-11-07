
package seedu.dailyplanner.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.dailyplanner.commons.core.Messages;
import seedu.dailyplanner.commons.core.UnmodifiableObservableList;
import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.model.category.Category;
import seedu.dailyplanner.model.category.UniqueCategoryList;
import seedu.dailyplanner.model.task.DateTime;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.Task;
import seedu.dailyplanner.model.task.UniqueTaskList;
import seedu.dailyplanner.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the daily
 * planner. Adds the task back with new updated information
 */
// @@author A0139102U
public class EditCommand extends Command {

	public static final String COMMAND_WORD = "edit";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Edits the task identified by the index number used in the last task listing.\n"
			+ "Format: edit [INDEX] (must be a positive integer) s/[STARTDATE] [STARTTIME] e/[ENDDATE] [ENDTIME]\n"
			+ "Example: " + COMMAND_WORD + " 2 s/3pm";

	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the daily planner";
	public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";

	public final int targetIndex;
	private final Optional<String> taskName;
	private final Optional<DateTime> start;
	private final Optional<DateTime> end;
	private final Optional<Set<String>> categories;
	private Optional<UniqueCategoryList> categoriesSet;

	public EditCommand(int targetIndex, String taskName, DateTime start, DateTime end, Set<String> cats)
			throws IllegalValueException {
		this.targetIndex = targetIndex;
		this.taskName = Optional.ofNullable(taskName);
		this.start = Optional.ofNullable(start);
		this.end = Optional.ofNullable(end);
		this.categories = Optional.ofNullable(cats);
		this.categoriesSet = Optional.empty();

		if (cats.size() != 0) {
			final Set<Category> catSet = new HashSet<>();

			for (String catName : cats) {
				catSet.add(new Category(catName));
			}
			this.categoriesSet = Optional.of(new UniqueCategoryList(catSet));
		}
	}

	@Override
	public CommandResult execute() {

		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

		if (lastShownList.size() < targetIndex) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);

		String toAddName = taskToEdit.getName();
		if (taskName.isPresent()) {
			toAddName = taskName.get();
		}
		DateTime toAddStart = taskToEdit.getStart();
		if (start.isPresent()) {
			toAddStart = start.get();
		}
		DateTime toAddEnd = taskToEdit.getEnd();
		if (end.isPresent()) {
			toAddEnd = end.get();
		}
		UniqueCategoryList toAddCats = taskToEdit.getCats();
		if (categoriesSet.isPresent()) {
			toAddCats = categoriesSet.get();
		}

		Task toAdd = new Task(toAddName, toAddStart, toAddEnd, taskToEdit.isComplete(), taskToEdit.isPinned(),
				toAddCats);

		try {
			model.getHistory().stackEditInstruction(taskToEdit, toAdd);
			model.deleteTask(taskToEdit);
			model.addTask(toAdd);
			model.updatePinBoard();
		} catch (TaskNotFoundException pnfe) {
			assert false : "The target task cannot be missing";
		} catch (UniqueTaskList.DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE_TASK);
		}
		return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
	}

}
