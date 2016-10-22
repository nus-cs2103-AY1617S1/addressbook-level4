package seedu.taskitty.commons.util;

import seedu.taskitty.model.task.Task;

/**
 * Helper functions for handling tasks.
 *
 */
public class TaskUtil {
    
    /**
     * Returns the specified index in the {@code command} IF a valid category character was given.
     *   else return the default index
     */
    public static int getCategoryIndex(String command) {
        switch(command) {
        
            case(Task.TODO_CATEGORY_CHAR) :
                return Task.TODO_CATEGORY_INDEX;
            
            case(Task.DEADLINE_CATEGORY_CHAR): 
                return Task.DEADLINE_CATEGORY_INDEX;
            
            case(Task.EVENT_CATEGORY_CHAR): 
                return Task.EVENT_CATEGORY_INDEX;
            
            default: 
                return Task.DEFAULT_CATEGORY_INDEX;
            
        }
    }
    
}
