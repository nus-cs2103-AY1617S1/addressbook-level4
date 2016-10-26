package seedu.menion.testutil;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.activity.*;
//@@author A0139164A
/**
 *
 */
public class TypicalTestActivities {

    public static TestActivity floatingTask, task, event, floatingTask2, task2, event2;

    public TypicalTestActivities() {
        try {
            floatingTask =  new Activitybuilder().withFloatingTask(Activity.FLOATING_TASK_TYPE, new ActivityName("CS2103T Lab"), new Note("Bring roses"), new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            task = new Activitybuilder().withTask(Activity.TASK_TYPE, new ActivityName("CS2103T testing"), new Note("it is so hard!"), new ActivityDate("10-08-2016"), new ActivityTime("1900"), new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            event = new Activitybuilder().withEvent(Activity.EVENT_TYPE, new ActivityName("CS2103T tutorial"), new Note("Don't Sleep"), new ActivityDate("10-08-2016"), new ActivityTime("1500"), new ActivityDate("10-08-2016"), new ActivityTime("1800"), new Completed(Completed.UNCOMPLETED_ACTIVITY)) .build();   
            floatingTask2 = new Activitybuilder().withFloatingTask(Activity.FLOATING_TASK_TYPE, new ActivityName("CS2103T Lab2"), new Note("Bring roses"), new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            task2 = new Activitybuilder().withTask(Activity.TASK_TYPE, new ActivityName("CS2103T testing2"), new Note("it is so hard!"), new ActivityDate("10-08-2016"), new ActivityTime("1900"), new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            event2 = new Activitybuilder().withEvent(Activity.EVENT_TYPE, new ActivityName("CS2103T tutorial2"), new Note("Don't Sleep"), new ActivityDate("10-08-2016"), new ActivityTime("1500"), new ActivityDate("10-08-2016"), new ActivityTime("1800"), new Completed(Completed.UNCOMPLETED_ACTIVITY)) .build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadActivityManagerWithSampleData(ActivityManager ab) {

        try {
            ab.addFloatingTask(new Activity(floatingTask));
            ab.addTask(new Activity(task));
            ab.addEvent(new Activity(event));
        } catch (UniqueActivityList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestActivity[] getTypicalTask() {
        return new TestActivity[]{task};
    }
    
    public TestActivity[] getTypicalFloatingTask() {
        return new TestActivity[]{floatingTask};
    }
    
    public TestActivity[] getTypicalEvent() {
        return new TestActivity[]{event};
    }

    public ActivityManager getTypicalActivityManager(){
        ActivityManager ab = new ActivityManager();
        loadActivityManagerWithSampleData(ab);
        return ab;
    }
}
