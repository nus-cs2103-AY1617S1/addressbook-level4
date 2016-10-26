//@@author A0093896H
package seedu.todo.logic.commands;

import seedu.todo.commons.core.Messages;
import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Tags a task identified using it's last displayed index from the to do list.
 * with tags
 */
public class UntagCommand extends Command{

    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Untags the task identified by the index number used in the last task listing. "
            + "Tag names must be unique\n"
            + "Parameters: INDEX TAGNAME [MORE TAGNAMES]\n"
            + "Example: " + COMMAND_WORD + " 1 birthday clique";

    public static final String MESSAGE_SUCCESS = "Untagged Task: %1$s";

    public final int targetIndex;
    public final UniqueTagList tags;
    
    public UntagCommand(int targetIndex, String tagNames) throws IllegalValueException {

        this.targetIndex = targetIndex;
        
        if (tagNames.isEmpty()) {
            throw new IllegalValueException("Tag Names cannot be empty");
        }
        
        tags = new UniqueTagList();
        for (String tagName : tagNames.trim().split(" ")) {
            tags.add(new Tag(tagName));
        }
        
    }
    
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUntag = lastShownList.get(targetIndex - 1);

        try{
            model.deleteTaskTags(taskToUntag, tags);
            model.updateFilteredListToShowAll();
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be found";
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToUntag.getName()));
    }
    
    
    
}
