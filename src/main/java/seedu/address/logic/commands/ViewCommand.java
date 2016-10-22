package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.AgendaTimeRangeChangedEvent;
import seedu.address.model.task.TaskDate;

/**
 * Selects a task identified using it's last displayed index from the task list.
 */
public class ViewCommand extends Command {

    public final TaskDate inputDate;

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": View the agenda of the week specified by input date.\n"
            + "Parameters: DATE_TIME \n"
            + "Example: " + COMMAND_WORD + " next WednesDay";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Agenda Updated to Week specified by: %1$s";

    public ViewCommand(TaskDate inputDate) {
        this.inputDate = inputDate;
    }

    @Override
    public CommandResult execute() {
    	
    	//assert false : "Select does not support recurring tasks"; // Should use TaskComponent instead of task

        EventsCenter.getInstance().post(new AgendaTimeRangeChangedEvent(inputDate, model.getFilteredTaskComponentList()));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, inputDate.getFormattedDate()));

    }

}
