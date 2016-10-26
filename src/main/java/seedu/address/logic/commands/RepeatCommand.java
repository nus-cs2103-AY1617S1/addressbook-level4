package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;

public class RepeatCommand extends Command{
	public static final String MESSAGE_SUCCESS = "Task repeated"; 
	public static final String COMMAND_WORD = "repeat";
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Repeats the selected task with a preset time extension.\n"
			+ "Parameters: INDEX (must be a positive integer)\n"
			+ "Example: " + COMMAND_WORD + " 1";
	
	public final int targetIndex;
	public String value;
	
	public RepeatCommand(int targetIndex, String value){
		this.targetIndex = targetIndex;
		this.value = value;
	}
	
	public CommandResult execute(){
		
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask task = lastShownList.get(targetIndex - 1);
        
        
        
		return new CommandResult(MESSAGE_SUCCESS);
	}
}
