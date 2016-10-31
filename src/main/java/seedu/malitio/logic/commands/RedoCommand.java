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
    public static final String MESSAGE_REDO_ADD_SUCCESS = "Redo successful. Redo add %1$s";
    public static final String MESSAGE_REDO_DELETE_SUCCESS = "Redo Successful. Redo delete %1$s";
    public static final String MESSAGE_REDO_CLEAR_SUCCESS = "Redo clear successful";
    public static final String MESSAGE_REDO_EDIT_SUCCESS = "Redo successful. Redo edit from %1$s to %2$s";
    public static final String MESSAGE_REDO_MARK_SUCCESS = "Redo mark sucessful";
    public static final String MESSAGE_REDO_UNMARK_SUCCESS = "Redo unmark sucessful";
    public static final String MESSAGE_REDO_COMPLETE_SUCCESS = "Redo complete successful";
    public static final String MESSAGE_REDO_UNCOMPLETE_SUCCESS = "Redo uncomplete successful";
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
            result = executeClear((InputClearHistory) previous);
            return new CommandResult(result);

        case UnmarkCommand.COMMAND_WORD:
            result = executeUnmark((InputMarkHistory) previous);
            return new CommandResult(result);

        case MarkCommand.COMMAND_WORD:
            result = executeMark((InputUnmarkHistory) previous);
            return new CommandResult(result);

        case UncompleteCommand.COMMAND_WORD:
            result = executeUncomplete((InputCompleteHistory) previous);
            return new CommandResult(result);

        case CompleteCommand.COMMAND_WORD:
            result = executeComplete((InputUncompleteHistory) previous);
            return new CommandResult(result);

        default:
            assert false;
            return null;
        }
    }
    
    //========== Private helper methods ==================================================
    
    public String executeAdd(InputDeleteHistory previous) {
        try {
            if (isFloatingTask(previous)) {
                model.addFloatingTaskAtSpecificPlace(previous.getTask(), previous.getPositionOfFloatingTask());
            } else {
                model.addTask(previous.getTask());
            }
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return String.format(MESSAGE_REDO_ADD_SUCCESS, previous.getTask().toString());
    }

    public String executeDelete(InputAddHistory previous) {
        try {
            model.deleteTask(previous.getTask());
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return String.format(MESSAGE_REDO_DELETE_SUCCESS, previous.getTask().toString());
    }

    private String executeEdit(InputEditHistory previous) {
        try {
            model.editTask(previous.getEditedTask(), previous.getTaskToEdit());
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return String.format(MESSAGE_REDO_EDIT_SUCCESS, previous.getTaskToEdit().toString(), previous.getEditedTask().toString());
    }
    
    private String executeClear(InputClearHistory previous) {
        ReadOnlyMalitio previousModel = new Malitio(previous.getFloatingTask(), previous.getDeadline(),
                previous.getEvent(), previous.getTag());
        model.resetData(previousModel);
        return MESSAGE_REDO_CLEAR_SUCCESS;
    }
    
    private String executeUnmark(InputMarkHistory previous) {
        try {
            model.markTask(previous.getTask(), false);
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return MESSAGE_REDO_UNMARK_SUCCESS;
    }
    
    private String executeMark(InputUnmarkHistory previous) {
        try {
            model.markTask(previous.getTask(), true);
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return MESSAGE_REDO_MARK_SUCCESS;
    }
    
    private String executeUncomplete(InputCompleteHistory previous) {
        try {
            model.uncompleteTask(previous.getTask());
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return MESSAGE_REDO_UNCOMPLETE_SUCCESS;
    }

    private String executeComplete(InputUncompleteHistory previous) {
        try {
            model.completeTask(previous.getTask());
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return MESSAGE_REDO_COMPLETE_SUCCESS;
    }
    
    private boolean isFloatingTask(InputDeleteHistory previous) {
        return previous.getPositionOfFloatingTask() != -1;
    }
}
