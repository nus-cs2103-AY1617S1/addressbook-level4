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
    public static final String MESSAGE_UNDO_ADD_SUCCESS = "Undo successful. Undo add %1$s";
    public static final String MESSAGE_UNDO_DELETE_SUCCESS = "Undo Successful. Undo delete %1$s";
    public static final String MESSAGE_UNDO_CLEAR_SUCCESS = "Undo clear successful";
    public static final String MESSAGE_UNDO_EDIT_SUCCESS = "Undo successful. Undo edit from %1$s to %2$s";
    public static final String MESSAGE_UNDO_MARK_SUCCESS = "Undo mark sucessful";
    public static final String MESSAGE_UNDO_UNMARK_SUCCESS = "Undo unmark sucessful";
    public static final String MESSAGE_UNDO_COMPLETE_SUCCESS = "Undo complete successful";
    public static final String MESSAGE_UNDO_UNCOMPLETE_SUCCESS = "Undo uncomplete successful";
    public String result;

    @Override
    public CommandResult execute() {

        Stack<InputHistory> history = model.getHistory();
        if (history.isEmpty()) {
            return new CommandResult("No action to undo!");
        }
        InputHistory previous = history.pop();
        switch (previous.getUndoCommand()) {

        case AddCommand.COMMAND_WORD:
            result = executeAdd((InputDeleteHistory) previous);
            updateModel(history);
            return new CommandResult(result);

        case DeleteCommand.COMMAND_WORD:
            result = executeDelete((InputAddHistory) previous);
            updateModel(history);
            return new CommandResult(result);

        case EditCommand.COMMAND_WORD:
            result = executeEdit((InputEditHistory) previous);
            updateModel(history);
            return new CommandResult(result);

        case ClearCommand.COMMAND_WORD:
            result = executeClear((InputClearHistory) previous);
            updateModel(history);
            return new CommandResult(result);

        case UnmarkCommand.COMMAND_WORD:
            result = executeUnmark((InputMarkHistory) previous);
            updateModel(history);
            return new CommandResult(result);

        case MarkCommand.COMMAND_WORD:
            result = executeMark((InputUnmarkHistory) previous);
            updateModel(history);
            return new CommandResult(result);

        case UncompleteCommand.COMMAND_WORD:
            result = executeUncomplete((InputCompleteHistory) previous);
            updateModel(history);
            return new CommandResult(result);

        case CompleteCommand.COMMAND_WORD:
            result = executeComplete((InputUncompleteHistory) previous);
            updateModel(history);
            return new CommandResult(result);

        default:
            assert false;
            return null;
        }
    }

    //========== Private helper methods ==================================================
    
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
        return String.format(MESSAGE_UNDO_DELETE_SUCCESS, previous.getTask().toString());
    }

    public String executeDelete(InputAddHistory previous) {        
        try {
            model.deleteTask(previous.getTask());  
        } catch (Exception e) {
            assert false : "Not Possible";
        }
        return String.format(MESSAGE_UNDO_ADD_SUCCESS, previous.getTask().toString());
    }
    
    private String executeEdit(InputEditHistory previous) {
        try {
            model.editTask(previous.getEditedTask(), previous.getTaskToEdit());
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return String.format(MESSAGE_UNDO_EDIT_SUCCESS, previous.getTaskToEdit().toString(),
                previous.getEditedTask().toString());
    }
    
    private String executeClear(InputClearHistory previous) {
        ReadOnlyMalitio previousModel = new Malitio(previous.getFloatingTask(), previous.getDeadline(), previous.getEvent(), previous.getTag());
        model.resetData(previousModel);
        return MESSAGE_UNDO_CLEAR_SUCCESS;
    }
    
    private String executeUnmark(InputMarkHistory previous) {
        try {
            model.markTask(previous.getTask(), previous.getMarkWhat());
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return MESSAGE_UNDO_MARK_SUCCESS;
    }
    
    private String executeMark(InputUnmarkHistory previous) {
        try {
            model.markTask(previous.getTask(), previous.getMarkWhat());
        } catch (Exception e) {
        assert false : "Not possible";
        }
        return MESSAGE_UNDO_UNMARK_SUCCESS;
    }
    
    private String executeUncomplete(InputCompleteHistory previous) {
       try {
           model.uncompleteTask(previous.getTask());
       } catch (Exception e) {
           assert false : "Not possible";
       }
       return MESSAGE_UNDO_COMPLETE_SUCCESS;
    }
    
    private String executeComplete(InputUncompleteHistory previous) {
        try {
            model.completeTask(previous.getTask());
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return MESSAGE_UNDO_UNCOMPLETE_SUCCESS;
    }

    private void updateModel(Stack<InputHistory> history) {
        showAllListsInModel();
        updateRedoStack(history);
    }
    
    private void showAllListsInModel() {
        model.updateFilteredTaskListToShowAll();
        model.updateFilteredDeadlineListToShowAll();
        model.updateFilteredEventListToShowAll();
    }
    
    private void updateRedoStack(Stack<InputHistory> history) {
        model.getFuture().push(history.pop());
    }

}
