package seedu.address.logic.commands;

import java.text.SimpleDateFormat;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.AgendaTimeRangeChangedEvent;
import seedu.address.model.task.TaskDate;

//@@author A0147967J
/**
 * Views the agenda for the week specified by (contains) input date.
 */
public class ViewCommand extends Command {

    public final TaskDate inputDate;
    
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy MMM dd, EEE");

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": View the agenda of the week specified by input date.\n"
            + "Parameters: DATE_TIME \n"
            + "Example: " + COMMAND_WORD + " next WednesDay";

    public static final String MESSAGE_UPDATE_AGENDA_SUCCESS = "Agenda Updated to Week specified by: %1$s";

    public ViewCommand(TaskDate inputDate) {
        this.inputDate = inputDate;
    }

    @Override
    public CommandResult execute() {

        EventsCenter.getInstance().post(new AgendaTimeRangeChangedEvent(inputDate, model.getTaskMaster().getTaskComponentList()));
        return new CommandResult(String.format(MESSAGE_UPDATE_AGENDA_SUCCESS, formatter.format(inputDate.getDate())));

    }

}
