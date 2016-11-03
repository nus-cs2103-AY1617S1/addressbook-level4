//@@author A0141052Y
package seedu.task.logic.commands;

import seedu.task.model.Model;

/**
 * Lists all tasks in the task list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists a subset of the tasks in the list "
            + "Parameters: [all|pinned|pending|completed|overdue]...\n"
            + "Example: " + COMMAND_WORD
            + " ALL";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    
    private final Model.FilterType listFilter;

    public ListCommand(Model.FilterType filter) {
        this.listFilter = filter;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredList(listFilter);
        return new CommandResult(true, MESSAGE_SUCCESS);
    }
}
