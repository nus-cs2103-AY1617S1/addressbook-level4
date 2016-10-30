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
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public String result;

    @Override
    public CommandResult execute() {

        Stack<InputHistory> history = model.getHistory();
        if (history.isEmpty()) {
            return new CommandResult("No action to undo!");
        }
        InputHistory previous = history.pop();
        showAllPanels();
        switch (previous.getUndoCommand()) {

        case AddCommand.COMMAND_WORD:
            result = executeAdd((InputDeleteHistory) previous);
            model.getFuture().push(history.pop());
            return new CommandResult(result);

        case DeleteCommand.COMMAND_WORD:
            result = executeDelete((InputAddHistory) previous);
            model.getFuture().push(history.pop());
            return new CommandResult(result);

        case EditCommand.COMMAND_WORD:
            result = executeEdit((InputEditHistory) previous);
            model.getFuture().push(history.pop());
            return new CommandResult(result);

        case ClearCommand.COMMAND_WORD:
            result = executeClear((InputClearHistory)previous);
            model.getFuture().push(history.pop());
            return new CommandResult(result);

        case UnmarkCommand.COMMAND_WORD:
            result = executeUnmark((InputMarkHistory)previous);
            model.getFuture().push(history.pop());
            return new CommandResult(result);
            
        case MarkCommand.COMMAND_WORD:
            result = executeMark((InputUnmarkHistory)previous);
            model.getFuture().push(history.pop());
            return new CommandResult(result);
            
        case UncompleteCommand.COMMAND_WORD:
            result = executeUncomplete((InputCompleteHistory)previous);
            model.getFuture().push(history.pop());
            return new CommandResult(result);
            
        case CompleteCommand.COMMAND_WORD:
            result = executeComplete((InputUncompleteHistory)previous);
            model.getFuture().push(history.pop());
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
       return "Undo complete successful.";
    }
    
    private String executeComplete(InputUncompleteHistory previous) {
        try {
            model.completeTask(previous.getTask());
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return "Undo uncomplete successful.";
    }
    
    private String executeMark(InputUnmarkHistory previous) {
        try {
            model.markTask(previous.getTask(), previous.getMarkWhat());
        } catch (Exception e) {
        assert false : "Not possible";
        }
        return "Undo unmark successful";
    }

    private String executeUnmark(InputMarkHistory previous) {
        try {
            model.markTask(previous.getTask(), previous.getMarkWhat());
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return "Undo mark successful.";
    }
    

    private String executeClear(InputClearHistory previous) {
        System.out.println(previous.getFloatingTask().getInternalList().isEmpty());
        ReadOnlyMalitio previousModel = new Malitio(previous.getFloatingTask(), previous.getDeadline(), previous.getEvent(), previous.getTag());
        model.resetData(previousModel);
        return "Undo clear successful.";

    }

    private String executeEdit(InputEditHistory previous) {
        try {
            model.editTask(previous.getEditedTask(), previous.getTaskToEdit());
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return ("Undo edit successful. Revert edit from " + previous.getTaskToEdit().toString() + " to "
                + previous.getEditedTask().toString());
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
        return "Undo successful. Undo delete " + previous.getTask().toString();
    }

    public String executeDelete(InputAddHistory previous) {        
        try {
            model.deleteTask(previous.getTask());  
        } catch (Exception e) {
            assert false : "Not Possible";
        }
        return "Successful. Undo add: " + previous.getTask().toString();
    }
    
    /**
     * A method to show all panels on the model.
     */
    private void showAllPanels() {
        model.updateFilteredTaskListToShowAll();
        model.updateFilteredDeadlineListToShowAll();
        model.updateFilteredEventListToShowAll();
    }
    
    /**
     * Updates the redo stack in Malitio
     * @param history
     */
    private void updateRedoStack(InputHistory history) {
        model.getFuture().push(history);
    }
}
