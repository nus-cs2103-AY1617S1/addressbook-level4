package seedu.taskscheduler.logic.commands;

import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0140007B
/**
 * Keep track of commands and modifications to task scheduler.
 */
public class CommandHistory {
	
	private static Stack<String> prevCmd = new Stack<String>();
	private static Stack<String> nextCmd = new Stack<String>();
	private static Stack<Command> executedCommands = new Stack<Command>();
    private static Stack<Command> revertedCommands = new Stack<Command>();
	private static ReadOnlyTask lastModTask;
    private static Set<String> filteredKeywords = null;
//    private static String storageFilePath;
	
	public static void addPrevCmd(String commandText) {
		while (!nextCmd.isEmpty()) {
			prevCmd.push(nextCmd.pop());
		}
		prevCmd.push(commandText);
	}
	
	public static void addNextCmd(String commandText) {
		nextCmd.push(commandText);
	}
	
	
	public static String getPrevCmd() {
		String result = "";
		if (prevCmd.size() > 0) {
			if (prevCmd.size() == 1) {
				result = prevCmd.peek();
			} else {
				result = prevCmd.pop();
				nextCmd.push(result);
			}
		}
		return result;
	}

	public static String getNextCmd() {
		String result = "";
		if (!nextCmd.isEmpty()) {
			result = nextCmd.pop();
			prevCmd.push(result);
		}
		return result;
	}

	public static void addExecutedCommand(Command command) {
		executedCommands.push(command);
	}
	
    public static void addRevertedCommand(Command command) {
        revertedCommands.push(command);
    }
	
	public static Command getExecutedCommand() throws EmptyStackException{
		if (executedCommands.size() > 0)
			return executedCommands.pop();
		else
			throw new EmptyStackException();
	}

    public static Command getRevertedCommand() throws EmptyStackException{
        if (revertedCommands.size() > 0)
            return revertedCommands.pop();
        else
            throw new EmptyStackException();
    }
	
	public static void flushExecutedCommands() {
		executedCommands.clear();
	}
	
    public static void flushPrevCommands() {
        prevCmd.clear();
    }
    
    public static void flushNextCommands() {
        nextCmd.clear();
    }
	
	public static void setModTask(ReadOnlyTask task) {
	    lastModTask = task;
	}
	
    public static ReadOnlyTask getModTask() throws TaskNotFoundException {
        if (lastModTask == null) {
            throw new TaskNotFoundException(Messages.MESSAGE_PREV_TASK_NOT_FOUND);
        } else {
            return lastModTask;
        }
    }
    
    public static Set<String> getFilteredKeyWords() {
        return filteredKeywords;
    }
    
    public static void setFilteredKeyWords(Set<String> keywords) {
        filteredKeywords = keywords;
    }
}
