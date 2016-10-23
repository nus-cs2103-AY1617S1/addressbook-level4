package seedu.taskitty.logic.commands;

import java.time.LocalDate;

import seedu.taskitty.commons.util.DateUtil;
import seedu.taskitty.model.task.TaskDate;

/**
 * Finds all events for the specified date, deadlines up to the specified date, and all todo tasks.
 * If no date is specified, all events for today, all deadlines and todo tasks will be displayed.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD + " [date]";
    public static final String MESSAGE_USAGE = "This command shows all tasks for the specified date, Meow!"
            + " Use \"view done\" to see all done tasks!";

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
    	this.date = DateUtil.createCurrentDate();
    	this.hasDate = false;
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
