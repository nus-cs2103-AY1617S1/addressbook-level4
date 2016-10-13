package seedu.address.logic;

import java.util.Stack;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;


public class CommandManager {
	
	private Stack<UndoableCommand> executionStack;
	private Stack<UndoableCommand> undoneStack;
	
	public CommandManager() {
		executionStack = new Stack<>();
		undoneStack = new Stack<>();
	}
	
	public CommandResult executeCommand(Command cmd) {
		if (cmd instanceof UndoableCommand) {
			executionStack.push((UndoableCommand) cmd);
		}
		
		undoneStack.clear();
		return cmd.execute();
	}
	
	public CommandResult undoCommand() {
		UndoableCommand cmd = executionStack.pop();
		undoneStack.push(cmd);
		return cmd.undo();
	}
	
	public CommandResult redoCommand() {
		UndoableCommand cmd = undoneStack.pop();
		executionStack.push(cmd);
		return cmd.execute();
	}
}
