package seedu.address.testutil;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.activity.*;

/**
 *
 */
public class TypicalTestActivities {

    public static TestActivity floatingTask, task, event;

    public TypicalTestActivities() {
        try {
            floatingTask =  new Activitybuilder().withFloatingTask(Activity.FLOATING_TASK_TYPE, new ActivityName("CS2103T Lab"), new Note("Bring roses"), new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            task = new Activitybuilder().withTask(Activity.TASK_TYPE, new ActivityName("CS2103T testing"), new Note("it is so hard!"), new ActivityDate("10-08-2016"), new ActivityTime("1900"), new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            event = new Activitybuilder().withEvent(Activity.EVENT_TYPE, new ActivityName("CS2103T tutorial"), new Note("Don't Sleep"), new ActivityDate("10-08-2016"), new ActivityTime("1500"), new ActivityDate("10-08-2016"), new ActivityTime("1800"), new Completed(Completed.UNCOMPLETED_ACTIVITY)) .build();   
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(ActivityManager ab) {

        try {
           // ab.addTask(new Activity(floatingTask));
            ab.addTask(new Activity(task));
           // ab.addTask(new Activity(event));
        } catch (UniqueActivityList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestActivity[] getTypicalActivity() {
        return new TestActivity[]{task};
    }

    public ActivityManager getTypicalActivityManager(){
        ActivityManager ab = new ActivityManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
