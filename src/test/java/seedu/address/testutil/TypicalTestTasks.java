package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("Alice Pauline").withEndTime("5:00pm")
                    .withStartTime("5:00am").withDate("06/06/15")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withEndTime("5:00pm")
                    .withStartTime("5:30am").withDate("04/11/13")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withDate("07/10/13").withStartTime("3:21am").withEndTime("4:10pm").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withDate("12/09/13").withStartTime("5:12pm").withEndTime("11:32am").build();
            elle = new TaskBuilder().withName("Elle Meyer").withDate("06/06/11").withStartTime("5:00am").withEndTime("5:12pm").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withDate("12/04/12").withStartTime("6:12pm").withEndTime("5:23pm").build();
            george = new TaskBuilder().withName("George Best").withDate("12/04/12").withStartTime("5:12am").withEndTime("6:11pm").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withDate("12/12/12").withStartTime("3:00pm").withEndTime("4:12am").build();
            ida = new TaskBuilder().withName("Ida Mueller").withDate("11/12/12").withStartTime("6:12pm").withEndTime("3:09pm").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(alice));
            ab.addTask(new Task(benson));
            ab.addTask(new Task(carl));
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
