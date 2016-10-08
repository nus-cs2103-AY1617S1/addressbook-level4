package seedu.address.logic;

import java.util.ArrayDeque;

import seedu.address.logic.commands.*;

import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.TaskList;

public class URManager {
	
	private ArrayDeque<Context> undoQueue;
	private ArrayDeque<Context> redoQueue;
	private final int MAX_TIMES = 3;
	
	public URManager(){		
		undoQueue = new ArrayDeque<Context>();
		redoQueue = new ArrayDeque<Context>();		
	}
	
	public void addToUndoQueue(Model model, Command command){
		if(!isUndoable(command)){
			undoQueue.clear();
			redoQueue.clear();
		}else{
			if(!isIgnored(command)){
				if(undoQueue.size() == MAX_TIMES) undoQueue.removeFirst();
				undoQueue.addLast(new Context(model, command));
				redoQueue.clear();
			}
		}
	}
	
	public Context getContextToUndo() throws NoAvailableCommandException{
		try{
			Context contextToUndo = undoQueue.removeLast();
			redoQueue.addLast(contextToUndo);
			return contextToUndo;
		} catch (Exception e){
			throw new NoAvailableCommandException();
		}
	}
	
	public Context getContextToRedo() throws NoAvailableCommandException{
		try{
			Context contextToRedo = redoQueue.removeLast();
			undoQueue.addLast(contextToRedo);
			return contextToRedo;
		} catch (Exception e){
			throw new NoAvailableCommandException();
		}
	}
	
	public Boolean isIgnored(Command command){
		return command instanceof RedoCommand || 
			   command instanceof UndoCommand ||
			   command instanceof IncorrectCommand;
	}
	
	public Boolean isUndoable(Command command){
		return !(command instanceof ChangeDirectoryCommand);
	}
	//=================================================================
	public class NoAvailableCommandException extends Exception{}
	
	public class Context{
		
		private ReadOnlyTaskList taskList;
		private Command command;
		Context(Model model, Command command){
			this.command = command;
			this.taskList = new TaskList(model.getTaskList());
		}
		public Command getCommand(){
			return command;			
		}
		public ReadOnlyTaskList getData(){
			return taskList;
		}
	}
}
