package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.AgendaTimeRangeChangedEvent;
import seedu.address.logic.UndoRedoManager.Context;
import seedu.address.logic.UndoRedoManager.NoAvailableCommandException;

//@@author A0147967J
/**
 * Undos the previous undoable operation.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "u";
    public static final String MESSAGE_SUCCESS = "Undo successfully.";
    public static final String MESSAGE_FAIL = "No command to undo.";

    public UndoCommand() {
    }

    @Override
    public CommandResult execute() {

        try {
            Context contextToUndo = urManager.getContextToUndo();
            
            model.resetData(contextToUndo.getData()); // resets the data
            
            if(contextToUndo.getCommand() instanceof FindCommand 
               || contextToUndo.getCommand() instanceof ListCommand) {
                // resets the filtered list if needed
                model.updateFilteredTaskList(contextToUndo.getPreviousExpression());
            }
            
            if(contextToUndo.getCommand() instanceof ViewCommand) {
                //resets the agenda if needed
                EventsCenter.getInstance().post(new AgendaTimeRangeChangedEvent(contextToUndo.previousTime, model.getTaskMaster().getTaskOccurrenceList()));
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (NoAvailableCommandException nace) {
            indicateAttemptToExecuteFailedCommand();
            return new CommandResult(MESSAGE_FAIL);
        }
    }

}
