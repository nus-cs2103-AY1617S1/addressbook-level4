package seedu.todoList.logic.commands;

import seedu.todoList.model.TaskList;

/**
 * Clears the TodoList.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String INVALID_TYPE = "Invalid data type";
    public static final String TODO_MESSAGE_SUCCESS = "TodoList has been cleared!";
    public static final String EVENT_MESSAGE_SUCCESS = "EventList has been cleared!";
    public static final String DEADLINE_MESSAGE_SUCCESS = "DeadlineList has been cleared!";
    
    private String dataType;

    public ClearCommand(String args) {
    	this.dataType = args.trim();
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        switch(dataType) {
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