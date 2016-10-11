package seedu.inbx0.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.core.UnmodifiableObservableList;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.tag.Tag;
import seedu.inbx0.model.tag.UniqueTagList;
import seedu.inbx0.model.task.Date;
import seedu.inbx0.model.task.Importance;
import seedu.inbx0.model.task.Name;
import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.model.task.Task;
import seedu.inbx0.model.task.Time;
import seedu.inbx0.model.task.UniqueTaskList;
import seedu.inbx0.model.task.UniqueTaskList.TaskNotFoundException;


/**
 * Edits a task identified using it's last displayed index from the address book.
 */
public class EditCommand extends Command {
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";
    public static final int TOTAL_NUMBER_OF_ARGUMENTS = 6;
    
    public final int targetIndex;
    public final Task toEditWith;

    public EditCommand(int targetIndex, String [] argumentsToEdit, Set<String> tags) throws IllegalValueException {
        
        this.targetIndex = targetIndex;
        
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        UniqueTagList editedTags;
        
        if(!tagSet.isEmpty()) {
            editedTags = new UniqueTagList(tagSet);
        }
        else {
            editedTags = obtainUniqueTagList(targetIndex);
            if(editedTags == null)
                editedTags = new UniqueTagList();
        }
       
        
        argumentsToEdit = obtainArguments(argumentsToEdit, targetIndex); 
        
        this.toEditWith = new Task(
                new Name(argumentsToEdit[0]),
                new Date(argumentsToEdit[1]),
                new Time(argumentsToEdit[2]),
                new Date(argumentsToEdit[3]),
                new Time(argumentsToEdit[4]),
                new Importance(argumentsToEdit[5]),
                editedTags
        );
        
        
    }


    private UniqueTagList obtainUniqueTagList(int targetIndex) {
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (lastShownList.size() < targetIndex) {
            
        }
        else {
            ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
            UniqueTagList original = taskToEdit.getTags();
            
            return original;
        }
        return null;
    }


    private String[] obtainArguments(String[] argumentsToEdit, int targetIndex) {
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        String [] originalArguments = new String[6];
        
        if (lastShownList.size() < targetIndex) {
            
        }
        else {
            ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
            
            originalArguments[0] = taskToEdit.getName().getName();
            originalArguments[1] = taskToEdit.getStartDate().getDate();
            originalArguments[2] = taskToEdit.getStartTime().getTime();
            originalArguments[3] = taskToEdit.getEndDate().getDate();
            originalArguments[4] = taskToEdit.getEndTime().getTime();
            originalArguments[5] = taskToEdit.getLevel().getLevel();
            
            for(int i = 0; i < TOTAL_NUMBER_OF_ARGUMENTS; i++) {
                if(argumentsToEdit[i] == null)
                   argumentsToEdit[i] = originalArguments[i]; 
            }           
        }
        
        return argumentsToEdit;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);

        try {
            model.editTask(taskToEdit, toEditWith);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e ) {    
            return new CommandResult(MESSAGE_DUPLICATE_TASK);            
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }
}
