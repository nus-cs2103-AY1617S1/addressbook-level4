package seedu.taskitty.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in task manager whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD
            + " [keyword] [more keywords]...";
    public static final String MESSAGE_USAGE = "This command helps you find a task from TasKitty, Meow!";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getTaskList().size()));
    }

}
