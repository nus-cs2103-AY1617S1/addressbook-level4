package seedu.malitio.logic.commands;

import java.util.Stack;

import seedu.malitio.model.Malitio;
import seedu.malitio.model.ReadOnlyMalitio;
import seedu.malitio.model.history.InputAddHistory;
import seedu.malitio.model.history.InputClearHistory;
import seedu.malitio.model.history.InputCompleteHistory;
import seedu.malitio.model.history.InputDeleteHistory;
import seedu.malitio.model.history.InputEditHistory;
import seedu.malitio.model.history.InputHistory;
import seedu.malitio.model.history.InputMarkHistory;
import seedu.malitio.model.history.InputUncompleteHistory;
import seedu.malitio.model.history.InputUnmarkHistory;

//@@author A0129595N
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public String result;
    
    @Override
    public CommandResult execute() {

        Stack<InputHistory> future = model.getFuture();
        if (future.isEmpty()) {
            return new CommandResult("No action to redo!");
        }
        InputHistory previous = future.pop();
        
        switch (previous.getUndoCommand()) {

        case AddCommand.COMMAND_WORD:
            result = executeAdd((InputDeleteHistory) previous);
            return new CommandResult(result);

        case DeleteCommand.COMMAND_WORD:
            result = executeDelete((InputAddHistory) previous);
            return new CommandResult(result);

        case EditCommand.COMMAND_WORD:
            result = executeEdit((InputEditHistory) previous);
            return new CommandResult(result);
        
        case ClearCommand.COMMAND_WORD:
            result = executeClear((InputClearHistory)previous);
            return new CommandResult(result);
            
        case UnmarkCommand.COMMAND_WORD:
            result = executeUnmark((InputMarkHistory)previous);
            return new CommandResult(result);
            
        case MarkCommand.COMMAND_WORD:
            result = executeMark((InputUnmarkHistory)previous);
            return new CommandResult(result);
            
        case UncompleteCommand.COMMAND_WORD:
            result = executeUncomplete((InputCompleteHistory)previous);
            return new CommandResult(result);
            
        case CompleteCommand.COMMAND_WORD:
            result = executeComplete((InputUncompleteHistory)previous);
            return new CommandResult(result);
        }
        return null;
    }

    private String executeUncomplete(InputCompleteHistory previous) {
        try {
            model.uncompleteTask(previous.getTask());
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return "Redo uncomplete successful.";
     }
     
     private String executeComplete(InputUncompleteHistory previous) {
         try {
             model.completeTask(previous.getTask());
         } catch (Exception e) {
             assert false : "Not possible";
         }
         return "Redo complete successful.";
     }
     
     private String executeMark(InputUnmarkHistory previous) {
         try {
             model.markTask(previous.getTask(), true);
         } catch (Exception e) {
             assert false : "Not possible";
         }
         return "Redo mark sucessful";
     }
    
    private String executeUnmark(InputMarkHistory previous) {
        try {
            model.markTask(previous.getTask(), false);
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return "Redo unmark sucessful";
    }
    
    private String executeClear(InputClearHistory previous) {
        System.out.println(previous.getFloatingTask().getInternalList().isEmpty());
        ReadOnlyMalitio previousModel = new Malitio(previous.getFloatingTask(), previous.getDeadline(), previous.getEvent(), previous.getTag());
        model.resetData(previousModel);
        return "Redo clear successful.";
        
    }

    private String executeEdit(InputEditHistory previous) {
        try {
            model.editTask(previous.getEditedTask(), previous.getTaskToEdit());
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return redoEditSuccessfulMessage(previous.getTaskToEdit().toString(), previous.getEditedTask().toString());
    }

    public String executeAdd(InputDeleteHistory previous) {
        try {
            if (previous.getPositionOfFloatingTask() != -1) {
                model.addFloatingTaskAtSpecificPlace(previous.getTask(), previous.getPositionOfFloatingTask());
            } else {
                model.addTask(previous.getTask());
            }
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return "Redo successful. Redo add " + previous.getTask().toString();
    }

    public String executeDelete(InputAddHistory previous) {
        try {
            model.deleteTask(previous.getTask());
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return "Redo Successful: Redo delete" + previous.getTask().toString();
    }
    
    /**
     * @param beforeEdit task to be edited
     * @param afterEdit edited task
     * @return Message to indicate successful redo of edit
     */
    private String redoEditSuccessfulMessage(String beforeEdit, String afterEdit) {
        return "Redo successful. Redo edit from" + beforeEdit + " to "
                + afterEdit;
    }
}
