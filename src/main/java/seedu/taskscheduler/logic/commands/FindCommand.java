package seedu.taskscheduler.logic.commands;

import java.util.Set;

//@@author A0148145E

/**
 * Finds and lists all tasks in task scheduler whose task name contains any of the argument keywords.
 * Keyword matching is not case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all task whose task names contain any of "
            + "the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD...\n"
            + "Example: " + COMMAND_WORD + " CS2103 Tutorial";

    private Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords);
        Set<String> temp = CommandHistory.getFilteredKeyWords();
        CommandHistory.setFilteredKeyWords(keywords);
        keywords = temp;
        CommandHistory.addExecutedCommand(this);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public CommandResult revert() {
        String message;
        if (keywords == null) {
            model.updateFilteredListToShowAll();
            message = ListCommand.MESSAGE_SUCCESS;
        } else {
            model.updateFilteredTaskList(keywords);
            message = getMessageForPersonListShownSummary(model.getFilteredTaskList().size());
        }
        Set<String> temp = CommandHistory.getFilteredKeyWords();
        CommandHistory.setFilteredKeyWords(keywords);
        keywords = temp;
        CommandHistory.addRevertedCommand(this);
        return new CommandResult(message);
    }

}
