package seedu.ggist.logic.commands;

import java.util.ArrayList;
import java.util.Collections;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.core.UnmodifiableObservableList;
import seedu.ggist.model.task.ReadOnlyTask;
import seedu.ggist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Marks a task as done identified using it's last displayed index from the
 * task list.
 */
//@@author A0147994J
public class ContinueCommand extends Command {

    public static final String COMMAND_WORD = "continue";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the done task identified by the index number used in the current listing as undone.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Task Continue: %1$s";

    public final ArrayList<Integer> targetIndexes;

    public ContinueCommand(ArrayList<Integer> indexes) {
        this.targetIndexes = indexes;
    }
    
  //@@author A0147994J

    @Override
    public CommandResult execute() {
        Collections.sort(targetIndexes);
        for(int i = 0; i < targetIndexes.size(); i++){
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (lastShownList.size() + i < targetIndexes.get(i)) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask taskToMarkContinue = lastShownList.get(targetIndexes.get(i) - 1 - i);
        try {
            model.continueTask(taskToMarkContinue);
            model.getListOfCommands().push(COMMAND_WORD);
            model.getListOfTasks().push(taskToMarkContinue);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < targetIndexes.size(); i++){
            if (i != targetIndexes.size()-1){
                sb.append(targetIndexes.get(i));
                sb.append(", ");            
            }
            else {
                sb.append(targetIndexes.get(i));
            }
        }
        indicateCorrectCommandExecuted();
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, sb.toString()));
    }

}