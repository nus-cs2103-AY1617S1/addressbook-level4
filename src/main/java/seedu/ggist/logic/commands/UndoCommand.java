package seedu.ggist.logic.commands;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.model.task.ReadOnlyTask;
import seedu.ggist.model.task.Task;
import seedu.ggist.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.ggist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Undo a previous command.
 */

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undo the previous command.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_COMMAND_SUCCESS = "Undo previous command: %1$s";

    @Override
    public CommandResult execute() {
        if (listOfCommands.empty() ==true) {
            return new CommandResult(Messages.MESSAGE_NO_PREVIOUS_COMMAND);
        }
        
        String previousCommand = listOfCommands.pop();
        if (previousCommand.equals("add")){
            ReadOnlyTask toDelete = listOfTasks.pop();
            try {
                model.deleteTask(toDelete);
            } catch (TaskNotFoundException e) {
                e.printStackTrace();
            }
            
        }
        
        else if (previousCommand.equals("delete")){
            Task toAdd = (Task) listOfTasks.pop();
            try {
                model.addTask(toAdd);
            } catch (DuplicateTaskException e) {
                e.printStackTrace();
            }        
        }
        
        else if (previousCommand.equals("done")){
            ReadOnlyTask undoDone = listOfTasks.pop();
            undoDone.setUnDone();
            model.updateFilteredTaskListToShowUndone();
        }

        return new CommandResult(String.format(MESSAGE_UNDO_COMMAND_SUCCESS, previousCommand));
    }
    


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

}
