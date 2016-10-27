package seedu.taskscheduler.logic.commands;

import java.util.Set;

//@@author A0148145E

/**
 * Lists all tasks in the Task Scheduler to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    private Set<String> keywords;
    
    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        keywords = CommandHistory.getFilteredKeyWords();
        CommandHistory.setFilteredKeyWords(null);
        CommandHistory.addExecutedCommand(this);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult revert() {
        model.updateFilteredTaskList(keywords);
        CommandHistory.setFilteredKeyWords(keywords);
        CommandHistory.addRevertedCommand(this);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredTaskList().size()));
    }
}
