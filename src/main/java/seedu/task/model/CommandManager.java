package seedu.task.model;

import java.util.EmptyStackException;
import java.util.Stack;

import seedu.task.logic.commands.Command;

//@@author A0153411W
/**
 * Manager for undo command
 */
public class CommandManager {

	private static final String NO_HISTORY_RESULT = "No commands executed."; 
	private static final int HISTORY_MAX_SIZE = 10; 
	
	private final Stack<Command> reversibleCommands;
	private final Stack<Command> redoCommands;
	private final Stack<String> textCommands;
	
	public CommandManager() {
		// Initialize stack for reversible commands
		reversibleCommands = new Stack<Command>();
		
		// Initialize stack for redo commands
		redoCommands = new Stack<Command>();
		
		// Initialize stack for text commands
		textCommands = new Stack<String>();
	}

	/** Add text representation of command to history */
	public void addCommandForRedo(Command command) {
		redoCommands.push(command);
	}

	/** Add command for undo */
	public void addCommandForUndo(Command command) {
		if (command.isReversible()) {
			reversibleCommands.push(command);
		}
	}

	/**
	 * Get last executed reversible command return Command
	 * 
	 * @throws EmptyStackException
	 *             if stack is empty
	 */
	public Command getCommandForUndo() throws EmptyStackException {
		return reversibleCommands.pop();
	}

	/**
	 * Get last undo command for redo
	 * return Command
	 * 
	 * @throws EmptyStackException
	 *             if stack is empty
	 */
	public Command getCommandForRedo() throws EmptyStackException {
		return redoCommands.pop();
	}
	
	/**
	 * Add user inputed command to command manager for history
	 */
	public void addCommandForHistory(String commandText) {
		textCommands.push(commandText);
	}
	
	/**
	 * Get history of executed user's commands
	 */
	public String getCommandHistory(){
		StringBuilder history= new StringBuilder(); 
		int historySize = 0;
		for(int i=textCommands.size()-2; i>=0 &&  textCommands.get(i)!=null && historySize< HISTORY_MAX_SIZE; i--){
			history.append(textCommands.get(i)+"\n"); 
			historySize++;
		}
		return history.length() == 0 ? NO_HISTORY_RESULT : history.toString();
	}
}
// @@author
