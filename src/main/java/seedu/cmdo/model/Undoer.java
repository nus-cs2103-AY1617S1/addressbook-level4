package seedu.cmdo.model;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.cmdo.commons.core.EventsCenter;
import seedu.cmdo.commons.core.LogsCenter;
import seedu.cmdo.commons.events.model.ToDoListChangedEvent;

public class Undoer {
	private static Undoer undoer;
	private Stack<ReadOnlyToDoList> undoList;
	private Stack<ReadOnlyToDoList> redoList;
	private Logger logger;
	private EventsCenter ec;

    private Undoer(ReadOnlyToDoList initialTdl) {
		init(initialTdl);
    }
    
    public static Undoer getInstance(ReadOnlyToDoList initialTdl) {
    	if (undoer == null) {
    		undoer = new Undoer(initialTdl);
    	} return undoer;
    }
    
	
	private void init(ReadOnlyToDoList initialTdl) {
		undoList = new Stack<ReadOnlyToDoList>();
		undoList.push(initialTdl);
		redoList = new Stack<ReadOnlyToDoList>();
		logger = LogsCenter.getLogger(Undoer.class);
		logger.info("" + undoList.size() + "/" + undoList.peek().toString());
		ec = EventsCenter.getInstance();
		ec.registerHandler(this);
    }
	
	/**
	 * Saves a ToDoList in a stack memory.
	 * 
	 * @param tdl ToDoList to be saved
	 * 
	 * @@author A0139661Y
	 */
	public void save(ReadOnlyToDoList tdl) {
		undoList.push(tdl);
		logger.info("saveLast called. UndoList has at top" + undoList.peek().toString());
	}
	
	public ReadOnlyToDoList undo() throws EmptyStackException {
		if (undoList.size() <=1 )
			throw new EmptyStackException();
		logger.info("undo called. UndoList has " + undoList.size());
		redoList.push(undoList.pop());
		logger.info("undoList " + undoList.peek().toString() +" popped. UndoList has" + undoList.size());
		logger.info("redoList is " + redoList.isEmpty());
		return undoList.peek();	
	}
	
	public ReadOnlyToDoList redo() {
		undoList.push(redoList.pop());
		return redoList.peek();
	}
	
	/**
	 * Saves the new toDoList to the stack upon changed event.
	 * It also saves an extra time upon undo, however, so we check if the existing tdl is not the same.
	 *  
	 * @param tdlce
	 * 
	 * @@author A0139661Y
	 */
	@Subscribe
	public void handleToDoListChangedEvent(ToDoListChangedEvent tdlce) {
        if (!tdlce.calling.equals(this.getClass())) {
        	logger.info(LogsCenter.getEventHandlingLogMessage(tdlce, "Saving modified tdl"));
			ReadOnlyToDoList modifiedTdl = tdlce.data;
			save(modifiedTdl);
        }
	}
}
