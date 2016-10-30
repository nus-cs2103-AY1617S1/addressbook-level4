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
	
	private static Stack<String> prevCommand = new Stack<String>();
	private static Stack<String> nextCommand = new Stack<String>();
	private static Stack<Command> executedCommands = new Stack<Command>();
    private static Stack<Command> revertedCommands = new Stack<Command>();
	private static ReadOnlyTask lastModifiedTask = null;
    private static Set<String> filteredKeywords = null;
//    private static String storageFilePath;
	
	public static void addPrevCommand(String commandText) {
		while (!nextCommand.isEmpty()) {
			prevCommand.push(nextCommand.pop());
		}
		prevCommand.push(commandText);
	}
	
	public static void addNextCommand(String commandText) {
		nextCommand.push(commandText);
	}
	
	
	public static String getPrevCommand() {
		String result = "";
		if (prevCommand.size() > 0) {
			if (prevCommand.size() == 1) {
				result = prevCommand.peek();
			} else {
				result = prevCommand.pop();
				nextCommand.push(result);
			}
		}
		return result;
	}

	public static String getNextCommand() {
		String result = "";
		if (!nextCommand.isEmpty()) {
			result = nextCommand.pop();
			prevCommand.push(result);
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
	
    public static void flushRevertedCommands() {
        revertedCommands.clear();
    }
	
    public static void flushPrevCommands() {
        prevCommand.clear();
    }
    
    public static void flushNextCommands() {
        nextCommand.clear();
    }
	
	public static void setModifiedTask(ReadOnlyTask task) {
	    lastModifiedTask = task;
	}
	
    public static void resetModifiedTask() {
        lastModifiedTask = null;
    }
	
    public static ReadOnlyTask getModifiedTask() throws TaskNotFoundException {
        if (lastModifiedTask == null) {
            throw new TaskNotFoundException(Messages.MESSAGE_PREV_TASK_NOT_FOUND);
        } else {
            return lastModifiedTask;
        }
    }
    
    public static Set<String> getFilteredKeyWords() {
        return filteredKeywords;
    }
    
    public static void setFilteredKeyWords(Set<String> keywords) {
        filteredKeywords = keywords;
    }

    public static void resetFilteredKeyWords() {
        filteredKeywords = null;
    }
    
    public static void resetAll() {
        resetFilteredKeyWords();
        resetModifiedTask();
        flushExecutedCommands();
        flushNextCommands();
        flushPrevCommands();
        flushRevertedCommands();
    }
}
