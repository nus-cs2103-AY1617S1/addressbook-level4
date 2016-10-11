package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.item.*;
import seedu.address.model.item.UniqueTaskList.DuplicateTaskException;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("Meet Alice Pauline").withPriority("1").build();
            benson = new TaskBuilder().withName("Meet Benson Meier").withPriority("2").build();
            carl = new TaskBuilder().withName("Meet Carl Kurz").withPriority("3").build();
            daniel = new TaskBuilder().withName("Meet Daniel Meier").withPriority("4").build();
            elle = new TaskBuilder().withName("Meet Elle Meyer").withPriority("5").build();
            fiona = new TaskBuilder().withName("Meet Fiona Kunz").withPriority("6").build();
            george = new TaskBuilder().withName("Meet George Best").withPriority("7").build();

            //Manually added
            hoon = new TaskBuilder().withName("Meet Hoon Meier").withPriority("8").build();
            ida = new TaskBuilder().withName("Meet Ida Mueller").withPriority("9").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        try {
            ab.addFloatingTask(new Task(alice));
            ab.addFloatingTask(new Task(benson));
            ab.addFloatingTask(new Task(carl));
            ab.addFloatingTask(new Task(daniel));
            ab.addFloatingTask(new Task(elle));
            ab.addFloatingTask(new Task(fiona));
            ab.addFloatingTask(new Task(george));
        } catch (DuplicateTaskException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
