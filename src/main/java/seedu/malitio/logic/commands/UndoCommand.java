package seedu.malitio.logic.commands;

import java.util.Stack;

import seedu.malitio.model.Malitio;
import seedu.malitio.model.ReadOnlyMalitio;
import seedu.malitio.model.history.InputAddHistory;
import seedu.malitio.model.history.InputClearHistory;
import seedu.malitio.model.history.InputDeleteHistory;
import seedu.malitio.model.history.InputEditHistory;
import seedu.malitio.model.history.InputHistory;
import seedu.malitio.model.history.InputMarkHistory;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineMarkedException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineNotFoundException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineUnmarkedException;
import seedu.malitio.model.task.UniqueDeadlineList.DuplicateDeadlineException;
import seedu.malitio.model.task.UniqueEventList.DuplicateEventException;
import seedu.malitio.model.task.UniqueEventList.EventMarkedException;
import seedu.malitio.model.task.UniqueEventList.EventNotFoundException;
import seedu.malitio.model.task.UniqueEventList.EventUnmarkedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.DuplicateFloatingTaskException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskMarkedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskUnmarkedException;

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
        updateMalitio(history);
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

        case MarkCommand.COMMAND_WORD:
            result = executeMark((InputMarkHistory)previous);
            return new CommandResult(result);

        case UnmarkCommand.COMMAND_WORD:
            result = executeMark((InputMarkHistory)previous);
            return new CommandResult(result);

        }
        return null;
    }

    private String executeMark(InputMarkHistory previous) {
        try {
            model.markTask(previous.getTaskToMark(), previous.getMarkWhat());
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
        } catch (FloatingTaskNotFoundException | DeadlineNotFoundException | EventNotFoundException e) {
            assert false : "Not Possible";
        }
        return "Successful. Undo add: " + previous.getTask().toString();
    }
    
    /**
     * Updates Malitio
     * @param history
     */
    private void updateMalitio(Stack<InputHistory> history) {
        updateRedoStack(history);
        showAllPanels();
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
    private void updateRedoStack(Stack<InputHistory> history) {
        model.getFuture().push(history.pop());
    }
}
