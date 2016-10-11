package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;


import java.util.HashSet;
import java.util.Set;

/**
 * Updates a task in the task list.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Update a task in the task list.\n "
            + "Parameters: INDEX (must be a positive integer) NAME \n"
            + "Example: " + COMMAND_WORD
            + " 1 cs2103 t/quiz";
    
    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated Task: %1$s"; 
    
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";
    
    public final int targetIndex;
    private final Task toUpdate;
    
    public String task_name;
    
    public UpdateCommand(int targetIndex,String name,Set<String> tags) throws IllegalValueException{ 
        this.targetIndex = targetIndex;
        
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
       
        this.toUpdate = new Task(
                new Name(name),
                new UniqueTagList(tagSet)
        );
    }
    
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex - 1);
        
        assert model != null;
        try {
            model.updateTask(taskToUpdate, toUpdate);
        } catch (DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } 
        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, toUpdate));
    }
}
