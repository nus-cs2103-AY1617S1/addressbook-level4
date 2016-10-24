package seedu.taskitty.logic.commands;

import java.time.LocalDate;

import seedu.taskitty.commons.util.DateUtil;
import seedu.taskitty.model.task.TaskDate;
//@@author A0130853L
/**
 * This command has 3 types of functionalities, depending on the following keyword that is entered.
 * Type 1: view [date]
 * Lists all events for the specified date, deadlines up to the specified date, and all todo tasks.
 * If no date is specified, all events for today, all deadlines and todo tasks will be displayed.
 * Type 2: view done
 * Lists all tasks that have been completed.
 * Type 3: view all
 * Lists all tasks in the task manager.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD + " [date]";
    public static final String MESSAGE_USAGE = "This command shows all tasks for the specified date, Meow!"
    		+ "If no date is entered, events for today and all other tasks will be shown, Meow Meow!"
            + " Use \"view done\" to see all done tasks, or \"view all\" to see all tasks!";
    public static final String VIEW_ALL_MESSAGE_SUCCESS = "All tasks are listed, Meow!";
    private LocalDate date;
    private boolean hasDate; // specific to the view [date], 
                             // to differentiate between the two commands since resulting conditions are slightly different
    private enum ViewType {done, date, all}; // to differentiate between 3 types of command functionalities
    private ViewType viewType;

    /**
     * Constructor for view done, view all, and view date command functionalities.
     * @param parameter must not be empty, and will definitely be "done", 
     * "all" or a valid date guaranteed by the command parser.
     */
    public ViewCommand(String parameter) {
    	assert parameter !=null;
    	switch (parameter) { 
    		case "done": // view done tasks
    			viewType = ViewType.done;
    			break;
    		case "all": // view all tasks
    			viewType = ViewType.all;
    			break;
    		default: // view tasks based on date
    			this.date = LocalDate.parse(parameter, TaskDate.DATE_FORMATTER);
        		this.hasDate = true;
        		viewType = ViewType.date;
    	}
    }
    
    /**
     * Constructor for view today, since no date is specified. 
     * a date object capturing the date of executing this command is created instead.
     */
    public ViewCommand() {
    	this.date = DateUtil.createCurrentDate();
    	this.hasDate = false;
    	this.viewType = ViewType.date;
    }

    
    @Override
    public CommandResult execute() {
    	switch(viewType) {
    		case done: // view done
    			model.updateFilteredDoneList();
    			return new CommandResult(getMessageForTaskListShownSummary(model.getTaskList().size()));
    		case all: // view all
    			 model.updateFilteredListToShowAll();
    		     return new CommandResult(VIEW_ALL_MESSAGE_SUCCESS);
    		default: // view date or view today   
    			model.updateFilteredDateTaskList(date, hasDate);
    			return new CommandResult(getMessageForTaskListShownSummary(model.getTaskList().size()));
    	}
    }

    @Override
    public void saveStateIfNeeded(String commandText) {
        model.saveState(commandText);
    }

}
