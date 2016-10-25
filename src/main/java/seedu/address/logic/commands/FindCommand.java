package seedu.address.logic.commands;

import java.util.Set;


//@@author A0139498J
/**
 * Finds and lists all tasks in task manager whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    
    public static final String TOOL_TIP = "find NAME";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " buy";

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
            model.updateFilteredUndoneTaskList(keywords);
            return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredUndoneTaskList().size()));
        }
    }

}
