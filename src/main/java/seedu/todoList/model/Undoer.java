package seedu.todoList.model;

import java.util.Stack;

import seedu.todoList.logic.commands.*;
import seedu.todoList.model.task.ReadOnlyTask;

//@@author A0144061U
public class Undoer {
	
	private final Stack<Command> undoStack;
	private final Model model;
	
	public Undoer (Model model) {
		this.model = model;
		undoStack = new Stack<Command>();
	}
	
	/*
	 * Push a delete command that undo this add command
	 */
	public void prepareUndoAdd(ReadOnlyTask task, String dataType) {
		undoStack.push(new DeleteCommand(task, dataType));
	}
	
	/*
	 * Push an add command that undo this delete command
	 */
	public void prepareUndoDelete(ReadOnlyTask restoredTask) {
		undoStack.push(new AddCommand(restoredTask));
	}
	
	/*
	 * Push an edit command that undo this edit command
	 */
	public void prepareUndoEdit(ReadOnlyTask original, String dataType, ReadOnlyTask toEdit) {
		undoStack.push(new EditCommand(toEdit, dataType, original));
	}
	
	/*
	 * Push a restore command that undo this clear command
	 */
	public void prepareUndoClear(String dataType) {
		undoStack.push(new RestoreListCommand(dataType));
	}
	
	//@@author A0139920A
	public void prepareUndoDone(String dataType, int index) {
		undoStack.push(new UndoneCommand(dataType,index));
	}
	
	public void executeUndo() {
		Command undoCommand = undoStack.pop();
    	undoCommand.setData(model, null);
    	undoCommand.execute();
	}
}
