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
import seedu.malitio.model.task.UniqueEventList.EventNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList.DuplicateFloatingTaskException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskMarkedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskUnmarkedException;

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
            model.getFuture().push(history.pop());
            showAllPanels();
            return new CommandResult(result);

        case DeleteCommand.COMMAND_WORD:
            result = executeDelete((InputAddHistory) previous);
            model.getFuture().push(history.pop());
            showAllPanels();
            return new CommandResult(result);

        case EditCommand.COMMAND_WORD:
            result = executeEdit((InputEditHistory) previous);
            model.getFuture().push(history.pop());
            showAllPanels();
            return new CommandResult(result);
        
        case ClearCommand.COMMAND_WORD:
            result = executeClear((InputClearHistory)previous);
            model.getFuture().push(history.pop());
            showAllPanels();
            return new CommandResult(result);
            
        case MarkCommand.COMMAND_WORD:
            result = executeMark((InputMarkHistory)previous);
            model.getFuture().push(history.pop());
            showAllPanels();
            return new CommandResult(result);

        }
        return null;
    }

    private String executeMark(InputMarkHistory previous) {
        if (previous.getType().equals("floating task")) {
            try {
                model.markFloatingTask(previous.getTaskToMark(), previous.getMarkWhat());
                return "Undo mark successful";
            } catch (FloatingTaskNotFoundException | FloatingTaskMarkedException | FloatingTaskUnmarkedException e) {
                assert false : "not possible";
            }
        }
        else {
            try {
                model.markDeadline(previous.getDeadlineToMark(), previous.getMarkWhat());
                return "Undo mark successful";
            } catch (DeadlineNotFoundException | DeadlineMarkedException | DeadlineUnmarkedException e) {
                assert false: "not possible";                    
            }
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
        if (previous.getType().equals("floating task")) {
            try {
                model.editFloatingTask(previous.getEditedTask(), previous.getTaskToEdit());
                return ("Undo edit successful. Revert edit from" + previous.getTaskToEdit().toString() + " to "+ previous.getEditedTask().toString());
            } catch (DuplicateFloatingTaskException e) {
                assert false : "not possible";
            } catch (FloatingTaskNotFoundException e) {
                assert false : "not possible";
            }
        }
        
        else if (previous.getType().equals("deadline")) {
            try {
                model.editDeadline(previous.getEditedDeadline(), previous.getDeadlineToEdit());
                return ("Undo edit successful. Revert edit from" + previous.getDeadlineToEdit().toString() + " to "+ previous.getEditedDeadline().toString());
            } catch (DuplicateDeadlineException e) {
                assert false : "not possible";
            } catch (DeadlineNotFoundException e) {
                assert false : "not possible";
            }
        }
        else {
            try {
                model.editEvent(previous.getEditedEvent(), previous.getEventToEdit());
                return ("Undo edit successful. Revert edit from" + previous.getEventToEdit().toString() + " to "+ previous.getEditedEvent().toString());
            } catch (DuplicateEventException e) {
                assert false : "not possible";
            } catch (EventNotFoundException e) {
                assert false : "not possible";
            }
        }
        return "Undo Failed";
    }

    public String executeAdd(InputDeleteHistory previous) {

        if (previous.getType().equals("floating task")) {
            try {
                model.addFloatingTaskAtSpecificPlace(previous.getFloatingTask(), previous.getPositionOfFloatingTask());
                return "Successful. Undo delete Floating Task: " + previous.getFloatingTask().toString();
            } catch (DuplicateFloatingTaskException e) {
                assert false : "not possible";
            }
        } else if (previous.getType().equals("deadline")) {
            try {
                model.addDeadline(previous.getDeadline());
                return "Successful. Undo delete Deadline: " + previous.getDeadline().toString();
            } catch (DuplicateDeadlineException e) {
                assert false : "not possible";
            }
        } else {
            try {
                model.addEvent(previous.getEvent());
                return "Successful. Undo delete Event: " + previous.getEvent().toString();
            } catch (DuplicateEventException e) {
                assert false : "not possible";
            }
        }
        return "Undo failed";
    }

    public String executeDelete(InputAddHistory previous) {

        if (previous.getType().equals("floating task")) {
            try {
                model.deleteTask(previous.getFloatingTask());
                return "Successful: Undo add Floating Task: " + previous.getFloatingTask().toString();
            } catch (FloatingTaskNotFoundException e) {
                assert false : "not possible";
            }
        } else if (previous.getType().equals("deadline")) {
            try {
                model.deleteTask(previous.getDeadline());
                return "Successful. Undo add Deadline: " + previous.getDeadline().toString();
            } catch (DeadlineNotFoundException e) {
                assert false : "not possible";
            }
        } else {
            try {
                model.deleteTask(previous.getEvent());
                return "Successful. Undo add Event: " + previous.getEvent().toString();
            } catch (EventNotFoundException e) {
                assert false : "not possible";
            }
        }
        return "Undo Failed";
    }
    
    /**
     * A method to show all panels on the model.
     */
    private void showAllPanels() {
        model.updateFilteredTaskListToShowAll();
        model.updateFilteredDeadlineListToShowAll();
        model.updateFilteredEventListToShowAll();
    }
}
