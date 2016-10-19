package seedu.taskitty.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in task manager whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class ViewDoneCommand extends Command {

    public static final String COMMAND_WORD = "viewdone";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": view all previously marked as done tasks. "
            + "Example: " + COMMAND_WORD;


    public ViewDoneCommand() {
    }

    @Override
    public CommandResult execute() {

        model.updateFilteredDoneList();
        return new CommandResult(getMessageForTaskListShownSummary(model.getTaskList().size()));
    }

    @Override
    public void saveStateIfNeeded(String commandText) {
        model.saveState(commandText);
    }

}
