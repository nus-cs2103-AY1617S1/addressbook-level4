package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.HashSet;
import java.util.Set;

//@@author A0141812R
public class EditCommand extends Command {

	public static final String COMMAND_WORD = "edit";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task in the task manager. "
            + "Parameters: NAME"
            + " Example: " + COMMAND_WORD
            + " 1 Task Name to be Changed d/121016";

	public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task edited: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    public final int targetIndex;
    public Name name;
    public Startline startline;
    public Deadline deadline;
    public Priority priority;
    public final UniqueTagList tagSet;
    private Task toAdd;

    //@@author A0141812R
    public EditCommand(String targetIndex, String name, String startline, String deadline, String priority, Set<String> tags)
    		throws IllegalValueException{
    	final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
    	this.targetIndex = Integer.parseInt(targetIndex);
    	this.name = new Name(name);
    	this.startline = new Startline(startline);
    	this.deadline = new Deadline(deadline);
    	this.priority = new Priority (priority);
    	this.tagSet = new UniqueTagList(tagSet);
    }

	@Override
	public CommandResult execute()  {
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);


        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        
        if (this.name.toString() == "0"){
        	String nameEdit = taskToDelete.getName().toString();
        	try {
        		this.name = new Name(nameEdit);
		    } catch (IllegalValueException e1) {
		    	e1.printStackTrace();
		    }
        }

        if (this.startline.toString() == ""){
        	String startlineEdit = taskToDelete.getStartline().toString();
        	try {
        		this.startline = new Startline(startlineEdit);
		    } catch (IllegalValueException e1) {
		    	e1.printStackTrace();
		    }
        }

        if (this.deadline.toString() == ""){

        	String deadlineEdit = taskToDelete.getDeadline().toString();
        	try {
        		this.deadline = new Deadline(deadlineEdit);
        	} catch (IllegalValueException e1) {
        		e1.printStackTrace();
        	}
        }

        if (this.priority.toString() == "0"){
        	String priorityEdit = taskToDelete.getPriority().toString();

        	try {
        		this.priority = new Priority(priorityEdit);
        	} catch (IllegalValueException e1) {
        		e1.printStackTrace();
        	}
        }

        toAdd = new Task(this.name, this.startline, this.deadline, this.priority, this.tagSet); //null for now

        assert model != null;
        try {
            model.addTask(toAdd);
            String point = String.format(MESSAGE_EDIT_TASK_SUCCESS, toAdd);
            model.currentState(point);
	    EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getFilteredTaskList().size() - 1));
            return new CommandResult(point);
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }


	}


}
