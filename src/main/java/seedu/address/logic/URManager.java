package seedu.address.logic;

import java.util.ArrayDeque;

import seedu.address.logic.commands.*;

import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTaskMaster;
import seedu.address.model.TaskMaster;

public class URManager {
	
	private ArrayDeque<Context> undoQueue;
	private ArrayDeque<Context> redoQueue;
	private final int MAX_TIMES = 3;
	
	public URManager(){		
		undoQueue = new ArrayDeque<Context>();
		redoQueue = new ArrayDeque<Context>();		
	}
	
	/**
	 * Adds the command to undo queue for LogicManager.
	 */
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
	
	/**
	 * Adds the command to undo queue for redo command.
	 */
	public void addToUndoQueueUsedByRedo(Model model, Command command){
		if(!isUndoable(command)){
			undoQueue.clear();
			redoQueue.clear();
		}else{
			if(!isIgnored(command)){
				if(undoQueue.size() == MAX_TIMES) undoQueue.removeFirst();
				undoQueue.addLast(new Context(model, command));
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
	
	/**
	 * Returns true if the command does not need to be added in undo/redo queue.
	 */
	public Boolean isIgnored(Command command){
		return command instanceof RedoCommand || 
			   command instanceof UndoCommand ||
			   command instanceof IncorrectCommand;
	}
	
	/**
	 * Returns true if the command is undoable.
	 * 	Currently, only ChangeDirectoryCommand is undoable.
	 */
	public Boolean isUndoable(Command command){
		return !(command instanceof ChangeDirectoryCommand);
	}
	//=================================================================
	public class NoAvailableCommandException extends Exception{}
	
	/**
	 * Inner class for backup previous data and commands.
	 */
	public class Context{
		
		private ReadOnlyTaskMaster taskList;
		private Command command;
		Context(Model model, Command command){
			this.command = command;
			this.taskList = new TaskMaster(model.getTaskMaster());
		}
		public Command getCommand(){
			return command;			
		}
		public ReadOnlyTaskMaster getData(){
			return taskList;
		}
	}
}
