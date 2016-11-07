package seedu.address.logic.commands;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.DisplayTaskListEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
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

    private Task taskToEdit;
    private final int internalListIndex;

    /**
     * For editing task corresponding to the internalListIndex
     * @throws IllegalValueException when name or tagName contain illegal values
     */
    public EditCommand(int targetIndex, Optional<String> name, 
    		Optional<LocalDateTime> newStartDate, Optional<LocalDateTime> newEndDate,
    		Set<String> tags, boolean isRemoveStartDateTime, 
    		boolean isRemoveEndDateTime)
    		throws IllegalValueException {
    	
    	UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
		
    	checkValidIndex(lastShownList, targetIndex);
		this.taskToEdit = getTaskToEdit(lastShownList, targetIndex);
		validateEndDateTimeAfterStartDateTime(newStartDate, newEndDate, isRemoveStartDateTime, isRemoveEndDateTime);
		
		this.internalListIndex = getIndexInInternalList(taskToEdit);
		this.setTaskToEditName(name);
		this.setTaskToEditDates(newStartDate, newEndDate, isRemoveStartDateTime, isRemoveEndDateTime);
        this.setTaskToEditTags(tags);
        
        this.checkNotDuplicateTask(lastShownList);
    }
    
    @Override
    public CommandResult execute() {
    	
    	EventsCenter.getInstance().post(new DisplayTaskListEvent(model.getFilteredTaskList()));

        model.saveState();
        	
        try {
			model.editTask(internalListIndex, taskToEdit);
		} catch (TaskNotFoundException e) {
			return new CommandResult(e.getMessage());
		}
            
        raiseJumpToTaskEvent(taskToEdit);

        model.checkForOverdueTasks();
        
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }
    
    /**
     * Checks that the taskToEdit is not a duplicate task after updating all the edits
     * @throws IllegalValueException if it is a duplicate
     */
    private void checkNotDuplicateTask(UnmodifiableObservableList<ReadOnlyTask> lastShownList) 
    		throws IllegalValueException{
    	if (lastShownList.contains(taskToEdit)) {
            throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
        }
    }
    
    /**
     * Checks that the targetIndex is within boundary of lastShownList 
     * @throws IllegalValueException when targetIndex is out of bound
     */
    private void checkValidIndex(UnmodifiableObservableList<ReadOnlyTask> lastShownList, int targetIndex) 
    	throws IllegalValueException {
    	if (lastShownList.size() < targetIndex) {
            model.undoSaveState();
            indicateAttemptToExecuteIncorrectCommand();
            throw new IllegalValueException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }
    }
    
    /**
     * Get the index of @param task in the TaskManager internalList
     */
    private int getIndexInInternalList (Task task) {
    	UnmodifiableObservableList<ReadOnlyTask> fullList = model.getUnfilteredTaskList();
    	return fullList.indexOf(task);
    }
    
    /**
     * Get the targeted Task to be edited
     */
    private Task getTaskToEdit(UnmodifiableObservableList<ReadOnlyTask> lastShownList, int targetIndex) {
    	return new Task(lastShownList.get(targetIndex - 1));
    }
    
    /**
     * Update taskToEdit with the new name
     * @throws IllegalValueException if name content is of illegal value
     */
    private void setTaskToEditName(Optional<String> name) throws IllegalValueException {
    	if (name.isPresent()) {
    	    Name newName = new Name(name.get());
    		taskToEdit.setName(newName);
        }
    }
    
    /**
     * Updates taskToEdit with the new start date and end date
     */
    private void setTaskToEditDates(Optional<LocalDateTime> newStartDateTime,
    		Optional<LocalDateTime> newEndDateTime, boolean isRemoveStartDateTime,
    		boolean isRemoveEndDateTime) {
    	if (newEndDateTime.isPresent()) {
        	taskToEdit.setEndDate(newEndDateTime.get());
        }
        
        if (newStartDateTime.isPresent()) {
            taskToEdit.setStartDate(newStartDateTime.get());
        }
        
        if (isRemoveStartDateTime) {
        	taskToEdit.removeStartDate();
        }
        
        if (isRemoveEndDateTime) {
        	taskToEdit.removeEndDate();
        }
    }
    
    /**
     * Update taskToEdit with the new tags
     * @throws IllegalValueException 
     */
    private void setTaskToEditTags (Set<String> tags) throws IllegalValueException {
    	final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
        	tagSet.add(new Tag(tagName));
        }
        
        UniqueTagList tagList = new UniqueTagList(tagSet);
        UniqueTagList newTags = this.taskToEdit.getTags();
        newTags.mergeFrom(tagList);
        this.taskToEdit.setTags(newTags);
    }
    //@@author A0142184L
	private void raiseJumpToTaskEvent(Task taskToEdit) {
		UnmodifiableObservableList<ReadOnlyTask> listAfterEdit = model.getFilteredTaskList();
		int indexToScrollTo = listAfterEdit.indexOf(taskToEdit);
		EventsCenter.getInstance().post(new JumpToListRequestEvent(indexToScrollTo));
	}    
	
	//@@author A0143756Y
	private void validateEndDateTimeAfterStartDateTime(Optional<LocalDateTime> newStartDateTime,
			Optional<LocalDateTime> newEndDateTime, boolean isRemoveStartDateTime,
			boolean isRemoveEndDateTime){
		
		if (newStartDateTime.isPresent() && newEndDateTime.isPresent()){
        	LocalDateTime startDateTime = newStartDateTime.get();
        	LocalDateTime endDateTime = newEndDateTime.get();
        	
        	Task.validateEndDateTimeAfterStartDateTime(startDateTime, endDateTime);
        }
        
        if (newStartDateTime.isPresent() && !newEndDateTime.isPresent()){
        	if (isRemoveEndDateTime){
        		throw new IllegalArgumentException(Task.MESSAGE_START_DATE_TIME_CANNOT_BE_SET_WITH_END_DATE_TIME_REMOVED);
        	}
        	
        	if (!taskToEdit.getEndDate().isPresent()){
        		throw new IllegalArgumentException(Task.MESSAGE_START_DATE_TIME_CANNOT_BE_SET_WITH_END_DATE_TIME_MISSING);
        	}
        	
        	LocalDateTime startDateTime = newStartDateTime.get();
    		LocalDateTime endDateTime = taskToEdit.getEndDate().get();
    		
    		Task.validateEndDateTimeAfterStartDateTime(startDateTime, endDateTime);
        }
        
        if (!newStartDateTime.isPresent() && newEndDateTime.isPresent()){           	
        	if ((!isRemoveStartDateTime) && taskToEdit.getStartDate().isPresent()){
        		LocalDateTime endDateTime = newEndDateTime.get();
        		LocalDateTime startDateTime = taskToEdit.getStartDate().get();
        		
        		Task.validateEndDateTimeAfterStartDateTime(startDateTime, endDateTime);
        	}
        }
	}
}
