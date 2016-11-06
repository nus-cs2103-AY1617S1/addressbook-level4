package seedu.agendum.logic.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import seedu.agendum.commons.core.Messages;
import seedu.agendum.commons.core.UnmodifiableObservableList;
import seedu.agendum.model.task.ReadOnlyTask;
import seedu.agendum.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.agendum.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0133367E
/**
 * Mark task(s) identified using their last displayed indices in the task listing.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";    
    public static final String COMMAND_FORMAT = "mark <id> <more ids>";
    public static final String COMMAND_DESCRIPTION = "mark task(s) as completed";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task(s)!";
    public static final String MESSAGE_DUPLICATE = "Hey, the task already exists";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " - "
            + COMMAND_DESCRIPTION + "\n"
            + COMMAND_FORMAT + "\n"
            + "(The id must be a positive number)\n"
            + "Example: " + COMMAND_WORD + " 1 3 5-6";

    private ArrayList<Integer> targetIndexes;
    private ArrayList<ReadOnlyTask> tasksToMark;


    public MarkCommand(Set<Integer> targetIndexes) {
        this.targetIndexes = new ArrayList<Integer>(targetIndexes);
        Collections.sort(this.targetIndexes);
        this.tasksToMark = new ArrayList<ReadOnlyTask>();
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (isAnyIndexInvalid(lastShownList)) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
 
        for (int targetIndex: targetIndexes) {
            ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);
            tasksToMark.add(taskToMark);
        }
        
        try {
            model.markTasks(tasksToMark);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (DuplicateTaskException pnfe) {
            model.resetDataToLastSavedList();
            return new CommandResult(MESSAGE_DUPLICATE);
        }

        return new CommandResult(MESSAGE_MARK_TASK_SUCCESS);
    }

    private boolean isAnyIndexInvalid(UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        return targetIndexes.stream().anyMatch(index -> index > lastShownList.size());
    }

    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getFormat() {
        return COMMAND_FORMAT;
    }

    public static String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}
