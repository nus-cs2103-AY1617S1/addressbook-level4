package seedu.jimi.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in Jimi whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " add water";

    private final Set<String> keywords;

    public FindCommand() {
        keywords = null;
    }
    
    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredAgendaTaskList(keywords);
        model.updateFilteredAgendaEventList(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredAgendaTaskList().size()
                + model.getFilteredAgendaEventList().size()));
    }
    
    @Override
    public boolean isValidCommandWord(String commandWord) {
        for (int i = 1; i <= COMMAND_WORD.length(); i++) {
            if (commandWord.equals(COMMAND_WORD.substring(0, i))) {
                return true;
            }
        }
        return false;
    }
}
