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

    public static final String MESSAGE_REDO_COMMAND_SUCCESS = "Redo previous undo command: %1$s";

    @Override
    public CommandResult execute() {
        if (model.getRedoListOfCommands().empty() ==true) {
            return new CommandResult(Messages.MESSAGE_NO_PREVIOUS_UNDO_COMMAND);
        }
        
        model.getListOfCommands().push(model.getRedoListOfCommands().peek());
        String previousUndoCommand = model.getRedoListOfCommands().pop();
        if (previousUndoCommand.equals("delete")){
            model.getListOfTasks().push(model.getRedoListOfTasks().peek());
            Task toDelete = (Task) model.getRedoListOfTasks().pop();
            try {
                model.deleteTask(toDelete);
            } catch (TaskNotFoundException e) {
                e.printStackTrace();
            }
            
        }
        
        else if (previousUndoCommand.equals("add")){
            model.getListOfTasks().push(model.getRedoListOfTasks().peek());
            Task toAdd = (Task) model.getRedoListOfTasks().pop();
            try {
                model.addTask(toAdd);
            } catch (DuplicateTaskException e) {
                e.printStackTrace();
            }        
        }
        
        else if (previousUndoCommand.equals("done")){
            model.getListOfTasks().push(model.getRedoListOfTasks().peek());
            ReadOnlyTask redoDone = model.getRedoListOfTasks().pop();
            redoDone.setDone();
            model.updateFilteredListToShowAllUndone();
        }
        
        else if (previousUndoCommand.equals("edit")){
            model.getListOfTasks().push(model.getRedoListOfTasks().peek());
            Task redoEdit = (Task)model.getRedoListOfTasks().pop();
         
            try {
                
                model.getEditTaskField().push(model.getRedoEditTaskField().peek());
                
                if (model.getEditTaskField().peek().equals("task")) {
                    model.getEditTaskValue().push(redoEdit.getTaskName().toString()); 
                } else if (model.getEditTaskField().peek().equals("start date")) {
                    model.getEditTaskValue().push(redoEdit.getStartDate().toString());
                } else if (model.getEditTaskField().peek().equals("end date")) {
                    model.getEditTaskValue().push(redoEdit.getEndDate().toString()); 
                } else if (model.getEditTaskField().peek().equals("start time")) {
                    model.getEditTaskValue().push(redoEdit.getStartTime().toString()); 
                } else if (model.getEditTaskField().peek().equals("end time")) {
                    model.getEditTaskValue().push(redoEdit.getEndTime().toString());
                } else if (model.getEditTaskField().peek().equals("priority")) {
                    model.getEditTaskValue().push(redoEdit.getPriority().toString());
                }
                model.editTask(redoEdit, model.getRedoEditTaskField().pop(), model.getRedoEditTaskValue().pop());
            } catch (TaskNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalValueException ive) {
                return new CommandResult(ive.getMessage());
            }
        }

        indicateCorrectCommandExecuted();
        return new CommandResult(String.format(MESSAGE_UNDO_COMMAND_SUCCESS, previousUndoCommand));
    }
    


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

}
//@@author
