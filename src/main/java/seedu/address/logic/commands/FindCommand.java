package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;

import java.util.Set;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        if (keywords.size() > 0) {
            model.updateFilteredToDoList(keywords);
            return new CommandResult(String.format(Messages.MESSAGE_FIND, model.getFilteredToDoList().size()));
        } else {
            model.updateFilteredListToShowAll();
            return new CommandResult(Messages.MESSAGE_CLEAR_FIND);
        }
    }
}
