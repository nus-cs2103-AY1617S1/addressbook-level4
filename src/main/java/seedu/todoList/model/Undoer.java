package seedu.todoList.model;

import java.util.Stack;

import seedu.todoList.logic.commands.*;
import seedu.todoList.model.task.ReadOnlyTask;

public class Undoer {
	
	private final Stack<Command> undoStack;
	private final Model model;
	
	public Undoer (Model model) {
		this.model = model;
		undoStack = new Stack<Command>();
	}
	
	public void prepareUndoAdd(ReadOnlyTask task, String dataType) {
		undoStack.push(new DeleteCommand(task, dataType));
	}
	
	public void prepareUndoDelete(ReadOnlyTask restoredTask) {
		undoStack.push(new AddCommand(restoredTask));
	}
	
	public void prepareUndoEdit(ReadOnlyTask original, String dataType, ReadOnlyTask toEdit) {
		undoStack.push(new EditCommand(toEdit, dataType, original));
	}
	
	public void prepareUndoClear(String dataType) {
		undoStack.push(new RestoreListCommand(dataType));
	}
	
	public void executeUndo() {
		Command undoCommand = undoStack.pop();
    	undoCommand.setData(model, null);
    	undoCommand.execute();
	}
}
