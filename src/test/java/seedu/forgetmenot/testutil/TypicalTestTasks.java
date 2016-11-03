package seedu.forgetmenot.testutil;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.model.TaskManager;
import seedu.forgetmenot.model.task.Task;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask apples, bananas, call, deed, egypt, flowers, garage, hide, iphone;

    public TypicalTestTasks() {
        try {
            apples = new TaskBuilder().withName("buy apples").withStartTime("10:30pm tmr").withEndTime("11pm tmr")
                    .withDone(false).withRecurrence("").build();
            bananas = new TaskBuilder().withName("buy bananas").withStartTime("11am tmr").withEndTime("1pm tmr")
                    .withDone(false).withRecurrence("").build();
            call = new TaskBuilder().withName("call dad").withDone(false).withEndTime("10am tmr")
                    .withStartTime("11am tmr").withRecurrence("").build();
            deed = new TaskBuilder().withName("give bananas away").withDone(false).withEndTime("5pm two days later")
                    .withStartTime("6pm two days later").withRecurrence("").build();
            egypt = new TaskBuilder().withName("plan trip to egypt").withDone(false).withStartTime("10am one month later")
                    .withEndTime("11am one month later").withRecurrence("").build();
            flowers = new TaskBuilder().withName("flowers for joan").withDone(false).withStartTime("11:59pm today")
                    .withEndTime("11:59pm today").withRecurrence("").build();
            garage = new TaskBuilder().withName("Garage sale").withDone(false).withStartTime("9am three days later")
                    .withEndTime("11am three days later").withRecurrence("").build();

            // Manually added
            hide = new TaskBuilder().withName("hide bananas").withDone(false).withStartTime("11:59pm four months later")
                    .withEndTime("11:59pm five months later").withRecurrence("").build();
            iphone = new TaskBuilder().withName("iphone").withDone(false).withStartTime("11pm five months later")
                    .withEndTime("11:30pm five months later").withRecurrence("").build();
        } catch (IllegalValueException e) {
            assert false : "TypicalTestTasks data details are invalid";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        ab.addTask(new Task(apples));
        ab.addTask(new Task(bananas));
        ab.addTask(new Task(call));
        ab.addTask(new Task(deed));
        ab.addTask(new Task(egypt));
        ab.addTask(new Task(flowers));
        ab.addTask(new Task(garage));
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] {flowers, call, bananas, apples, deed, garage, egypt};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
