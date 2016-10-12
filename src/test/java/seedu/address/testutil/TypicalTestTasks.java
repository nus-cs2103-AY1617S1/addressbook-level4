package seedu.address.testutil;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.activity.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask assignment1, assignment2, assignment3, assignment4, assignment5, assignment6, assignment7, assignment8, assignment9;

    public TypicalTestTasks() {
        try {
            assignment1 =  new TaskBuilder().withName("assignment1").withType("task").withNote("must do")
                    .withStartTime("1200").withStartDate("12-12-2016").build();
            assignment2 = new TaskBuilder().withName("assignment2").withType("task").withNote("excel in this")
                    .withStartTime("0900").withStartDate("10-10-2016").build();
            assignment3 = new TaskBuilder().withName("assignment3").withType("task").withStartDate("11-11-2016").withStartTime("1000").withNote("hand up on time").build();
            assignment4 = new TaskBuilder().withName("assignment4").withType("task").withStartDate("09-09-2016").withStartTime("1100").withNote("yay").build();
            assignment5 = new TaskBuilder().withName("assignment5").withType("task").withStartDate("08-08-2016").withStartTime("1200").withNote("i can do this").build();
            assignment6 = new TaskBuilder().withName("assignment6").withType("task").withStartDate("07-07-2016").withStartTime("1300").withNote("almost there").build();
            assignment7 = new TaskBuilder().withName("assignment7").withType("task").withStartDate("06-06-2016").withStartTime("1400").withNote("last 2").build();

            //Manually added
            assignment8 = new TaskBuilder().withName("assignment8").withType("task").withStartDate("05-05-2016").withStartTime("1500").withNote("last 1").build();
            assignment9 = new TaskBuilder().withName("assignment9").withType("task").withStartDate("04-04-2016").withStartTime("1700").withNote("finally").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(ActivityManager ab) {

        try {
            ab.addTask(new Activity(assignment1));
            ab.addTask(new Activity(assignment2));
            ab.addTask(new Activity(assignment3));
            ab.addTask(new Activity(assignment4));
            ab.addTask(new Activity(assignment5));
            ab.addTask(new Activity(assignment6));
            ab.addTask(new Activity(assignment7));
        } catch (UniqueActivityList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{assignment1, assignment2, assignment3, assignment4, assignment5, assignment6, assignment7};
    }

    public ActivityManager getTypicalActivityManager(){
        ActivityManager ab = new ActivityManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
