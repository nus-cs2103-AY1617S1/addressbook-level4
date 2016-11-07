
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
import seedu.dailyplanner.model.task.UniqueTaskList.PersonNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the address
 * book. Adds the task back with new updated information
 */
// @@author A0139102U
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
	    + ": Edits the task identified by the index number used in the last task listing.\n"
	    + "Format: edit [INDEX] (must be a positive integer) s/[STARTDATE] [STARTTIME] e/[ENDDATE] [ENDTIME]\n" + "Example: "
	    + COMMAND_WORD + " 2 s/3pm";

    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the daily planner";
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Task: %1$s";

    public final int targetIndex;
    private final Optional<String> taskName;
    private final Optional<DateTime> start;
    private final Optional<DateTime> end;
    private final Optional<Set<String>> tags;
    private Optional<UniqueCategoryList> tagSet;

    public EditCommand(int targetIndex, String taskName, DateTime start, DateTime end, Set<String> tags)
	    throws IllegalValueException {
	this.targetIndex = targetIndex;
	this.taskName = Optional.ofNullable(taskName);
	this.start = Optional.ofNullable(start);
	this.end = Optional.ofNullable(end);
	this.tags = Optional.ofNullable(tags);
	this.tagSet = Optional.empty();

	if (tags.size()!=0) {
	    final Set<Category> tagSet = new HashSet<>();


	    for (String tagName : tags) {
		tagSet.add(new Category(tagName));
	    }
	    this.tagSet = Optional.of(new UniqueCategoryList(tagSet));
	}
    }

    @Override
    public CommandResult execute() {

	UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();

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
	UniqueCategoryList toAddTags = taskToEdit.getTags();
	if (tagSet.isPresent()) {
	    toAddTags = tagSet.get();
	}

	Task toAdd = new Task(toAddName, toAddStart, toAddEnd, taskToEdit.isComplete(), taskToEdit.isPinned(),
		toAddTags);

	try {
	    model.getHistory().stackEditInstruction(taskToEdit, toAdd);
	    model.deletePerson(taskToEdit);
	    model.addPerson(toAdd);
	    model.updatePinBoard();
	} catch (PersonNotFoundException pnfe) {
	    assert false : "The target task cannot be missing";
	} catch (UniqueTaskList.DuplicatePersonException e) {
	    return new CommandResult(MESSAGE_DUPLICATE_PERSON);
	}
	return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, taskToEdit));
    }

}
