package seedu.address.logic.commands;


/**
 * Lists all persons in the address book to the user.
 */
//@@author A0131813R
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    private final String typeOfList;

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    
    public static final String MESSAGE_SUCCESS_TASK = "Listed all tasks";

    public static final String MESSAGE_SUCCESS_ACT = "Listed all activities";
    
    public static final String MESSAGE_SUCCESS_EVENT = "Listed all events";
    
    public ListCommand(String typeOfList) {
        this.typeOfList= typeOfList;
    }

    @Override
    public CommandResult execute() {

        if(typeOfList.contains("task")){
            model.updateFilteredTaskListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS_TASK);
        }    
        if(typeOfList.contains("activit")){
            model.updateFilteredActivityListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS_ACT);
        }
        
        if(typeOfList.contains("event")){
            model.updateFilteredEventListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS_EVENT);
        }
        
        else
            model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
