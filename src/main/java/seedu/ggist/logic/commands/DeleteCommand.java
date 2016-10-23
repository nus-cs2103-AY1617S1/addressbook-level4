package seedu.ggist.logic.commands;

import java.util.ArrayList;
import java.util.Collections;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.core.UnmodifiableObservableList;
import seedu.ggist.model.task.ReadOnlyTask;
import seedu.ggist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the current listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final ArrayList<Integer> targetIndexes;

    public DeleteCommand(ArrayList<Integer> indexes) {
        this.targetIndexes = indexes;
    }


    @Override
    public CommandResult execute() {
        Collections.sort(targetIndexes);
        for(int i = 0; i < targetIndexes.size(); i++){
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (lastShownList.size() + i < targetIndexes.get(i)) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
       
        ReadOnlyTask taskToDelete = lastShownList.get(targetIndexes.get(i) - 1 - i);
        try {
            model.deleteTask(taskToDelete);
            listOfCommands.push(COMMAND_WORD);
            listOfTasks.push(taskToDelete);
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
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS,sb.toString()));
    }


    @Override
    public  String toString(){
        return COMMAND_WORD;
    }

}
