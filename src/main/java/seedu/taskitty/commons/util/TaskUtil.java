package seedu.taskitty.commons.util;

public class TaskUtil {
    
    /**
     * Returns the specified index in the {@code command} IF a valid category character was given.
     *   else return the default index
     */
    public static int getCategoryIndex(String command) {
        switch(command) {
            case("d"): 
                return 1;
            
            case("e"): 
                return 2;
            
            default: 
                return 0;
            
        }
    }
    
}
