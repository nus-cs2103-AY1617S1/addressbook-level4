package seedu.taskitty.logic.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import seedu.taskitty.model.task.TaskDate;

/**
 * Finds all events for the specified date, deadlines up to the specified date, and all todo tasks.
 * If no date is specified, all events for today, all deadlines and todo tasks will be displayed.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Events for the specified date, deadlines up to the specified date, and all todo tasks are displayed."
            + "and displays them as a list with index numbers.\n"
    		+ "if no date is entered, events for today, all deadline tasks and all todo tasks will be displayed."
            + "Parameters: [DATE] \n"
            + "Example: " + COMMAND_WORD + " 16 Oct 2016 \n"
            + "Note: if \"view done\" is entered instead, the list of done tasks will be shown.";

    private LocalDate date;
    private boolean hasDate;
    private boolean isViewDoneCommand;

    public ViewCommand(String parameter) {
    	assert parameter !=null;
    	if (parameter.equals("done")) {
    		isViewDoneCommand = true;
    	} else { // parameter is a date
    		this.date = LocalDate.parse(parameter, TaskDate.DATE_FORMATTER);
    		this.hasDate = true;
    		isViewDoneCommand = false;
    	}
    }
    
    public ViewCommand() {
    	this.date = createTaskDate();
    	this.hasDate = false;
    }

    private LocalDate createTaskDate() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	Date dateobj = new Date();
    	String date = df.format(dateobj);
    	return LocalDate.parse(date, TaskDate.DATE_FORMATTER);
	}
    
    @Override
    public CommandResult execute() {
    	if (isViewDoneCommand) {
    		model.updateFilteredDoneList();
            return new CommandResult(getMessageForTaskListShownSummary(model.getTaskList().size()));
    	} else {
    		model.updateFilteredDateTaskList(date, hasDate);
    		return new CommandResult(getMessageForTaskListShownSummary(model.getTaskList().size()));
    	}
    }

    @Override
    public void saveStateIfNeeded(String commandText) {
        model.saveState(commandText);
    }

}
