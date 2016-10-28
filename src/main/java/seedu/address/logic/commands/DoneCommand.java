package seedu.address.logic.commands;
//@@author A0142325R
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Mark the specified task or event as done.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mark the specified task or event as done \n "
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " horror night";
    public static final String MARK_DONE_SUCCESS="Marked as done: %1$s";
    public static final String MULTIPLE_TASK_SATISFY_KEYWORD="Please select the Task identified "
    		+ "by the index number.\n"+"Parameters: INDEX(must be a positive integer)\n"
    		+"Example: "+COMMAND_WORD+" 1";
    public static final String TASK_NOT_FOUND="Task not found: %1$s";

    private final Set<String> keywords;
    public final int targetIndex;

    public DoneCommand(Set<String> keywords) {
        this.keywords = keywords;
        targetIndex=-1;
    }
    
    public DoneCommand(int indexToMark){
    	keywords=null;
    	targetIndex=indexToMark;
    }

    @Override
    public CommandResult execute() {
        ReadOnlyTask taskToMark = null;
        if (keywords != null) {
            model.updateFilteredTaskList(keywords);
            if (model.getFilteredTaskList().size() == 0) {
                return new CommandResult(TASK_NOT_FOUND);
            } else if (model.getFilteredTaskList().size() > 1) {
                return new CommandResult(MULTIPLE_TASK_SATISFY_KEYWORD);
            } else {
                UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
                assert lastShownList.size() == 1;
                taskToMark = lastShownList.get(0);
            }
        } else {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            if (lastShownList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            taskToMark = lastShownList.get(targetIndex - 1);
        }
        model.markTask(taskToMark);
        String message = String.format(MARK_DONE_SUCCESS, taskToMark);
        model.saveState(message);
        return new CommandResult(message);
    }

}
