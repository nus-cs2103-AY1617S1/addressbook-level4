package seedu.address.logic.commands;

/**
 * Lists all tasks regardless it is completed or not  in the task manager to the user.
 */
public class ListAllCommand extends Command{
     public static final String COMMAND_WORD = "listall";

	 public static final String MESSAGE_SUCCESS = "Listed all tasks";

	 public ListAllCommand() {}
     
	 @Override
	 public CommandResult execute() {
	     model.updateFilteredListToShowAll();
	     return new CommandResult(MESSAGE_SUCCESS);
	 }
}
