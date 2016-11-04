package seedu.stask.logic.commands;

import seedu.stask.commons.exceptions.IllegalValueException;
import seedu.stask.model.task.Datetime;

//@@author A0143884W
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
        model.updateFilteredTaskListByDate(datetime.getStart());
        return new CommandResult("Viewing tasks for " + datetime.getDateString());
    }
}
