package seedu.simply.logic.commands;

import java.util.ArrayList;

import seedu.simply.commons.core.EventsCenter;
import seedu.simply.commons.core.Messages;
import seedu.simply.commons.core.UnmodifiableObservableList;
import seedu.simply.commons.events.ui.JumpToListRequestEvent;
import seedu.simply.model.task.ReadOnlyTask;

/**
 * Selects a task identified using it's last displayed index from the address book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer with either E, D or T in front.)\n"
            + "Example: " + COMMAND_WORD + " E1\n"
            + "Example: " + COMMAND_WORD + " D1\n"
            + "Example: " + COMMAND_WORD + " T1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    public final Integer targetIndex;
    public final String Args;
    public final char category;


    public SelectCommand(Integer index, String args, char category) {
    	this.targetIndex = index;
        this.Args = args;
        this.category = category;
    }

    @Override
    public CommandResult execute() {      
    	
    	UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownDeadlineList = model.getFilteredDeadlineList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownTodoList = model.getFilteredTodoList();
        if (category == 'E') {
        	if (lastShownEventList.size() < targetIndex) {
        		indicateAttemptToExecuteIncorrectCommand();
        		return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        	}
        	//model.addToUndoStack();
        	EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1, category));
        	return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, lastShownEventList.get(targetIndex-1).toString()));            
        }
        else if (category == 'D') {
        	if (lastShownDeadlineList.size() < targetIndex) {
        		indicateAttemptToExecuteIncorrectCommand();
        		return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        	}
        	//model.addToUndoStack();
        	EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1, category));
        	return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, lastShownDeadlineList.get(targetIndex-1).toString()));
        }
        else {
            if (lastShownTodoList.size() < targetIndex) {
           		indicateAttemptToExecuteIncorrectCommand();
           		return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
           	}
            //model.addToUndoStack();
           	EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1, category));
           	return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, lastShownTodoList.get(targetIndex-1).toString()));    
        }
        
    }

}