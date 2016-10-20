package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.deadline.UniqueDeadlineList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class EditCommand extends Command {
	
	public static final String COMMAND_WORD = "edit";
	
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task in the task manager. "
            + "Parameters: NAME p/PRIORITY" 
            + " Example: " + COMMAND_WORD
            + " 1 Task Name to be Changed d/121016 p/3";
	
	public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task edited: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    public final int targetIndex;
    public final Name name;
    public final Startline startline;
    public final UniqueDeadlineList deadlineSet;
    public final Priority priority;
    public final UniqueTagList tagSet;
    private Task toAdd;
    
    
    public EditCommand(String targetIndex, String startline, String name, Set<String> deadlines, String priority, Set<String> tags) 
    		throws IllegalValueException{
    	final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        final Set<Deadline> deadlineSet = new HashSet<>();
        for (String deadlineDate : deadlines) {
            deadlineSet.add(new Deadline(deadlineDate));
        }
        
    	this.targetIndex = Integer.parseInt(targetIndex);
    	this.name = new Name(name);
    	this.startline = new Startline(startline);
    	this.deadlineSet = new UniqueDeadlineList(deadlineSet);
    	this.priority = new Priority (priority);
    	this.tagSet = new UniqueTagList(tagSet);
    }

	@Override
	public CommandResult execute()  {
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask personToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(personToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        
        toAdd = new Task(this.name, this.startline, this.deadlineSet, this.priority, this.tagSet); //null for now

        assert model != null;
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
 
        
	}
    
    
}
;