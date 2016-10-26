package seedu.todoList.logic.commands;

import java.util.EmptyStackException;

/**
 * Selects a task identified using it's last displayed index from the TodoList.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": Undo the latest command. If there is no previous command, nothing will happen.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_SUCCESS = "Undone the latest task";

    public UndoCommand() {}

    @Override
    public CommandResult execute() {
        try {
            model.undoLatestCommand();
        } catch (EmptyStackException e) {
            assert false : "There was no undoable command made before";
        }

        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }

}
