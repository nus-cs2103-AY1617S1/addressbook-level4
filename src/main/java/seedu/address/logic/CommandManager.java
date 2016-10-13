package seedu.address.logic;

import java.util.Stack;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;


public class CommandManager {
	
	private Stack<Command> commandStack;
	private Stack<Command> redoStack;
	
	public CommandManager() {
		commandStack = new Stack<>();
		redoStack = new Stack<>();
	}
	
	public CommandResult executeCommand(Command cmd) {
		return cmd.execute();
	}
}
