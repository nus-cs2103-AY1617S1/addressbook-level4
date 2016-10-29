package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Datetime;

/**
 * Populate the list of tasks happening on the selected DATE 
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the list of tasks happening on the selected DATE \n"
            + "Parameters: DATE \n"
            + "Example: " + COMMAND_WORD + " today";

    private final Datetime datetime;

    public ViewCommand(String date) throws IllegalValueException {
		this.datetime = new Datetime(date);
    }

    @Override
    public CommandResult execute() {
    	Set<String> date = new HashSet<>();
    	date.add(datetime.getDateString());
        model.updateFilteredTaskListByKeywords(date);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredDatedTaskList().size()+model.getFilteredUndatedTaskList().size()));
    }
}
