package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Tags a task identified using it's last displayed index from the to do list.
 * with tags
 */
public class TagCommand extends Command{

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags the task identified by the index number used in the last task listing.\n"
            + ": Tag names must be unique\n"
            + "Parameters: INDEX TAGNAME1 [MORE TAGNAMES]"
            + "Example: " + COMMAND_WORD + " 1 birthday clique";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Tagged Task: %1$s";

    public final int targetIndex;
    public final UniqueTagList tags;
    
    public TagCommand(String targetIndex, String tagNames) throws Exception {

        this.targetIndex = Integer.parseInt(targetIndex);
        
        if (tagNames.isEmpty()) {
            throw new Exception();
        }
        
        tags = new UniqueTagList();
        for (String tagName : tagNames.split(" ")) {
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

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }
    
    
    
}
