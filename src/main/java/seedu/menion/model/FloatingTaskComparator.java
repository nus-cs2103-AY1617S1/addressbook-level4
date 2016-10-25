package seedu.menion.model;

import java.util.Comparator;
import javafx.collections.ObservableList;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.UniqueActivityList;

/**
 * Author (A0146752B)
 * 
 * This class sorts a floating task list based on completion status,
 * Completed floating tasks are pushed to the bottom of the list,
 */
public class FloatingTaskComparator implements Comparator<Activity> {
    
    private int completeSortInt;
    
    @Override
    public int compare(Activity activityA, Activity activityB) {
        
        //sort by completion status 
        if (activityA.getActivityStatus().toString().equals(activityB.getActivityStatus().toString())) {
            completeSortInt = 0;
        }
        else if (activityA.getActivityStatus().toString().equals("Completed")
                && activityB.getActivityStatus().toString().equals("Uncompleted")) {
            completeSortInt = 1;
        }
        else {
            completeSortInt = -1;
        }
      
        return completeSortInt;
    }
}
