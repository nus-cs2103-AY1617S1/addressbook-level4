package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ActivityManager;
import seedu.address.model.activity.*;

/**
 *
 */
public class TypicalTestActivities {

    public static TestActivity groceries, reading, guitar;

    public TypicalTestActivities() {
        try {
            groceries =  new ActivityBuilder().withName("Buy groceries").build();
            reading = new ActivityBuilder().withName("Read favourite book").build();
            guitar = new ActivityBuilder().withName("Practice playing guitar").build();
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadActivityManagerWithSampleData(ActivityManager ab) {

//        try {
            ab.addActivity(new Activity(groceries));
            ab.addActivity(new Activity(reading));
            ab.addActivity(new Activity(guitar));
//        } catch (UniquePersonList.DuplicatePersonException e) {
//            assert false : "not possible";
//        }
    }

    public TestActivity[] getTypicalActivities() {
        return new TestActivity[]{groceries, reading, guitar};
    }

    public ActivityManager getTypicalActivityManager(){
        ActivityManager am = new ActivityManager();
        loadActivityManagerWithSampleData(am);
        return am;
    }
}
