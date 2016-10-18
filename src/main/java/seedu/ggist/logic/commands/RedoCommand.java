package seedu.ggist.logic.commands;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.model.task.ReadOnlyTask;
import seedu.ggist.model.task.Task;
import seedu.ggist.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.ggist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Redo a previous undo command.
 */

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redo the previous undo command.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_COMMAND_SUCCESS = "Redo previous undo command: %1$s";

    @Override
    public CommandResult execute() {
        if (redoListOfCommands.empty() ==true) {
            return new CommandResult(Messages.MESSAGE_NO_PREVIOUS_UNDO_COMMAND);
        }
        
        listOfCommands.push(redoListOfCommands.peek());
        String previousUndoCommand = redoListOfCommands.pop();
        if (previousUndoCommand.equals("delete")){
            listOfTasks.push(redoListOfTasks.peek());
            ReadOnlyTask toDelete = redoListOfTasks.pop();
            try {
                model.deleteTask(toDelete);
            } catch (TaskNotFoundException e) {
                e.printStackTrace();
            }
            
        }
        
        else if (previousUndoCommand.equals("add")){
            listOfTasks.push(redoListOfTasks.peek());
            Task toAdd = (Task) redoListOfTasks.pop();
            try {
                model.addTask(toAdd);
            } catch (DuplicateTaskException e) {
                e.printStackTrace();
            }        
        }
        
        else if (previousUndoCommand.equals("done")){
            listOfTasks.push(redoListOfTasks.peek());
            ReadOnlyTask redoDone = redoListOfTasks.pop();
            redoDone.setDone();
            model.updateFilteredListToShowAllUndone();
        }

        return new CommandResult(String.format(MESSAGE_UNDO_COMMAND_SUCCESS, previousUndoCommand));
    }
    


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

}
