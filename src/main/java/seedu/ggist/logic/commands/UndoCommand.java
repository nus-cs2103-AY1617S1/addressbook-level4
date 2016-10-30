package seedu.ggist.logic.commands;
//@@author A0138420N

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

    public static final String MESSAGE_UNDO_COMMAND_SUCCESS = "Undo previous command: %1$s";

    @Override
    public CommandResult execute() {
        if (model.getListOfCommands().empty() ==true) {
            return new CommandResult(Messages.MESSAGE_NO_PREVIOUS_COMMAND);
        }
        
        model.getRedoListOfCommands().push(model.getListOfCommands().peek());
        String previousCommand = model.getListOfCommands().pop();
        if (previousCommand.equals("add")){
            model.getRedoListOfTasks().push(model.getListOfTasks().peek());
            Task toDelete = (Task)model.getListOfTasks().pop();
            try {
                model.deleteTask(toDelete);
            } catch (TaskNotFoundException e) {
                e.printStackTrace();
            }
            
        }
        
        else if (previousCommand.equals("delete")){
            model.getRedoListOfTasks().push(model.getListOfTasks().peek());
            Task toAdd = (Task) model.getListOfTasks().pop();
            try {
                model.addTask(toAdd);
            } catch (DuplicateTaskException e) {
                e.printStackTrace();
            }        
        }
        
        else if (previousCommand.equals("done")){
            model.getRedoListOfTasks().push(model.getListOfTasks().peek());
            Task undoDone = (Task)model.getListOfTasks().pop();
            undoDone.setUndone();
        }
        
        else if (previousCommand.equals("edit")){
            model.getRedoListOfTasks().push(model.getListOfTasks().peek());
            Task undoEdit = (Task)model.getListOfTasks().pop();
         
            try {
                
                model.getRedoEditTaskField().push(model.getEditTaskField().peek());
                
                if (model.getRedoEditTaskField().peek().equals("task")){
                    model.getRedoEditTaskValue().push(undoEdit.getTaskName().toString()); 
                } else if (model.getRedoEditTaskField().peek().equals("start date")){
                    model.getRedoEditTaskValue().push(undoEdit.getStartDate().toString());
                } else if (model.getRedoEditTaskField().peek().equals("end date")){
                    model.getRedoEditTaskValue().push(undoEdit.getEndDate().toString()); 
                } else if (model.getRedoEditTaskField().peek().equals("start time")){
                    model.getRedoEditTaskValue().push(undoEdit.getStartTime().toString()); 
                } else if (model.getRedoEditTaskField().peek().equals("end time")){
                    model.getRedoEditTaskValue().push(undoEdit.getEndTime().toString());
                } else if (model.getRedoEditTaskField().peek().equals("priority")){
                    model.getRedoEditTaskValue().push(undoEdit.getPriority().toString());
                }
                model.editTask(undoEdit, model.getEditTaskField().pop(), model.getEditTaskValue().pop());
            } catch (TaskNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalValueException ive) {
                new CommandResult(ive.getMessage());
            }
        }
        model.updateListing();
        indicateCorrectCommandExecuted();
        return new CommandResult(String.format(MESSAGE_UNDO_COMMAND_SUCCESS, previousCommand));
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

}
//@@author
