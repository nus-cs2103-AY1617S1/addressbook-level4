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
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the current listing as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Task Done: %1$s";

    public final ArrayList<Integer> targetIndexes;

    public DoneCommand(ArrayList<Integer> indexes) {
        this.targetIndexes = indexes;
    }
    
  //@@author A0138420N

    @Override
    public CommandResult execute() {
        Collections.sort(targetIndexes);
        for(int i = 0; i < targetIndexes.size(); i++){
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (lastShownList.size() + i < targetIndexes.get(i)) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask taskToMarkDone = lastShownList.get(targetIndexes.get(i) - 1 - i);
        try {
            model.doneTask(taskToMarkDone);
            listOfCommands.push(COMMAND_WORD);
            listOfTasks.push(taskToMarkDone);
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

        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, sb.toString()));
    }

}
//@@author
