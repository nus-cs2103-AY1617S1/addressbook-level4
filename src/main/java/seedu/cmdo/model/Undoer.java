package seedu.cmdo.model;

import java.util.EmptyStackException;
import java.util.Stack;

import seedu.cmdo.commons.core.EventsCenter;

public class Undoer {
	private static Undoer undoer;
	private Stack<ReadOnlyToDoList> undoList;
	private Stack<ReadOnlyToDoList> redoList;
	private EventsCenter ec;

    private Undoer(ReadOnlyToDoList initialTdl) {
		init(initialTdl);
    }
    
    public static Undoer getInstance(ReadOnlyToDoList initialTdl) {
    	if (undoer == null) {
    		undoer = new Undoer(initialTdl);
    	} return undoer;
    }
    
    private Undoer() {
		init(new ToDoList());
    }
    
    public static Undoer getInstance() {
    	if (undoer == null) {
    		undoer = new Undoer();
    	} return undoer;
    }
    
	
	private void init(ReadOnlyToDoList initialTdl) {
		undoList = new Stack<ReadOnlyToDoList>();
		undoList.push(initialTdl);
		redoList = new Stack<ReadOnlyToDoList>();
		ec = EventsCenter.getInstance();
		ec.registerHandler(this);
    }
	
	public ReadOnlyToDoList peekUndoList() {
		return undoList.peek();
	}
	
	/**
	 * Creates a snapshot of the ToDoList and saves it to the stack.
	 * 
	 * @param tdl ToDoList to be saved
	 * 
	 * @@author A0139661Y
	 */
	public void snapshot(ReadOnlyToDoList tdl) {
		undoList.push(tdl);
	}
	
	//@@ author A0139661Y
	public ReadOnlyToDoList undo(ToDoList currentState) throws EmptyStackException {
		if (undoList.size() <= 1 )
			throw new EmptyStackException();
		ReadOnlyToDoList topmost = undoList.pop();
		redoList.push(currentState);
		return topmost;	
	}
	
	//@@author A0141006B
	public ReadOnlyToDoList redo(ToDoList currentState) throws EmptyStackException {
		if (redoList.size() <= 0)
			throw new EmptyStackException();
		ReadOnlyToDoList topmost = redoList.pop();
		undoList.push(currentState);
		return topmost;
	}
}