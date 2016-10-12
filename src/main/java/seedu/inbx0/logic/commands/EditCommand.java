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
    public static final String MESSAGE_INVALID_ARGUMENTS = "The arguments provided are invalid.";
    public static final int TOTAL_NUMBER_OF_ARGUMENTS = 6;
    
    public final int targetIndex;
    public String [] editArguments;
    public UniqueTagList tags;

    public EditCommand(int targetIndex, String [] argumentsToEdit, Set<String> tags) throws IllegalValueException {
        
        this.targetIndex = targetIndex;
        
        if(tags == null) {
            this.tags = null;
        }
        else {
            final Set<Tag> tagSet = new HashSet<>();
            for (String tagName : tags) {
                tagSet.add(new Tag(tagName));
            }
            
            UniqueTagList editedTags = null;
            
            if(!tagSet.isEmpty()) {
                editedTags = new UniqueTagList(tagSet);
            }
         
            this.tags = editedTags;
        }
        this.editArguments = argumentsToEdit;    
    }


    private UniqueTagList obtainUniqueTagList(ReadOnlyTask taskToEdit) {
        
      UniqueTagList original = taskToEdit.getTags();
            
      return original;
    }


    private String[] obtainArguments(String[] editArguments, ReadOnlyTask taskToEdit) {
       
        String [] originalArguments = new String[6];
                    
        originalArguments[0] = taskToEdit.getName().getName();
        originalArguments[1] = taskToEdit.getStartDate().getDate();
        originalArguments[2] = taskToEdit.getStartTime().getTime();
        originalArguments[3] = taskToEdit.getEndDate().getDate();
        originalArguments[4] = taskToEdit.getEndTime().getTime();
        originalArguments[5] = taskToEdit.getLevel().getLevel();
            
        for(int i = 0; i < TOTAL_NUMBER_OF_ARGUMENTS; i++) {
            if(editArguments[i] == null)
                editArguments[i] = originalArguments[i];                       
        }
        
        return editArguments;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        this.editArguments = obtainArguments(editArguments, taskToEdit);
        
        if(tags == null)
            this.tags = obtainUniqueTagList(taskToEdit);
        
        Task toEditWith = null;
        try {
            toEditWith = createToEditWithTask(editArguments, tags);
        } catch (IllegalValueException e1) {
            return new CommandResult(String.format(Messages.MESSAGE_INVALID_ARGUMENTS, MESSAGE_USAGE));
        }

        try {
            model.editTask(taskToEdit, toEditWith);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e ) {    
            return new CommandResult(MESSAGE_DUPLICATE_TASK);            
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }


    private Task createToEditWithTask(String[] editArguments, UniqueTagList tags) throws IllegalValueException {
        Task toEditWith = new Task (
                new Name(editArguments[0]),
                new Date(editArguments[1]),
                new Time(editArguments[2]),
                new Date(editArguments[3]),
                new Time(editArguments[4]),
                new Importance(editArguments[5]),
                tags
                );
        return toEditWith;
    }
}
