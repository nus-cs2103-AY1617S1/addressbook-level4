package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Lists all persons in the address book to the user.
 */
//@@author A0131813R
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    private final String typeOfList;

    public static final String MESSAGE_SUCCESS = "Listed all";
    
    public static final String MESSAGE_SUCCESS_TASK = "Listed tasks only";

    public static final String MESSAGE_SUCCESS_ACT = "Listed activities only";
    
    public static final String MESSAGE_SUCCESS_EVENT = "Listed events only";
    
    public static final String MESSAGE_INVALID_LIST_TYPE = "List Command should be followed by (optional) either 'activity', 'event' or 'task' only ";   
    
    public ListCommand(String typeOfList) {
        this.typeOfList= typeOfList;
    }

    @Override
    public CommandResult execute() {

      switch(typeOfList) {

      case "activity":   
          model.updateFilteredActivityListToShowAll();
          return new CommandResult(MESSAGE_SUCCESS_ACT);
      
      case "task":
    	  model.updateFilteredTaskListToShowAll();
          return new CommandResult(MESSAGE_SUCCESS_TASK);
       

      
      case "event":
          model.updateFilteredEventListToShowAll();
          return new CommandResult(MESSAGE_SUCCESS_EVENT);

      case "done":
          model.updateFilteredDoneListToShowAll();
          return new CommandResult(MESSAGE_SUCCESS_EVENT);
          default: //typeOfList equals ""
			model.updateFilteredListToShowAll();
			return new CommandResult(MESSAGE_SUCCESS);
		}
      
      }
    	
}
