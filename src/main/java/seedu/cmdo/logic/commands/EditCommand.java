package seedu.cmdo.logic.commands;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import seedu.cmdo.commons.core.Messages;
import seedu.cmdo.commons.core.UnmodifiableObservableList;
import seedu.cmdo.commons.exceptions.IllegalValueException;
import seedu.cmdo.logic.parser.MainParser;
import seedu.cmdo.model.tag.Tag;
import seedu.cmdo.model.tag.UniqueTagList;
import seedu.cmdo.model.task.Detail;
import seedu.cmdo.model.task.DueByDate;
import seedu.cmdo.model.task.DueByTime;
import seedu.cmdo.model.task.Priority;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.model.task.Task;
import seedu.cmdo.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0141128R
/**
 * Edits the task associated with the intended index.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the task residing at index input. \n"
            + "Parameters: <index> <details> by/on <date> at <time> /<priority> /<TAG...>\n"
    		+ "NOTE: You must reenter all parameters again.\n"
            + "Example: " + COMMAND_WORD + " 2 Take Bongo out for a walk tomorrow 2pm /medium -dog";
    
    public static final String MESSAGE_EDITED_TASK_SUCCESS = "Edited task.";

    private final int targetIndex;
    private final Task toEditWith;
    private final boolean floating;
    private final boolean onlyOne;
    private final boolean removePriority;
    private boolean tagIsEmpty = false;
    
    
    public EditCommand(	boolean removePriority,
    					boolean floating,
    					boolean onlyOne,
    					int targetIndex,
    					String newDetail,
    					LocalDate newDueByDate,
    					LocalTime newDueByTime,
    					String newPriority,
    					Set<String> newTags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        if(newTags.isEmpty())
        	tagIsEmpty = true;
        for (String tagName : newTags) {
            tagSet.add(new Tag(tagName));
        }
        this.toEditWith = new Task(
                new Detail(newDetail),
                new DueByDate (newDueByDate),
                new DueByTime(newDueByTime),
                new Priority(newPriority),
                new UniqueTagList(tagSet)
        );
        this.floating = floating;
        this.targetIndex = targetIndex;
        this.removePriority = removePriority;
        this.isUndoable=true;
        this.onlyOne = onlyOne;
    }
        
    /**
     * For RANGE DATE AND TIME
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(boolean removePriority, 
    				  int targetIndex,
    		          String details,
                      LocalDate dueByDateStart,
                      LocalTime dueByTimeStart,
                      LocalDate dueByDateEnd,
                      LocalTime dueByTimeEnd,
                      String priority,
                      Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        if(tags.isEmpty())
        	tagIsEmpty = true;
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toEditWith = new Task(
                new Detail(details),
                new DueByDate (dueByDateStart, dueByDateEnd),
                new DueByTime(dueByTimeStart, dueByTimeEnd),
                new Priority(priority),
                new UniqueTagList(tagSet)
        );
        this.targetIndex = targetIndex;
        this.isUndoable = true;
        floating = false;//since if range constructor is used, user would have keyed in a timing 
        this.removePriority = removePriority; 
        this.isUndoable=true;
        this.onlyOne = false;
    }
    
    public ReadOnlyTask getTask() {
        return toEditWith;
    }
    
    //check for changes in detail and append
    public void editDetails(ReadOnlyTask taskToEdit){
        if(toEditWith.getDetail().toString().equals(""))
        	toEditWith.setDetail(taskToEdit.getDetail());
        }
    //check for changes in date and time and append
    public void editDateTime(ReadOnlyTask taskToEdit){
    	try {//check if changing to floating task
        if(floating)
        	toEditWith.setFloating();
        // check if the user only meant to key in only one of either date or time
        else if (onlyOne) {
        	if (toEditWith.getDueByDate().dateNotEntered() && toEditWith.getDueByTime().timeNotEntered()) {
        		// The user accidentally typed in only. Ignore.
        	} else if (toEditWith.getDueByDate().dateNotEntered()) {
        		// The user used time only, so he must mean today's time.
        		toEditWith.setDueByDate(new DueByDate(LocalDate.now()));
        	} else if (toEditWith.getDueByTime().timeNotEntered()) {
        		// The user used date only, so he must mean one single date only.
        		toEditWith.setDueByTime(new DueByTime(MainParser.NO_TIME_DEFAULT));
        	}
        }
        //check for if time is empty and append and check if have changes in date otherwise append old date
        else{
        if(toEditWith.getDueByDate().dateNotEntered() && toEditWith.getDueByTime().timeNotEntered()){
        	toEditWith.setDueByDate(taskToEdit.getDueByDate());
        	toEditWith.setDueByTime(taskToEdit.getDueByTime());
        	}
        //time entered only
        //but if single date and time is entered, it bypass the check and fails
        else if(!(toEditWith.getDueByTime().timeNotEntered()) && !(toEditWith.getDueByDate().isRange())){
        	toEditWith.setDueByDate(taskToEdit.getDueByDate());
        	}
        //date entered only
        else if(!(toEditWith.getDueByDate().dateNotEntered()) && toEditWith.getDueByTime().timeNotEntered()){
        	toEditWith.setDueByTime(taskToEdit.getDueByTime());
        			}
        }
    	}catch (Exception e) {
    		// This is an internal method, no exception should be thrown.
    	}
    }
   
    //check if priority is empty and append with old details
    public void editPriority(ReadOnlyTask taskToEdit){
    	if(toEditWith.getPriority().getValue().equals("")) 
            toEditWith.getPriority().setPriority(taskToEdit.getPriority().getValue()); 
          //remove priority 
          if(removePriority) 
            toEditWith.getPriority().setPriority(""); 
    }
    
    //append tags 
    public void editTags(ReadOnlyTask taskToEdit){
    	  if(tagIsEmpty) 
    		  toEditWith.setTags(taskToEdit.getTags()); 
    }
    
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        //slap these
        // Check if target index is valid
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }      
        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        // Check if task is done.
        if (taskToEdit.checkDone().value) {
            indicateAttemptToExecuteIncorrectCommand();
        	return new CommandResult(Messages.MESSAGE_EDIT_TASK_IS_DONE_ERROR);
        }
        
        //check for changes in detail and append
        editDetails(taskToEdit);
        //check for date and time and append
        editDateTime(taskToEdit);
        //check if priority is empty and append with old details
        editPriority(taskToEdit);
        //append tags 
        editTags(taskToEdit);        
        try {
        	updateSelectionInPanel(model.editTask(taskToEdit, toEditWith));
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }      
    	return new CommandResult(MESSAGE_EDITED_TASK_SUCCESS);
    }
}