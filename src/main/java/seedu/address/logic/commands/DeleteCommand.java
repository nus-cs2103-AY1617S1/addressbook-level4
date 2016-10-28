package seedu.address.logic.commands;
//@@author A0142325R
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the  Task identified by the index number or specific name used in the last Task listing.\n"
            + "Parameters: INDEX (must be a positive integer) or NAME\n"
            + "Example: " + COMMAND_WORD + " 1 or horror night";
    public static final String MESSAGE_DELETE_SAME_NAME="Please select the Task identified "
    		+ "by the index number.\n"+"Parameters: INDEX(must be a positive integer)\n"
    		+"Example: "+COMMAND_WORD+" 1";
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";
    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    public final int targetIndex;
    public final String name;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.name = null; 
    }

    public DeleteCommand(String name,Pattern k){
    	this.name = name;
    	this.targetIndex = -1;
    }
    
    @Override
    public CommandResult execute() {
        ReadOnlyTask TaskToDelete = null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (targetIndex != -1) {
            if (lastShownList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            TaskToDelete = lastShownList.get(targetIndex - 1);
        } else {
            assert this.name != null;
            ArrayList<ReadOnlyTask> shownList=new ArrayList<ReadOnlyTask>();
            for (ReadOnlyTask e : lastShownList) {
                if (name.trim().equals(e.getName().taskName)) {
                    shownList.add(e);
                }
            }
            if(shownList.size()>1){
            	final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(name.trim());
            	 if (!matcher.matches()) {
                     return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                             DeleteCommand.MESSAGE_USAGE));
                 }
                 // keywords delimited by whitespace
                 final String[] keywords = matcher.group("keywords").split("\\s+");
                 final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
            	model.updateFilteredTaskList(keywordSet);
            	return new CommandResult(MESSAGE_DELETE_SAME_NAME);
            }else if(shownList.size()==1){
            	TaskToDelete=shownList.get(0);
            }else{	
            	return new CommandResult(Messages.MESSAGE_INVALID_TASK_NAME);
            }
        }
        try {
            model.deleteTask(TaskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        String message = String.format(getDeleteSuccessMessage(TaskToDelete), TaskToDelete);
        model.saveState(message);
        return new CommandResult(message);
    }

    public static String getDeleteSuccessMessage(ReadOnlyTask TaskToDelete) {
        if (TaskToDelete.isEvent()) {
            return MESSAGE_DELETE_EVENT_SUCCESS;
        } else {
            return MESSAGE_DELETE_TASK_SUCCESS;
        }
    }

}
