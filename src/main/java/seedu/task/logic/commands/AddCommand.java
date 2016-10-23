package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.*;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;


/**
 * Adds a task to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: DESCRIPTION [pr/PRIORITY] [start/TIME] [end/TIME] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Go to Tutorial pr/normal start/12:00 end/14:00 t/tutorial";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    public static final String MESSAGE_ILLEGAL_START_END_TIME = "End is before start.";

    private final Task toAdd;
    private int index;

    private Time start;
    private Time end;

    private boolean canUndo = false;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String description, String priority, String timeStart, String timeEnd, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        start = new Time(timeStart);
        end = new Time(timeEnd);

        this.toAdd = new Task(
                new Description(description),
                new Priority(priority),
                start,
                end,
                new UniqueTagList(tagSet)
        );

    }

    public AddCommand(String description, String priority, Time start, Time end, UniqueTagList tags, boolean completeStatus,int index)
            throws IllegalValueException {

    	this.start = start;
        this.end = end;

    	this.toAdd = new Task(
                new Description(description),
                new Priority(priority),
                this.start,
                this.end,
                tags,
                completeStatus
        );
        this.index = index;
    }

    public AddCommand(Task task, int index)
            throws IllegalValueException {
    	this.toAdd = task;
        this.index = index;
    }

    @Override
    public CommandResult execute() {
        assert model != null;

        if(end.isEndBeforeStart(start))
        	return new CommandResult(MESSAGE_ILLEGAL_START_END_TIME);

        try {
            model.addTask(toAdd);
            undo = true;
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
	 public CommandResult undo(){
    	UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        ReadOnlyTask taskToDelete = lastShownList.get(lastShownList.size() - 1);

        try {
            model.deleteTask(taskToDelete);
            undo = false;
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

	     return new CommandResult("Undo complete!");
	 }

    public CommandResult insert() {
        assert model != null;

        if(end!=null && start!=null && end.isEndBeforeStart(start))
        	return new CommandResult(MESSAGE_ILLEGAL_START_END_TIME);

        try {
			model.insertTask(index, toAdd);
			undo = false;
		} catch (DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE_TASK);
		}
		return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
