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

        switch (previous.getUndoCommand()) {

        case AddCommand.COMMAND_WORD:
            result = executeAdd((InputDeleteHistory) previous);
            updateMalitio(history);
            return new CommandResult(result);

        case DeleteCommand.COMMAND_WORD:
            result = executeDelete((InputAddHistory) previous);
            updateMalitio(history);
            return new CommandResult(result);

        case EditCommand.COMMAND_WORD:
            result = executeEdit((InputEditHistory) previous);
            updateMalitio(history);
            return new CommandResult(result);

        case ClearCommand.COMMAND_WORD:
            result = executeClear((InputClearHistory)previous);
            updateMalitio(history);
            return new CommandResult(result);

        case MarkCommand.COMMAND_WORD:
            result = executeMark((InputMarkHistory)previous);
            updateMalitio(history);
            return new CommandResult(result);

        case UnmarkCommand.COMMAND_WORD:
            result = executeMark((InputMarkHistory)previous);
            updateMalitio(history);
            return new CommandResult(result);

        }
        return null;
    }

    private String executeMark(InputMarkHistory previous) {
        try {
            if (previous.getType().equals("floating task")) {
                model.markFloatingTask(previous.getTaskToMark(), previous.getMarkWhat());
                return "Undo mark successful";
            } else if (previous.getType().equals("deadline")) {
                model.markDeadline(previous.getDeadlineToMark(), previous.getMarkWhat());
                return "Undo mark successful";
            } else {
                model.markEvent(previous.getEventToMark(), previous.getMarkWhat());
                return "Undo mark successful";
            }
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return "Undo Failed";
    }

    private String executeClear(InputClearHistory previous) {
        System.out.println(previous.getFloatingTask().getInternalList().isEmpty());
        ReadOnlyMalitio previousModel = new Malitio(previous.getFloatingTask(), previous.getDeadline(), previous.getEvent(), previous.getTag());
        model.resetData(previousModel);
        return "Undo clear successful.";

    }

    private String executeEdit(InputEditHistory previous) {
        try {
            if (previous.getType().equals("floating task")) {
                model.editFloatingTask(previous.getEditedTask(), previous.getTaskToEdit());
                return ("Undo edit successful. Revert edit from " + previous.getTaskToEdit().toString() + " to "
                        + previous.getEditedTask().toString());
            } else if (previous.getType().equals("deadline")) {
                model.editDeadline(previous.getEditedDeadline(), previous.getDeadlineToEdit());
                return ("Undo edit successful. Revert edit from " + previous.getDeadlineToEdit().toString() + " to "
                        + previous.getEditedDeadline().toString());
            } else {
                model.editEvent(previous.getEditedEvent(), previous.getEventToEdit());
                return ("Undo edit successful. Revert edit from " + previous.getEventToEdit().toString() + " to "
                        + previous.getEditedEvent().toString());
            }
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return "Undo Failed";
    }

    public String executeAdd(InputDeleteHistory previous) {
        try {
            if (previous.getType().equals("floating task")) {
                model.addFloatingTaskAtSpecificPlace(previous.getFloatingTask(), previous.getPositionOfFloatingTask());
                return "Successful. Undo delete Floating Task: " + previous.getFloatingTask().toString();
            } else if (previous.getType().equals("deadline")) {
                model.addTask(previous.getDeadline());
                return "Successful. Undo delete Deadline: " + previous.getDeadline().toString();
            } else {
                model.addTask(previous.getEvent());
                return "Successful. Undo delete Event: " + previous.getEvent().toString();
            }
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return "Undo Failed";
    }

    public String executeDelete(InputAddHistory previous) {
        try {
            if (previous.getType().equals("floating task")) {
                model.deleteTask(previous.getFloatingTask());
                return "Successful: Undo add Floating Task: " + previous.getFloatingTask().toString();
            } else if (previous.getType().equals("deadline")) {
                model.deleteTask(previous.getDeadline());
                return "Successful. Undo add Deadline: " + previous.getDeadline().toString();
            } else {
                model.deleteTask(previous.getEvent());
                return "Successful. Undo add Event: " + previous.getEvent().toString();
            }
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return "Undo Failed";
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
