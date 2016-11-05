//@@author A0147335E
package seedu.task.logic;

import java.util.ArrayList;

/**
 * This class keeps track of the successful commands typed by users
 * to allow and support undo command.
 * 
 *
 */
public class HistoryManager {

    private ArrayList<RollBackCommand> undoList;

    private ArrayList<String> previousCommandList;

    public HistoryManager() {
        undoList = new ArrayList<RollBackCommand>();
        previousCommandList = new ArrayList<String>();
    }

    public ArrayList<RollBackCommand> getUndoList() {
        return undoList;
    }


    public ArrayList<String> getPreviousCommandList() {
        return previousCommandList;
    }
}