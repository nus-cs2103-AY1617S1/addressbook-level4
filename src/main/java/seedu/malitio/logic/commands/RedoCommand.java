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
            if (previous.getType().equals("floating task")) {
                model.markFloatingTask(previous.getTaskToMark(), previous.getMarkWhat());
                return "Redo mark Floating Task successful";
            } else if (previous.getType().equals("deadline")) {
                model.markDeadline(previous.getDeadlineToMark(), previous.getMarkWhat());
                return "Redo mark Deadline successful";
            } else {
                model.markEvent(previous.getEventToMark(), previous.getMarkWhat());
                return "Redo mark Event successful";
            }
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return "Redo Failed";
    }
    
    private String executeClear(InputClearHistory previous) {
        System.out.println(previous.getFloatingTask().getInternalList().isEmpty());
        ReadOnlyMalitio previousModel = new Malitio(previous.getFloatingTask(), previous.getDeadline(), previous.getEvent(), previous.getTag());
        model.resetData(previousModel);
        return "Redo clear successful.";
        
    }

    private String executeEdit(InputEditHistory previous) {
        try {
            if (previous.getType().equals("floating task")) {
                model.editFloatingTask(previous.getEditedTask(), previous.getTaskToEdit());
                return redoEditSuccessfulMessage(previous.getTaskToEdit().toString(), previous.getEditedTask().toString());
            } else if (previous.getType().equals("deadline")) {
                model.editDeadline(previous.getEditedDeadline(), previous.getDeadlineToEdit());
                return redoEditSuccessfulMessage(previous.getDeadlineToEdit().toString(),previous.getEditedDeadline().toString());
            } else {
                model.editEvent(previous.getEditedEvent(), previous.getEventToEdit());
                return redoEditSuccessfulMessage(previous.getEventToEdit().toString(), previous.getEditedEvent().toString());
            }
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return "Redo Failed";
    }

    public String executeAdd(InputDeleteHistory previous) {
        try {
            if (previous.getType().equals("floating task")) {
                model.addFloatingTaskAtSpecificPlace(previous.getFloatingTask(), previous.getPositionOfFloatingTask());
                return "Redo successful. Redo delete Floating Task: " + previous.getFloatingTask().toString();
            } else if (previous.getType().equals("deadline")) {
                model.addTask(previous.getDeadline());
                return "Redo successful. Redo delete Deadline: " + previous.getDeadline().toString();
            } else {
                model.addTask(previous.getEvent());
                return "Redo successful. Redo delete Event: " + previous.getEvent().toString();
            }
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return "Redo failed";
    }

    public String executeDelete(InputAddHistory previous) {
        try {
            if (previous.getType().equals("floating task")) {
                model.deleteTask(previous.getFloatingTask());
                return "Redo Successful: Redo add Floating Task: " + previous.getFloatingTask().toString();
            } else if (previous.getType().equals("deadline")) {
                model.deleteTask(previous.getDeadline());
                return "Redo Successful. Redo add Deadline: " + previous.getDeadline().toString();
            } else {
                model.deleteTask(previous.getEvent());
                return "Redo successful. Redo add Event: " + previous.getEvent().toString();
            }
        } catch (Exception e) {
            assert false : "Not possible";
        }
        return "Redo Failed";
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
