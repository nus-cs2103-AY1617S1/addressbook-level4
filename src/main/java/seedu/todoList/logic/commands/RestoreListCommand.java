package seedu.todoList.logic.commands;

/**
 * Restore the TaskList.
 */
public class RestoreListCommand extends Command {

    public static final String COMMAND_WORD = "restore";
    public static final String INVALID_TYPE = "Invalid data type";
    public static final String TODO_MESSAGE_SUCCESS = "TodoList has been restored!";
    public static final String EVENT_MESSAGE_SUCCESS = "EventList has been restored!";
    public static final String DEADLINE_MESSAGE_SUCCESS = "DeadlineList has been restored!";
    
    private String dataType;

    public RestoreListCommand(String args) {
    	this.dataType = args.trim();
    }

    //@@author A0144061U
    @Override
    public CommandResult execute() {
        assert model != null;
        switch(dataType) {
        	case "todo":
        		model.restoreTodoListData();
                return new CommandResult(TODO_MESSAGE_SUCCESS);
        	case "event":
        		model.restoreEventListData();
                return new CommandResult(EVENT_MESSAGE_SUCCESS);
        	case "deadline":
        		model.restoreDeadlineListData();
                return new CommandResult(DEADLINE_MESSAGE_SUCCESS);
        }
        return new CommandResult(INVALID_TYPE);
    }
}