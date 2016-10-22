package seedu.malitio.logic.commands;

import java.util.Stack;

import seedu.malitio.model.history.InputAddHistory;
import seedu.malitio.model.history.InputDeleteHistory;
import seedu.malitio.model.history.InputEditHistory;
import seedu.malitio.model.history.InputHistory;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineNotFoundException;
import seedu.malitio.model.task.UniqueDeadlineList.DuplicateDeadlineException;
import seedu.malitio.model.task.UniqueEventList.DuplicateEventException;
import seedu.malitio.model.task.UniqueEventList.EventNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList.DuplicateFloatingTaskException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    @Override
    public CommandResult execute() {

        Stack<InputHistory> history = model.getHistory();
        if (history.isEmpty()) {
            return new CommandResult("No action to undo!");
        }
        InputHistory previous = history.pop();
        
        switch (previous.getUndoCommand()) {

        case AddCommand.COMMAND_WORD:
            executeAdd((InputDeleteHistory) previous);
            history.pop();
            return new CommandResult("Undo Successful");

        case DeleteCommand.COMMAND_WORD:
            executeDelete((InputAddHistory) previous);
            history.pop();
            return new CommandResult("Undo Successful");

        case EditCommand.COMMAND_WORD:
            executeEdit((InputEditHistory) previous);
            history.pop();
            return new CommandResult("Undo Successful");

        }
        return null;
    }

    private void executeEdit(InputEditHistory previous) {
        if (previous.getType().equals("floating task")) {
            try {
                model.editFloatingTask(previous.getEditedTask(), previous.getTaskToEdit());
            } catch (DuplicateFloatingTaskException e) {
                assert false : "not possible";
            } catch (FloatingTaskNotFoundException e) {
                assert false : "not possible";
            }
        }
        
        else if (previous.getType().equals("deadline")) {
            try {
                model.editDeadline(previous.getEditedDeadline(), previous.getDeadlineToEdit());
            } catch (DuplicateDeadlineException e) {
                assert false : "not possible";
            } catch (DeadlineNotFoundException e) {
                assert false : "not possible";
            }
        }
        else {
            try {
                model.editEvent(previous.getEditedEvent(), previous.getEventToEdit());
            } catch (DuplicateEventException e) {
                assert false : "not possible";
            } catch (EventNotFoundException e) {
                assert false : "not possible";
            }
        }
        
        
    }

    public void executeAdd(InputDeleteHistory previous) {

        if (previous.getType().equals("floating task")) {
            try {
                model.addFloatingTaskAtSpecificPlace(previous.getFloatingTask(), previous.getPositionOfFloatingTask());
            } catch (DuplicateFloatingTaskException e) {
                assert false : "not possible";
            }
        } else if (previous.getType().equals("deadline")) {
            try {
                model.addDeadline(previous.getDeadline());
            } catch (DuplicateDeadlineException e) {
                assert false : "not possible";
            }
        } else {
            try {
                model.addEvent(previous.getEvent());
            } catch (DuplicateEventException e) {
                assert false : "not possible";
            }
        }
    }

    public void executeDelete(InputAddHistory previous) {

        if (previous.getType().equals("floating task")) {
            try {
                model.deleteTask(previous.getFloatingTask());
            } catch (FloatingTaskNotFoundException e) {
                assert false : "not possible";
            }
        } else if (previous.getType().equals("deadline")) {
            try {
                model.deleteTask(previous.getDeadline());
            } catch (DeadlineNotFoundException e) {
                assert false : "not possible";
            }
        } else {
            try {
                model.deleteTask(previous.getEvent());
            } catch (EventNotFoundException e) {
                assert false : "not possible";
            }
        }
    }
}
