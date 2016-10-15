package seedu.inbx0.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.core.UnmodifiableObservableList;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.task.*;
import seedu.inbx0.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.inbx0.model.tag.Tag;
import seedu.inbx0.model.tag.UniqueTagList;

public class TagCommand extends Command{
	
	public static final String COMMAND_WORD = "tag";
	
	public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds 1 or more tags to an existing task.\n"
            + "Parameters: INDEX (must be a positive integer) [t/TAG]... (must have at least one tag) \n"
            + "Example: " + COMMAND_WORD + " 1 " + "t/cute \n";
	
	public static final String MESSAGE_SUCCESS = "Tag successfully added!";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the tasklist";
	public static final String TAGS_INVALID_ARGUMENTS = "You must input at least 1 tag!";
	
	public final int targetIndex;
    public UniqueTagList tags;
	
    public TagCommand(int targetIndex,  Set<String> tags) throws IllegalValueException {
    	
    	this.targetIndex = targetIndex;
        
        final Set<Tag> tagSet = new HashSet<>();
       
        for (String tagName : tags) {
        	tagSet.add(new Tag(tagName));
        }
            
        UniqueTagList addedTags = null;
            
        if(!tagSet.isEmpty()) {
            addedTags = new UniqueTagList(tagSet);
        }
          
        this.tags = addedTags;
    }
    
    private UniqueTagList obtainUniqueTagList(ReadOnlyTask taskToEdit) {
        
        UniqueTagList original = taskToEdit.getTags();
              
        return original;
    }
    
    private Task createToEditWithTask(ReadOnlyTask taskToEdit, UniqueTagList tags) throws IllegalValueException{
    	
    	
    	Task toEditWith = new Task (
                new Name(taskToEdit.getName().getName()),
                new Date(taskToEdit.getStartDate().getDate()),
                new Time(taskToEdit.getStartTime().getTime()),
                new Date(taskToEdit.getEndDate().getDate()),
                new Time(taskToEdit.getEndTime().getTime()),
                new Importance(taskToEdit.getLevel().getLevel()),
                tags
                );
        return toEditWith;
    }
    
	@Override
    public CommandResult execute() {
		
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        
        UniqueTagList originalTags = obtainUniqueTagList(taskToEdit);
        
        originalTags.mergeFrom(tags);
        
        Task toEditWith = null;
        
        try {
            toEditWith = createToEditWithTask(taskToEdit, originalTags);
        } catch (IllegalValueException e1) {
            return new CommandResult(TAGS_INVALID_ARGUMENTS);
        }
        
        try {
            model.editTask(taskToEdit, toEditWith);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e ) {    
            return new CommandResult(MESSAGE_DUPLICATE_TASK);            
        }
        
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
