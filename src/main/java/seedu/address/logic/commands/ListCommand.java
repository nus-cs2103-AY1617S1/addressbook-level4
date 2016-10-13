package seedu.address.logic.commands;

import seedu.address.logic.commands.models.ListCommandModel;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the tasks in the task list.\n"
            + "Parameters: [t/LIST_TYPE]\n"
            + "Example: " + COMMAND_WORD + " t/Archived";
    
    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    private final ListCommandModel commandModel;
    
    public ListCommand(ListCommandModel commandModel) {
        assert commandModel != null;
        this.commandModel = commandModel;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    @Override
    protected boolean canUndo() {
        return false;
    }

    /**
     * Redo the list command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        // nothing required to be done
        return true;
    }

    
    /**
     * Undo the list command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        // nothing required to be done
        return true;
    }
}
