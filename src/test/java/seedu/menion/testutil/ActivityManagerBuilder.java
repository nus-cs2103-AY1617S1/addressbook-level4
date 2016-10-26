package seedu.menion.testutil;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.UniqueActivityList;

/**
 * A utility class to help with building ActivityManager objects.
 * Example usage: <br>
 *     {@code ActivityManager ab = new ActivityManagerBuilder().withTask("assignment1", "assignment2").build();}
 */
public class ActivityManagerBuilder {

    private ActivityManager activityManager;

    public ActivityManagerBuilder(ActivityManager activityManager){
        this.activityManager = activityManager;
    }

    public ActivityManagerBuilder withTask(Activity task) throws UniqueActivityList.DuplicateTaskException {
        activityManager.addTask(task);
        return this;
    }

   
    public ActivityManager build(){
        return activityManager;
    }
}
