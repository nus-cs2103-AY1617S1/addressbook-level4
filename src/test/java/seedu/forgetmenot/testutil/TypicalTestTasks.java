package seedu.forgetmenot.testutil;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.model.TaskManager;
import seedu.forgetmenot.model.task.Task;
import seedu.forgetmenot.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("Alice Pauline").withEndTime("11:59pm")
                    .withStartTime("11:59pm").withDone(false).withRecurrence("")
                    .build();
            benson = new TaskBuilder().withName("Benson Meier").withEndTime("11:59pm")
                    .withStartTime("11:59pm").withDone(false).withRecurrence("")
                    .build();
            carl = new TaskBuilder().withName("Carl Kurz").withDone(false).withStartTime("11:59pm").withEndTime("11:59pm").withRecurrence("").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withDone(false).withStartTime("11:59pm").withEndTime("11:59pm").withRecurrence("").build();
            elle = new TaskBuilder().withName("Elle Meyer").withDone(false).withStartTime("11:59pm").withEndTime("11:59pm").withRecurrence("").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withDone(false).withStartTime("11:59pm").withEndTime("11:59pm").withRecurrence("").build();
            george = new TaskBuilder().withName("George Best").withDone(false).withStartTime("11:59pm").withEndTime("11:59pm").withRecurrence("").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withDone(false).withStartTime("11:59pm").withEndTime("11:59pm").withRecurrence("").build();
            ida = new TaskBuilder().withName("Ida Mueller").withDone(false).withStartTime("11:59pm").withEndTime("11:59pm").withRecurrence("").build();
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
