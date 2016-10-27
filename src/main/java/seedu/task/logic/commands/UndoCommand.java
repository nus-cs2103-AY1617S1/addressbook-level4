//@@ author A0147969E
package seedu.task.logic.commands;


import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.LogicManager;

/**
 * Undo the last operation.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the last add/delete/addTag/deleteTag/update Command.\n"
            + "Example: " + COMMAND_WORD;

    public UndoCommand() {}

    @Override
    public CommandResult execute() {
    	try {
            return LogicManager.undo();
        } catch (IllegalValueException e) {
            return new CommandResult(e.getMessage());
        }
    }
}
