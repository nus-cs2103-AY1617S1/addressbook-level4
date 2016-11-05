package seedu.task.model;

import java.util.EmptyStackException;
import java.util.Stack;

import seedu.task.logic.commands.Command;

//@@author A0153411W
/**
 * Manager for undo command
 */
public class CommandManager {

	private final Stack<Command> reversibleCommands;
	private final Stack<Command> redoCommands;
	
	public CommandManager() {
		// Initialize stack for reversible commands
		reversibleCommands = new Stack<Command>();
		
		// Initialize stack for redo commands
		redoCommands = new Stack<Command>();
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
}
// @@author
