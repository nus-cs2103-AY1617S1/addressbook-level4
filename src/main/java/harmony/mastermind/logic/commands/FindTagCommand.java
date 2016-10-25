package harmony.mastermind.logic.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.model.tag.Tag;

/**
 * Finds and lists all tasks in task manager whose tag contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */

public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all tasks whose tags contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + " meal finals";

    private final Set<Tag> keywords;

    public FindTagCommand(Set<Tag> keywords) throws IllegalValueException {
        
        this.keywords = keywords;
    }

    /**
     * Returns copy of keywords in this command.
     */
    public Set<Tag> getKeywords() {
        return new HashSet<>(keywords);
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTagTaskList(keywords);
        return new CommandResult(COMMAND_WORD, getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}