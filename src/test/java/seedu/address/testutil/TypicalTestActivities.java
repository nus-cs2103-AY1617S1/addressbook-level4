package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ActivityManager;
import seedu.address.model.activity.*;

/**
 *
 */
public class TypicalTestActivities {

    public static TestActivity groceries, reading, guitar, tidy, paint;

    public TypicalTestActivities() {
        try {
            // Automated activities
            groceries =  new ActivityBuilder().withName("Buy groceries").build();
            reading = new ActivityBuilder().withName("Read favourite book").build();
            guitar = new ActivityBuilder().withName("Practice playing guitar").build();
            paint = new ActivityBuilder().withName("Paint room wall (blue)").build();
            
            
            // Manual activities
            tidy = new ActivityBuilder().withName("Tidy study desk").build();
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadActivityManagerWithSampleData(ActivityManager am) {

//        try {
            am.addActivity(new Activity(groceries));
            am.addActivity(new Activity(reading));
            am.addActivity(new Activity(guitar));
            am.addActivity(new Activity(paint));
//        } catch (UniquePersonList.DuplicatePersonException e) {
//            assert false : "not possible";
//        }
    }

    public TestActivity[] getTypicalActivities() {
        return new TestActivity[]{groceries, reading, guitar, paint};
    }

    public ActivityManager getTypicalActivityManager(){
        ActivityManager am = new ActivityManager();
        loadActivityManagerWithSampleData(am);
        return am;
    }
}
