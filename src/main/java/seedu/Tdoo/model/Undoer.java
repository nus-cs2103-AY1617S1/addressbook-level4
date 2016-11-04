package seedu.Tdoo.model;

import java.util.Stack;

import seedu.Tdoo.logic.commands.*;
import seedu.Tdoo.model.task.ReadOnlyTask;

//@@author A0144061U
public class Undoer {

	private final Stack<Command> undoStack;
	private final Model model;
	private static Undoer instance;
	private boolean undoEdit = false;
	
	private final String EMPTY_UNDOSTACK_MESSAGE = "There was no undoable command before.";

	public static Undoer getInstance(Model model) {
		if (instance == null) {
			instance = new Undoer(model);
		}
		return instance;
	}

	private Undoer(Model model) {
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
	//@@author A0139923X
	public void prepareUndoEdit(ReadOnlyTask original, String dataType, ReadOnlyTask toEdit , int targetIndex , String type) {
	    System.out.println("index undo = " + targetIndex);
	    if(!type.equals("")){
	        undoStack.push(new UndoEditCommand(type, original, toEdit, targetIndex));
	        undoEdit = true;
		}else{
		    undoStack.push(new EditCommand(toEdit, dataType, original, targetIndex));
		}
	}
	//@@author

	/*
	 * Push a restore command that undo this clear command
	 */
	public void prepareUndoClear(String dataType) {
		undoStack.push(new RestoreListCommand(dataType));
	}

	// @@author A0139920A
	public void prepareUndoDone(String dataType, int index) {
		undoStack.push(new UndoneCommand(dataType, index));
	}

	// @author A0144061U
	public void executeUndo() {
		assert !undoStack.isEmpty();
		Command undoCommand = undoStack.pop();
		undoCommand.setData(model, null);
		undoCommand.execute();
		if(undoEdit){
		    undoStack.pop();
		    undoStack.pop();
		}
	}
}
