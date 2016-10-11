package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.item.*;
import seedu.address.model.item.UniqueTaskList.DuplicateTaskException;

/**
 *
 */
public class TypicalTestFloatingTasks {

    public static TestFloatingTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestFloatingTasks() {
        try {
            alice =  new FloatingTaskBuilder().withName("Meet Alice Pauline").withPriority("1").build();
            benson = new FloatingTaskBuilder().withName("Meet Benson Meier").withPriority("2").build();
            carl = new FloatingTaskBuilder().withName("Meet Carl Kurz").withPriority("3").build();
            daniel = new FloatingTaskBuilder().withName("Meet Daniel Meier").withPriority("4").build();
            elle = new FloatingTaskBuilder().withName("Meet Elle Meyer").withPriority("5").build();
            fiona = new FloatingTaskBuilder().withName("Meet Fiona Kunz").withPriority("6").build();
            george = new FloatingTaskBuilder().withName("Meet George Best").withPriority("7").build();

            //Manually added
            hoon = new FloatingTaskBuilder().withName("Meet Hoon Meier").withPriority("8").build();
            ida = new FloatingTaskBuilder().withName("Meet Ida Mueller").withPriority("9").build();
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

    public TestFloatingTask[] getTypicalPersons() {
        return new TestFloatingTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
