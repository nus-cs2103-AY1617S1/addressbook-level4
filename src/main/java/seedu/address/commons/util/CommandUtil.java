package seedu.address.commons.util;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.TaskBook.TaskType;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0139145E
/**
 * Utility methods related to Command
 */
public class CommandUtil {

    /**
     * Returns true if either of the lists contains the given index (offset by +1)
     * 
     */
    public static boolean isValidIndex(String target, int UndatedListSize, int DatedListSize) {
        if (!isValidType(target)){
            return false;
        }
        if (getTaskType(target) == TaskType.UNDATED && getIndex(target) <= UndatedListSize && getIndex(target) > 0) {
            //givenIndex points to an undated task
            return true;
        }
        else if (getTaskType(target) == TaskType.DATED && getIndex(target) <= DatedListSize && getIndex(target) > 0) {
            //givenIndex points to an dated task
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Returns ReadOnlyTask from correct list based on targetIndex
     * @param targetIndex
     * @param lastDatedTaskList
     * @param lastUndatedTaskList
     * @return ReadOnlyTask
     */
    public static ReadOnlyTask getTaskFromCorrectList(String targetIndex, 
    		UnmodifiableObservableList<ReadOnlyTask> lastDatedTaskList,
    		UnmodifiableObservableList<ReadOnlyTask> lastUndatedTaskList){
    	
    	TaskType type = getTaskType(targetIndex);
        int indexNum = getIndex(targetIndex);
        
        if (type == TaskType.DATED) {
            return lastDatedTaskList.get(indexNum - 1);
        }
        else if (type == TaskType.UNDATED){
            return lastUndatedTaskList.get(indexNum - 1);
        }
        else {
            assert false : "Task type not found";
        	return null;
        }    	
    }
    
    private static boolean isValidType(String target){
       char taskType = target.trim().toUpperCase().charAt(0);
       if (taskType == 'A' || taskType == 'B') {
           return true;
       }
       else {
           return false;
       }
    }
    
    /**
     * Returns the numerical index in the given target index
     */
    public static int getIndex(String target){
        return Integer.parseInt(target.trim().substring(1));
    }
    
    /**
     * Returns the task type in the given target index
     */
    public static TaskType getTaskType(String target){
        switch (target.trim().charAt(0)){
        case 'A':
            return TaskType.UNDATED;
        
        case 'B':
            return TaskType.DATED;
            
        default:
            return null;
        }
    }
    
}
