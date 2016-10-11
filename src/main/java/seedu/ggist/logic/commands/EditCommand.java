package seedu.ggist.logic.commands;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.core.UnmodifiableObservableList;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.task.*;

import static seedu.ggist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Adds a person to the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Edits a person identified by the index number used in the last person listing. "
            +"Contact details can be marked private by prepending 'p' to the prefix.\n\t"
            + "Parameters: INDEX [NAME] [p]p/PHONE [p]e/EMAIL [p]a/ADDRESS  [t/TAG]...\n\t"
            + "Example: " + COMMAND_WORD
            + " 1, TaskName, Buy eggs";

    public static final String MESSAGE_SUCCESS = "Task edited: %1$s";
    public static final String MESSAGE_INVALID_TASK_TYPE = "%1$s is not a valid type";

    public int targetIndex;
    public String toEdit;
    public String type;
    
    public final String TASK_NAME_TYPE = "task";
    public final String DATE_TYPE = "date";
    public final String START_TIME_TYPE = "start time";
    public final String END_TIME_TYPE= "end time";
    
    /**
     * Convenience constructor using raw values.
     *
     */
    public EditCommand(int targetIndex, String type, String toEdit){
    	this.targetIndex = targetIndex;
        this.type = type;
        this.toEdit = toEdit;
        
    }
    
    @Override
    public CommandResult execute() {
        try {
        	UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        	ReadOnlyTask toBeEditedTask = lastShownList.get(targetIndex - 1);
        	switch (type) {
        	case TASK_NAME_TYPE:
        		toBeEditedTask.getTaskName().editTaskName(toEdit);
        		break;
        	case DATE_TYPE:
        		 toBeEditedTask.getDate().editDate(toEdit);
        		 break;
        	case START_TIME_TYPE:
        		toBeEditedTask.getStartTime().editTime(toEdit);
        		break;
        	case END_TIME_TYPE:
        		toBeEditedTask.getEndTime().editTime(toEdit);
        		break;
        	default:
        		return new CommandResult(String.format(MESSAGE_INVALID_TASK_TYPE, type));
        	}
            return new CommandResult(String.format(MESSAGE_SUCCESS, toEdit));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_Task_DISPLAYED_INDEX);
        } catch (IllegalValueException e) {
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    }

}