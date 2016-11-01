package seedu.address.commons.util;

import seedu.address.model.TaskBook.TaskType;

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
        return (target.trim().charAt(0) == 'A' ? TaskType.UNDATED : TaskType.DATED);
    }
    
}
