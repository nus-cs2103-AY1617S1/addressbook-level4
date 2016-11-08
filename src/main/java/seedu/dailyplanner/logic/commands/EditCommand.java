
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
 * Edits a task identified using it's last displayed index from the daily
 * planner. Adds the task back with new updated information
 */

public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    // @@author A0139102U
    public static final String MESSAGE_USAGE = COMMAND_WORD
	    + ": Edits the task identified by the index number shown in current list.\n"
	    + "Format: edit [INDEX] (must be a positive integer)[TASKNAME] s/[STARTDATE] [STARTTIME] e/[ENDDATE] [ENDTIME]\n"
	    + "Example: " + COMMAND_WORD + " 2 s/3pm";

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the daily planner";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    
    public final int targetIndex;
    private final Optional<String> taskName;
    private final Optional<DateTime> start;
    private final Optional<DateTime> end;
    private Optional<UniqueCategoryList> categoriesSet;

    public EditCommand(int targetIndex, String taskName, DateTime start, DateTime end, Set<String> cats)
	    throws IllegalValueException {
	this.targetIndex = targetIndex;
	this.taskName = Optional.ofNullable(taskName);
	this.start = Optional.ofNullable(start);
	this.end = Optional.ofNullable(end);
	this.categoriesSet = Optional.empty();

	if (cats.size() != 0) {
	    final Set<Category> catSet = new HashSet<>();

	    for (String catName : cats) {
		catSet.add(new Category(catName));
	    }
	    this.categoriesSet = Optional.of(new UniqueCategoryList(catSet));
	}
    }

    // @@author A0146749N
    @Override
    public CommandResult execute() {

	UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

	if (lastShownList.size() < targetIndex) {
	    indicateAttemptToExecuteIncorrectCommand();
	    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	}

	ReadOnlyTask originalTask = lastShownList.get(targetIndex - 1);

	// if any of the fields passed in by user are empty, take it from the
	// original task
	String toAddName = (taskName.isPresent()) ? taskName.get() : originalTask.getName();
	DateTime toAddStart = (start.isPresent()) ? start.get() : originalTask.getStart();
	DateTime toAddEnd = (end.isPresent()) ? end.get() : originalTask.getEnd();
	UniqueCategoryList toAddCats = (categoriesSet.isPresent()) ? categoriesSet.get() : originalTask.getCats();
	Task toAdd = new Task(toAddName, toAddStart, toAddEnd, originalTask.isComplete(), originalTask.isPinned(),
		toAddCats);

	try {
	    model.getHistory().stackEditInstruction(originalTask, toAdd);
	    model.deleteTask(originalTask);
	    model.addTask(toAdd);
	    model.updatePinBoard();
	} catch (TaskNotFoundException pnfe) {
	    assert false : "The target task cannot be missing";
	} catch (UniqueTaskList.DuplicateTaskException e) {
	    return new CommandResult(MESSAGE_DUPLICATE_TASK);
	}
	return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, originalTask));
    }
}
