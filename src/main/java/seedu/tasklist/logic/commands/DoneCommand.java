//@@author A0144919W
package seedu.tasklist.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Marks the task at a specified index or containing specified keywords as done or complete
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a task as complete\n"
            + "Parameters: either INDEX (must be a positive integer) or TASKNAME\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_DONE_TASK_FAILURE = "No such task was found.";
    public static final String MESSAGE_DONE_IN_NEXT_STEP = "Multiple tasks were found containing the entered keywords. Please check below and mark as complete by index.";
    public static final String MESSAGE_ALREADY_DONE = "This task is already marked as complete.";

    public final boolean doneByIndex;

    public int targetIndex;
    public String taskName;
    
    public DoneCommand(int targetIndex) {
        doneByIndex = true;
        this.targetIndex = targetIndex-1;
    }

    public DoneCommand(String taskName) {
        doneByIndex = false;
        taskName = taskName.trim();
        this.taskName = taskName;
    }

    @Override
    public CommandResult execute() {
        if(doneByIndex){
            return doneUsingIndex();
        }
        else{
            return doneUsingString();
        }   
    }
    
    //marks the task at the specified index as complete
    private CommandResult doneUsingIndex() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if(invalidIndexEntered(lastShownList))
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        else {
            return markAsDoneByIndexAndDisplayDoneSuccess(lastShownList);
        }
    }

    /**
     * looks for tasks with specific keywords
     * if only one matching task is found, it is marked as done
     * else all the matching tasks are displayed from which, user can mark a task as complete using index
     */
    private CommandResult doneUsingString() {
    	Set<String> taskNameSet = new HashSet<String>();
    	taskNameSet.add(taskName);
    	model.updateFilteredTaskList(taskNameSet);
    	UnmodifiableObservableList<ReadOnlyTask> matchingTasks = model.getFilteredTaskList();
    	
    	// No tasks match string
    	if (matchingTasks.isEmpty()){
            return displayDoneFailure();
    	}
    	
    	// Only 1 task matches string
    	else if (matchingTasks.size() == 1) {
    		return markAsDoneAndDisplayDoneSuccess(matchingTasks);
    	} 
    	
    	//More than 1 task matches string
    	else {
            return displayMatchingTasks();
    	}
    }
    
    private CommandResult displayMatchingTasks() {
        Set<String> keywords = new HashSet<String>();
        keywords.add(taskName);
        model.updateFilteredTaskList(keywords);
        return new CommandResult(String.format(MESSAGE_DONE_IN_NEXT_STEP));
    }

    private CommandResult markAsDoneAndDisplayDoneSuccess(UnmodifiableObservableList<ReadOnlyTask> matchingTasks) {
        ReadOnlyTask taskToMark = matchingTasks.get(0);
        try {
            model.markTaskAsComplete(taskToMark);
        }
        catch (TaskNotFoundException e) {
            assert false: "The target task cannot be missing";
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToMark.getTaskDetails()));
    }

    private CommandResult displayDoneFailure() {
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_DONE_TASK_FAILURE));
    }

    boolean targetIndexWasEntered() {
        return (targetIndex >= 0);
    }

    private CommandResult markAsDoneByIndexAndDisplayDoneSuccess(UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        ReadOnlyTask taskToMark = lastShownList.get(targetIndex);
        if(taskToMark.isComplete())
            return new CommandResult(MESSAGE_ALREADY_DONE);
        try {
            model.markTaskAsComplete(taskToMark);
        }
        catch (TaskNotFoundException e) {
            assert false: "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToMark.getTaskDetails()));
    }

    private boolean invalidIndexEntered(UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        return targetIndex >= lastShownList.size();
    }

}