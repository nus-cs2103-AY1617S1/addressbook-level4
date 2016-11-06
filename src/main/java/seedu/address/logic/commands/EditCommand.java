package seedu.address.logic.commands;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.DisplayTaskListEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task identified using it's last displayed index from the task manager.
 */
//@@author A0139339W
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (positive integer) ['NEW_NAME'] [from TIME DATE] [by TIME DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 'chill for the day' from 12am today by 11pm today";
 
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "Edit will result in duplicate tasks in task manager";   
    
    //@@author A0143756Y
    public static final String MESSAGE_START_DATE_TIME_AFTER_END_DATE_TIME = 
    		"Start of event is scheduled after end of event. Please re-enter correct start and end dates/times.\n";
    public static final String MESSAGE_START_DATE_TIME_EQUALS_END_DATE_TIME =
    		"Start of event equals end of event. Please re-enter correct start and end dates/times.\n";
    //@@author A0139339W

    public final int targetIndex;
    private final Optional<Name> newName;
    private final Optional<LocalDateTime> newStartDateTime;
    private final Optional<LocalDateTime> newEndDateTime;
    private final boolean isRemoveStartDateTime;
    private final boolean isRemoveEndDateTime;

    /**
     * For editing name of task
     * @throws IllegalValueException 
     */
    public EditCommand(int targetIndex, Optional<String> name, 
    		Optional<LocalDateTime> newStartDate, Optional<LocalDateTime> newEndDate,
    		boolean isRemoveStartDateTime, boolean isRemoveEndDateTime)
    		throws IllegalValueException {
        this.targetIndex = targetIndex;
        if(name.isPresent()) {
        	newName = Optional.of(new Name(name.get()));
        } else {
        	newName = Optional.empty();
        }
        this.newStartDateTime = newStartDate;
        this.newEndDateTime = newEndDate;
        this.isRemoveStartDateTime = isRemoveStartDateTime;
        this.isRemoveEndDateTime = isRemoveEndDateTime;
    }
    
    @Override
    public CommandResult execute() {
    	
    	EventsCenter.getInstance().post(new DisplayTaskListEvent(model.getFilteredTaskList()));

        model.saveState();
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        UnmodifiableObservableList<ReadOnlyTask> fullList = model.getUnfilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            model.undoSaveState();
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);

        try {
            Task postEdit = new Task(taskToEdit);
            int index = fullList.indexOf(postEdit);
            if(newName.isPresent()) {
        	    postEdit.setName(newName.get());
            }
            
            if(newStartDateTime.isPresent() && !newEndDateTime.isPresent()){
            	LocalDateTime startDateTime = newStartDateTime.get();
            	
            	if(taskToEdit.getEndDate().isPresent()){
            		LocalDateTime endDateTime = taskToEdit.getEndDate().get();
            		
                	if(startDateTime.isAfter(endDateTime)){
                    	return new CommandResult(MESSAGE_START_DATE_TIME_AFTER_END_DATE_TIME);
                	}  
            	}
            }
            
            if(!newStartDateTime.isPresent() && newEndDateTime.isPresent()){
            	LocalDateTime endDateTime = newEndDateTime.get();
            	
            	if(taskToEdit.getStartDate().isPresent()){
            		LocalDateTime startDateTime = taskToEdit.getStartDate().get();
            		
                	if(!endDateTime.isAfter(startDateTime)){
                		return new CommandResult(MESSAGE_START_DATE_TIME_AFTER_END_DATE_TIME);
                	}      
            	}
            }
            
            if(newEndDateTime.isPresent()) {
            	postEdit.setEndDate(newEndDateTime.get());
            }
            
            if(newStartDateTime.isPresent()) {
                postEdit.setStartDate(newStartDateTime.get());
            }
            
            if(isRemoveStartDateTime) {
            	postEdit.removeStartDate();
            }
            
            if(isRemoveEndDateTime) {
            	postEdit.removeEndDate();
            }
        	
            if(lastShownList.contains(postEdit)) {
                model.undoSaveState();
                return new CommandResult(MESSAGE_DUPLICATE_TASK);
            }
        	
            model.editTask(index, postEdit);
            
           
            raiseJumpToTaskEvent(postEdit);

            
        } catch (UnsupportedOperationException uoe) {
            model.undoSaveState();
            return new CommandResult(uoe.getMessage());
        } catch (TaskNotFoundException tnfe) {
            model.undoSaveState();
            assert false : "The target task cannot be missing";
        }
        
        
        model.checkForOverdueTasks();
        
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }
    
    //@@author A0142184L
	private void raiseJumpToTaskEvent(Task postEdit) {
		UnmodifiableObservableList<ReadOnlyTask> listAfterEdit = model.getFilteredTaskList();
		int indexToScrollTo = listAfterEdit.indexOf(postEdit);
		EventsCenter.getInstance().post(new JumpToListRequestEvent(indexToScrollTo));
	}    
}
