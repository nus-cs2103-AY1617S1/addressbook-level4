package seedu.cmdo.model;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.cmdo.MainApp;
import seedu.cmdo.commons.core.LogsCenter;
import seedu.cmdo.commons.events.model.ToDoListChangedEvent;
import seedu.cmdo.commons.events.ui.ExitAppRequestEvent;
import seedu.cmdo.model.task.Task;

/*
* This class handles the saving of past status of the application
* Mainly for UNDO and REDO (possibly) functions
* @@author A0141128R
*/
public class StatusSaver {
	private static final Logger logger = LogsCenter.getLogger(MainApp.class);
	private Stack<ArrayList<Task>> undoMasterStack;
    private Stack<ObservableList<Task>> undoObservableStack;

    private Stack<ArrayList<Task>> redoMasterStack;
    private Stack<ObservableList<Task>> redoObservableStack;

    private ArrayList<Task> tempTaskMasterList;
    private ObservableList<Task> tempTaskObservableList;

   
    public StatusSaver() {
        undoMasterStack = new Stack<ArrayList<Task>>();
        undoObservableStack = new Stack<ObservableList<Task>>();
        redoMasterStack = new Stack<ArrayList<Task>>();
        redoObservableStack = new Stack<ObservableList<Task>>();
    }
    
    /* ***********************************
     * Public methods
     * ***********************************/
    public void saveStatus(ArrayList<Task> taskMasterList, ObservableList<Task> taskObservableList) {
        logger.info("Undo stack size before save: " + undoMasterStack.size());
        logger.info("Redo stack size before save: " + redoMasterStack.size());
        
        undoMasterStack.push(copyArrayList(taskMasterList));
        undoObservableStack.push(copyObservableList(taskObservableList));
        redoMasterStack.clear();
        redoObservableStack.clear();
        
        logger.info("Undo stack size after save: " + undoMasterStack.size());
        logger.info("Redo stack size after save: " + redoMasterStack.size());
    }

    public void retrieveLastStatus() {
        logger.info("Undo stack size before retrieve undo: " + undoMasterStack.size());
        logger.info("Redo stack size before retrieve undo: " + redoMasterStack.size());
        
        try {
            redoMasterStack.push(copyArrayList(undoMasterStack.pop()));
            redoObservableStack.push(copyObservableList(undoObservableStack.pop()));

            tempTaskMasterList = copyArrayList(undoMasterStack.peek());
            tempTaskObservableList = copyObservableList(undoObservableStack.peek());
        } catch (EmptyStackException e) {
            e.printStackTrace();
        }
        
        logger.info("Undo stack size after retrieve undo: " + undoMasterStack.size());
        logger.info("Redo stack size after retrieve undo: " + redoMasterStack.size());
    }

    public void retrieveRedoStatus() {
        logger.info("Undo stack size before retrieve redo: " + undoMasterStack.size());
        logger.info("Redo stack size before retrieve redo: " + redoMasterStack.size());
        
        try {
            tempTaskMasterList = redoMasterStack.pop();
            tempTaskObservableList = redoObservableStack.pop();

            undoMasterStack.push(copyArrayList(tempTaskMasterList));
            undoObservableStack.push(copyObservableList(tempTaskObservableList));
        } catch (EmptyStackException e) {
            e.printStackTrace();
        }
        
        logger.info("Undo stack size after retrieve redo: " + undoMasterStack.size());
        logger.info("Redo stack size after retrieve redo: " + redoMasterStack.size());
    }

    /* ***********************************
     * Public getters
     * ***********************************/
    public ArrayList<Task> getLastTaskMasterList() {
        assert tempTaskMasterList != null;
        return tempTaskMasterList;
    }

    public ObservableList<Task> getLastTaskObservableList() {
        assert tempTaskObservableList != null;
        return tempTaskObservableList;
    }
    
    public boolean isUndoMasterStackEmpty() {
        return undoMasterStack.size() == 1;
    }

    public boolean isRedoMasterStackEmpty() {
        return redoMasterStack.isEmpty();
    }

    public int getUndoMasterStackSize() {
        return undoMasterStack.size();
    }

    public int getRedoMasterStackSize() {
        return redoMasterStack.size();
    }
    
    /* ***********************************
     * Utility methods
     * ***********************************/
    private ArrayList<Task> copyArrayList(ArrayList<Task> origin) {
        ArrayList<Task> duplicate = new ArrayList<Task>();
        for (Task task : origin) {
            duplicate.add(task.makeCopy());
        }
        return duplicate;
    }
    
   
    private ObservableList<Task> copyObservableList(ObservableList<Task> origin) {
        ArrayList<Task> duplicate = new ArrayList<Task>();
        for (Task task : origin) {
            duplicate.add(task.makeCopy());
        }
        return FXCollections.observableArrayList(duplicate);
    }
    
    @Subscribe
    public void handleToDoListChangedEvent(ToDoListChangedEvent event) {
    	saveStatus(event.data);
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }
}
