package seedu.ggist.logic.commands;

//@@author A0138420N

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
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
            Task toDelete = (Task) redoListOfTasks.pop();
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
        
        else if (previousUndoCommand.equals("edit")){
            listOfTasks.push(redoListOfTasks.peek());
            Task redoEdit = (Task)redoListOfTasks.pop();
         
            try {
                
                editTaskField.push(redoEditTaskField.peek());
                
                if (editTaskField.peek().equals("task")) {
                    editTaskValue.push(redoEdit.getTaskName().toString()); 
                } else if (editTaskField.peek().equals("start date")) {
                    editTaskValue.push(redoEdit.getStartDate().toString());
                } else if (editTaskField.peek().equals("end date")) {
                    editTaskValue.push(redoEdit.getEndDate().toString()); 
                } else if (editTaskField.peek().equals("start time")) {
                    editTaskValue.push(redoEdit.getStartTime().toString()); 
                } else if (editTaskField.peek().equals("end time")) {
                    editTaskValue.push(redoEdit.getEndTime().toString());
                } else if (editTaskField.peek().equals("priority")) {
                    editTaskValue.push(redoEdit.getPriority().toString());
                }
                model.editTask(redoEdit, redoEditTaskField.pop(), redoEditTaskValue.pop());
            } catch (TaskNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalValueException ive) {
                return new CommandResult(ive.getMessage());
            }
        }


        return new CommandResult(String.format(MESSAGE_UNDO_COMMAND_SUCCESS, previousUndoCommand));
    }
    


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

}
//@@author
