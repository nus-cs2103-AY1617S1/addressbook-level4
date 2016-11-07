package seedu.todo.logic.commands;

import seedu.todo.commons.core.Messages;
import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.UniqueTaskList.TaskNotFoundException;
//@@author A0142421X
/**
 * Tags a task identified using it's last displayed index from the to do list.
 * with tags
 */
public class TagCommand extends Command{

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags the task identified by the index number used in the last task listing.\n"
            + "Tag names must be unique and can only contain alphabets and numbers.\n"
            + "Parameters: INDEX TAGNAME [MORE TAGNAMES]\n"
            + "Example: " + COMMAND_WORD + " 1 birthday clique";

    public static final String MESSAGE_SUCCESS = "Tagged Task at Index: %1$d\n%2$s";

    public final int targetIndex;
    public final UniqueTagList tags;
    
    public TagCommand(int targetIndex, String tagNames) throws IllegalValueException {

        this.targetIndex = targetIndex;
       
        tags = new UniqueTagList();
        for (String tagName : tagNames.trim().split(" ")) {
            tags.add(new Tag(tagName));
        }
        
    }
    
    /**
     * Executes the tag command.
     * 
     * If the index is invalid or if the specified task cannot be found, returns the 
     * relevant message to inform the user.
     */
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToTag = lastShownList.get(targetIndex - 1);

        try {
            model.addTaskTags(taskToTag, tags);
            
            model.refreshCurrentFilteredTaskList();
            model.updateTodayListToShowAll();
            model.updateWeekListToShowAll();
        } catch (TaskNotFoundException e) {
            return new CommandResult(Messages.MESSAGE_TASK_NOT_FOUND);
        }
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex, taskToTag));
    }
    
    
    
}
