package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Set;

import seedu.address.model.TaskManager;
import seedu.address.model.item.Task;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    
    public static final String TOOL_TIP = "find NAME";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        if (model.isCurrentListDoneList()) {
            model.updateFilteredDoneTaskList(keywords);
            return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredDoneTaskList().size()));
        } else {
            model.updateFilteredFloatingTaskList(keywords);
            return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredUndoneTaskList().size()));
        }
    }

}
