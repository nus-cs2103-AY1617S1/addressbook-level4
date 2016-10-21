package tars.commons.util;

import java.util.ArrayList;

import tars.model.task.Status;

/**
 * Tracks changes made (if any) during mark command
 * @@author A0121533W
 *
 */
public class MarkTaskTracker {
    
    private ArrayList<Integer> markDoneTasks;
    private ArrayList<Integer> markUndoneTasks;
    private ArrayList<Integer> alreadyDoneTasks;
    private ArrayList<Integer> alreadyUndoneTasks;
    
    public static final String SUCCESS_DONE = "Task: %1$s marked done successfully.\n"; 
    public static final String SUCCESS_UNDONE = "Task: %1$s marked undone successfully.\n"; 
    public static final String ALREADY_DONE = "Task: %1$s already marked done.\n"; 
    public static final String ALREADY_UNDONE = "Task: %1$s already marked undone.\n"; 
    
    
    /**
     * Constructor
     */
    public MarkTaskTracker() {
        this.markDoneTasks = new ArrayList<Integer>();
        this.markUndoneTasks = new ArrayList<Integer>();
        this.alreadyDoneTasks = new ArrayList<Integer>();
        this.alreadyUndoneTasks = new ArrayList<Integer>();
    }
    
    /**
     * Adds target index of task to relevant "To Mark List" based on status
     * @param targetIndex
     * @param status Done or Undone
     */
    public void addToMark(int targetIndex, Status status) {
        if (status.status) {
            addToMarkDoneTask(targetIndex);
        } else {
            addToMarkUndoneTask(targetIndex);
        }
        
    }

    /**
     * Adds target index of task to relevant "Already Marked List" based on status
     * @param targetIndex
     * @param status Done or Undone
     */
    public void addAlreadyMarked(int targetIndex, Status status) {
        if (status.status) {
            addToAlreadyDoneTasks(targetIndex);
        } else {
           addToAlreadyUndoneTasks(targetIndex);
        }
    }

    /**
     * Return string for each tasks index in the specific ArrayLists
     * @return
     */
    public String getResult() {
        String markDoneTasksString = getIndexesString(markDoneTasks);
        String markUndoneTasksString = getIndexesString(markUndoneTasks);
        String alreadyDoneTasksString = getIndexesString(alreadyDoneTasks);
        String alreadyUndoneTasksString = getIndexesString(alreadyUndoneTasks);
        
        String result = formatResults(markDoneTasksString, markUndoneTasksString, 
                alreadyDoneTasksString, alreadyUndoneTasksString);
        
        return result;
    }

    private String formatResults(String markDoneTasksString, String markUndoneTasksString,
            String alreadyDoneTasksString, String alreadyUndoneTasksString) {
        
        String markDoneResult = "";
        String markUndoneResult = "";
        String alreadyDoneResult = "";
        String aldreadyUndoneResult = "";
        
        if (markDoneTasksString.length() != 0) {
            markDoneResult = String.format(SUCCESS_DONE, markDoneTasksString);
        }
        if (markUndoneTasksString.length() != 0) {
            markUndoneResult = String.format(SUCCESS_UNDONE, markUndoneTasksString);
        }
        if (alreadyDoneTasksString.length() != 0) {
            alreadyDoneResult = String.format(ALREADY_DONE, alreadyDoneTasksString);
        }
        if (alreadyUndoneTasksString.length() != 0) {
            aldreadyUndoneResult = String.format(ALREADY_UNDONE, alreadyUndoneTasksString);
        }
        
        return markDoneResult + markUndoneResult
               + alreadyDoneResult + aldreadyUndoneResult;
    }

    /**
     * Gets String of indexes separated by comma
     */
    private String getIndexesString(ArrayList<Integer> list) {
        String toReturn = "";
        if (list.size() != 0) {
            for (int i = 0; i < list.size() - 1; i++) {
                toReturn += Integer.toString(list.get(i)) + ", ";
            }
            // Add last index
            toReturn += Integer.toString(list.get(list.size() - 1));
        }
        return toReturn;
    }
    
    private void addToMarkDoneTask(int index) {
        this.markDoneTasks.add(index);
    }
    
    private void addToMarkUndoneTask(int index) {
        this.markUndoneTasks.add(index);
    }
    
    private void addToAlreadyDoneTasks(int index) {
        this.alreadyDoneTasks.add(index);
    }
    
    private void addToAlreadyUndoneTasks(int index) {
        this.alreadyUndoneTasks.add(index);
    }
}
