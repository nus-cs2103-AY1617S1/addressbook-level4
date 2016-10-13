package seedu.address.logic;

import java.util.Stack;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;


public class CommandManager {
	
	private Stack<Command> executionStack;
	private Stack<Command> undoneStack;
	
	public CommandManager() {
		executionStack = new Stack<>();
		undoneStack = new Stack<>();
	}
	
	public CommandResult executeCommand(Command cmd) {
		executionStack.push(cmd);
		undoneStack.clear();
		return cmd.execute();
	}
	
	public CommandResult undoCommand() {
		Command cmd = executionStack.pop();
		undoneStack.push(cmd);
		return cmd.undo();
	}
	
	public CommandResult redoCommand() {
		Command cmd = undoneStack.pop();
		executionStack.push(cmd);
		return cmd.execute();
	}
}
