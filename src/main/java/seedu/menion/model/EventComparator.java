package seedu.menion.model;

import java.util.Comparator;
import javafx.collections.ObservableList;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.UniqueActivityList;

/**
 * Author (A0146752B)
 * 
 * This class sorts a event list based on date and time
 * Events with earlier state dates are sorted in front.
 */
public class EventComparator implements Comparator<Activity> {
    
    private int dateSortInt, timeSortInt;
    private String date1, date2, time1, time2;
    
    @Override
    public int compare(Activity activityA, Activity activityB) {
        
        //sort by start date
        date1 = activityA.getActivityStartDate().toString();
        date2 = activityB.getActivityStartDate().toString();

        String[] valueOfDate1 = date1.split("-");
        String[] valueOfDate2 = date2.split("-");

        date1 = valueOfDate1[2] + valueOfDate1[1] + valueOfDate1[0];
        date2 = valueOfDate2[2] + valueOfDate2[1] + valueOfDate2[0];

        dateSortInt = date1.compareTo(date2);

        if (dateSortInt != 0){
            return dateSortInt;
        }
        //if both events have same state date, we sort by start time
        else {
            time1 = activityA.getActivityStartTime().toString();
            time2 = activityB.getActivityStartTime().toString();

            timeSortInt = time1.compareTo(time2);

            return timeSortInt;
        }
    }
}
