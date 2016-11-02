package seedu.Tdoo.logic.commands;

/**
 * Clears the TaskList.
 */
public class ClearDoneCommand extends Command {

    public static final String COMMAND_WORD = "clear_done";
    public static final String INVALID_TYPE = "Invalid data type";
    public static final String ALL_MESSAGE_SUCCESS = "All lists have been cleared!";
    public static final String TODO_MESSAGE_SUCCESS = "TodoList has been cleared!";
    public static final String EVENT_MESSAGE_SUCCESS = "EventList has been cleared!";
    public static final String DEADLINE_MESSAGE_SUCCESS = "DeadlineList has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clear a task list with given data type.\n"
            + "Parameters: TASK_TYPE\n"
            + "Example: " + COMMAND_WORD + " all\n"
            + "Example: " + COMMAND_WORD + " todo\n"
            + "Example: " + COMMAND_WORD + " event\n"
            + "Example: " + COMMAND_WORD + " deadline";
    
    private String dataType;

    public ClearDoneCommand(String args) {
    	this.dataType = args.trim();
    }

    //@@author A0144061U
    @Override
    public CommandResult execute() {
        assert model != null;
        switch(dataType) {
	        case "all":
	    		model.resetTodoListData();
	    		model.resetEventListData();
	    		model.resetDeadlineListData();
	            return new CommandResult(ALL_MESSAGE_SUCCESS);
        	case "todo":
        		model.resetTodoListData();
                return new CommandResult(TODO_MESSAGE_SUCCESS);
        	case "event":
        		model.resetEventListData();
                return new CommandResult(EVENT_MESSAGE_SUCCESS);
        	case "deadline":
        		model.resetDeadlineListData();
                return new CommandResult(DEADLINE_MESSAGE_SUCCESS);
        }
        return new CommandResult(INVALID_TYPE);
    }
}