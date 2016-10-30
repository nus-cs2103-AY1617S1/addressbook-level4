package seedu.taskscheduler.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.tag.Tag;
import seedu.taskscheduler.model.tag.UniqueTagList;
import seedu.taskscheduler.model.task.Task;
import seedu.taskscheduler.model.task.UniqueTaskList.TaskNotFoundException;

public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set tags to a task in task scheduler  "
            + "Parameters: [INDEX] TAG..."
            + "Example: " + COMMAND_WORD
            + " School Priority\n";

    public static final String MESSAGE_SUCCESS = "Tags: %s";

    private final int targetIndex;
    private final String args;
    
    private Task task;
    private UniqueTagList tagList;
    
    /**
     * Convenience constructors using raw values.
     */
    public TagCommand(String args) {
        this(EMPTY_INDEX, args);
    }
    
    public TagCommand(int targetIndex, String args) {
        this.targetIndex = targetIndex;
        this.args = args;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            task = (Task)getTaskFromIndexOrLastModified(targetIndex);
            tagList = task.getTags();
            model.tagTask(task, extractTagsFromArgs(args));
            CommandHistory.addExecutedCommand(this);
            CommandHistory.setModifiedTask(task);
            
        } catch (TaskNotFoundException | IllegalValueException ex) {
            return new CommandResult(ex.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, task));
    }

    @Override
    public CommandResult revert() {
        try {
            model.tagTask(task, tagList);
            CommandHistory.addRevertedCommand(this);
            CommandHistory.setModifiedTask(task);
        } catch (TaskNotFoundException e) {
            assert false : Messages.MESSAGE_TASK_CANNOT_BE_MISSING;
        }
        return new CommandResult(String.format(MESSAGE_REVERT_COMMAND, COMMAND_WORD, "\n" + task));
    }
    
    public UniqueTagList extractTagsFromArgs(String args) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : args.split("\\s+")) {
            tagSet.add(new Tag(tagName));
        }
        return new UniqueTagList(tagSet);
    }
    
}