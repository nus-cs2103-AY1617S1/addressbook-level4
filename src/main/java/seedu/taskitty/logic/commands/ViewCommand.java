package seedu.taskitty.logic.commands;

import java.time.LocalDate;

import seedu.taskitty.commons.util.DateUtil;
import seedu.taskitty.model.task.TaskDate;
//@@author A0130853L
/**
 * This command has 4 types of functionalities, depending on the following keyword that is entered.
 * Type 1: view DATE/today
 * Lists all events for the specified date, deadlines up to the specified date, and all todo tasks.
 * Type 2: view done
 * Lists all tasks that have been completed.
 * Type 3: view
 * Lists all tasks in the task manager.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD + " [DATE/done/all]";
    public static final String MESSAGE_USAGE = "This command shows upcoming tasks, Meow!\n"
            + " Use \"view [DATE]\" for dated tasks, \"view done\" for done tasks, \"view all\" for all tasks!";
    public static final String VIEW_ALL_MESSAGE_SUCCESS = "All tasks are listed, Meow!";
    private LocalDate date;
    private enum ViewType {done, date, all, normal}; // to differentiate between 4 types of command functionalities
    private ViewType viewType;

    /**
     * Constructor for view done and view date command functionalities.
     * @param parameter must not be empty, and will definitely be "done", "all", 
     * or a valid date guaranteed by the command parser.
     */
    public ViewCommand(String parameter) {
    	assert parameter !=null;
    	switch (parameter) { 
    		case "done": // view done tasks
    			viewType = ViewType.done;
    			break;
    		case "all":
    			viewType = ViewType.all;
    			break;
    		default: // view tasks based on date
    			this.date = LocalDate.parse(parameter, TaskDate.DATE_FORMATTER);
        		viewType = ViewType.date;
    	}
    }
    
    /**
     * Views uncompleted and upcoming tasks, events and deadlines.
     */
    public ViewCommand() {
    	this.viewType = ViewType.normal;
    }

    
    @Override
    public CommandResult execute() {
    	switch(viewType) {
    		case normal: // view uncompleted and upcoming tasks
    			model.updateToDefaultList();
    			return new CommandResult(getMessageForTaskListShownSummary(model.getTaskList().size()));
    		case done: // view done
    			model.updateFilteredDoneList();
    			return new CommandResult(getMessageForTaskListShownSummary(model.getTaskList().size()));
    		case all: // view all
    			 model.updateFilteredListToShowAll();
    		     return new CommandResult(VIEW_ALL_MESSAGE_SUCCESS);
    		default: // view date 
    			model.updateFilteredDateTaskList(date);
    			return new CommandResult(getMessageForTaskListShownSummary(model.getTaskList().size()));
    	}
    }

    @Override
    public void saveStateIfNeeded(String commandText) {
        model.saveState(commandText);
    }

}
