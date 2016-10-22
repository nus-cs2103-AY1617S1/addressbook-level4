package seedu.ggist.logic.commands;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
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

    public static final String MESSAGE_UNDO_COMMAND_SUCCESS = "Undo previous command: %1$s %2$s";

    @Override
    public CommandResult execute() {
        if (listOfCommands.empty() ==true) {
            return new CommandResult(Messages.MESSAGE_NO_PREVIOUS_COMMAND);
        }
        
        redoListOfCommands.push(listOfCommands.peek());
        String previousCommand = listOfCommands.pop();
        if (previousCommand.equals("add")){
            redoListOfTasks.push(listOfTasks.peek());
            ReadOnlyTask toDelete = listOfTasks.pop();
            try {
                model.deleteTask(toDelete);
            } catch (TaskNotFoundException e) {
                e.printStackTrace();
            }
            
        }
        
        else if (previousCommand.equals("delete")){
            redoListOfTasks.push(listOfTasks.peek());
            Task toAdd = (Task) listOfTasks.pop();
            try {
                model.addTask(toAdd);
            } catch (DuplicateTaskException e) {
                e.printStackTrace();
            }        
        }
        
        else if (previousCommand.equals("done")){
            redoListOfTasks.push(listOfTasks.peek());
            ReadOnlyTask undoDone = listOfTasks.pop();
            undoDone.setUnDone();
            model.updateFilteredListToShowAllUndone();
        }
        
        else if (previousCommand.equals("edit")){
            redoListOfTasks.push(listOfTasks.peek());
            ReadOnlyTask undoEdit = listOfTasks.pop();
         
            try {
                
                redoEditTaskField.push(editTaskField.peek());
                
                if (redoEditTaskField.peek().equals("task")){
                    redoEditTaskValue.push(undoEdit.getTaskName().toString()); 
                } else if (redoEditTaskField.peek().equals("start date")){
                    redoEditTaskValue.push(undoEdit.getStartDate().toString());
                } else if (redoEditTaskField.peek().equals("end date")){
                    redoEditTaskValue.push(undoEdit.getEndDate().toString()); 
                } else if (redoEditTaskField.peek().equals("start time")){
                    redoEditTaskValue.push(undoEdit.getStartTime().toString()); 
                } else if (redoEditTaskField.peek().equals("end time")){
                    redoEditTaskValue.push(undoEdit.getEndTime().toString());
                } else if (redoEditTaskField.peek().equals("priority")){
                    redoEditTaskValue.push(undoEdit.getPriority().toString());
                }
                model.editTask(undoEdit, editTaskField.pop(), editTaskValue.pop());
            } catch (TaskNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalValueException ive) {
                new CommandResult(ive.getMessage());
            }
        }
        
        

        return new CommandResult(String.format(MESSAGE_UNDO_COMMAND_SUCCESS, previousCommand));
    }
    


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

}
