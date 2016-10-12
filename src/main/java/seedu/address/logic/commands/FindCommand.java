package seedu.address.logic.commands;

import java.util.Set;

import seedu.address.logic.commands.models.FindCommandModel;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final FindCommandModel commandModel;

    public FindCommand(FindCommandModel commandModel) {
        assert (commandModel != null);
        this.commandModel = commandModel;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(commandModel.getKeywords());
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
    
    @Override
    protected boolean canUndo() {
        return false;
    }

    /**
     * Redo the find command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        // nothing required to be done
        return true;
    }

    /**
     * Undo the find command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        // nothing required to be done
        return true;
    }

}
