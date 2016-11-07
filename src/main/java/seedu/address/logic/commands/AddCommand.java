package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.DisplayTaskListEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event/ deadline/ task to be done someday (someday) to the task manager.\n"
            + "Event Same Day Parameters: 'TASK_NAME' from HH:MM to HH:MM on DD-MM-YY\n"
            + "Event Same Day Example: " + COMMAND_WORD
            + " 'dinner with wife' from 19:00 to 21:00 on 25-12-16\n"
            + "Event Different Days Parameters: 'TASK_NAME' from HH:MM DD-MM-YY to HH:MM DD-MM-YY\n"
            + "Event Different Days Example: " + COMMAND_WORD
            + " 'business conference' from 19:00 20-12-16 to 21:00 25-12-16\n"
            + "Deadline Parameters: 'TASK_NAME' by HH:MM DD-MM-YY\n"
            + "Deadline Example: " + COMMAND_WORD
            + " 'lab report' by 16:00 03-03-15\n"
            + "Someday Parameters: 'TASK_NAME'\n"
            + "Someday Example: " + COMMAND_WORD
            + " 'water the plants'";
    
    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    private final Task toAdd;
    
    //@@author A0141019U    
    public AddCommand(String name, String taskType, Optional<LocalDateTime> startDate, Optional<LocalDateTime> endDate, Set<String> tags) 
    		throws IllegalValueException, IllegalArgumentException {
    	final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
    	
    	this.toAdd = new Task(
        		new Name(name),
        		new TaskType(taskType),
        		new Status("pending"), 
        		startDate, 
        		endDate,
        		new UniqueTagList(tagSet)
         );
    }
    
    /**
     * Copy constructor.
     */
    public AddCommand(ReadOnlyTask source) {
        this.toAdd = new Task(source);
    }


    @Override
    public CommandResult execute() {
    	assert model != null;
        try {
        	EventsCenter.getInstance().post(new DisplayTaskListEvent(model.getFilteredTaskList()));
        	model.saveState();
            model.addTask(toAdd);
            raiseJumpToTaskEvent(toAdd);
            model.checkForOverdueTasks();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
        	// If adding was unsuccessful, then the state should not be saved - no change was made.
        	model.undoSaveState();
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }
    
    //@@author A0142184L
	private void raiseJumpToTaskEvent(Task taskAdded) {
		UnmodifiableObservableList<ReadOnlyTask> listAfterAdd = model.getFilteredTaskList();
		int indexToScrollTo = listAfterAdd.indexOf(taskAdded);
		EventsCenter.getInstance().post(new JumpToListRequestEvent(indexToScrollTo));
	}

}
