package seedu.todoList.logic.commands;

import java.util.EmptyStackException;

//@@author A0144061U
/**
 * Undo the most recent command by the user.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": Undo the latest command. If there is no previous command, nothing will happen.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_SUCCESS = "Undone the latest task.";
    public static final String MESSAGE_UNDO_FAIL = "There was no undoable command made before.";

    public UndoCommand() {}

    @Override
    public CommandResult execute() {
        try {
            model.undoLatestCommand();
        } catch (EmptyStackException e) {
        	return new CommandResult(MESSAGE_UNDO_FAIL);
        }

        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }

}
