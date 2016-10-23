package seedu.task.model;


import java.util.EmptyStackException;
import java.util.Stack;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.CommandResult;
import seedu.task.model.task.Task;


public class UndoCommandManager {
	
	private final Stack<Command> executedCommands;
	
	public UndoCommandManager(){
		executedCommands= new Stack<Command>();
	}
	
	public void addCommand(Command command){
		executedCommands.push(command);
	}
	
	public Command getCommandForUndo() throws EmptyStackException{
		return executedCommands.pop();
	}
}




