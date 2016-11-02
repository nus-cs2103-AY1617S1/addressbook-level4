package seedu.unburden.logic.commands;

import java.util.List;

import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.tag.UniqueTagList.DuplicateTagException;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.model.task.Task;

/**
 * Represents an incorrect command. Upon execution, produces some feedback to the user.
 */
public class IncorrectCommand extends Command {

    public final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser){
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute() throws DuplicateTagException, IllegalValueException {
        indicateAttemptToExecuteIncorrectCommand();
        overdueOrNot();
        return new CommandResult(feedbackToUser);
    }
    //This method checks the entire list to check for overdue tasks
   	private void overdueOrNot() throws IllegalValueException, DuplicateTagException {
   		List<ReadOnlyTask> currentTaskList= model.getListOfTask().getTaskList();
   		for(ReadOnlyTask task : currentTaskList){
   			if(((Task) task).checkOverDue()){
   				((Task) task).setOverdue();
   			}
   			else{
   				((Task) task).setNotOverdue();
   			}
   		}
   	}
}

