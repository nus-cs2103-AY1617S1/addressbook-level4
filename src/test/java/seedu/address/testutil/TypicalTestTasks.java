package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.item.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("Meet Alice Pauline").withPriority("high").build();
            benson = new TaskBuilder().withName("Meet Benson Meier").withPriority("high").build();
            carl = new TaskBuilder().withName("Meet Carl Kurz").withPriority("high").build();
            daniel = new TaskBuilder().withName("Meet Daniel Meier").withPriority("medium").build();
            elle = new TaskBuilder().withName("Meet Elle Meyer").withPriority("medium").build();
            fiona = new TaskBuilder().withName("Meet Fiona Kunz").withPriority("medium").build();
            george = new TaskBuilder().withName("Meet George Best").withPriority("low").build();

            //Manually added
            hoon = new TaskBuilder().withName("Meet Hoon Meier").withPriority("low").build();
            ida = new TaskBuilder().withName("Meet Ida Mueller").withPriority("low").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        ab.addTask(new Task(alice));
        ab.addTask(new Task(benson));
        ab.addTask(new Task(carl));
        ab.addTask(new Task(daniel));
        ab.addTask(new Task(elle));
        ab.addTask(new Task(fiona));
        ab.addTask(new Task(george));
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
