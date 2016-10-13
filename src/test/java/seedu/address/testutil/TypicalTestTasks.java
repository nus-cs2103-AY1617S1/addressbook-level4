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
            alice =  new TaskBuilder().withName("Aid alice").withStatus("not done").build();
            benson =  new TaskBuilder().withName("Aid benson").withStatus("not done").build();
            carl =  new TaskBuilder().withName("Aid carl").withStatus("not done").build();
            daniel =  new TaskBuilder().withName("Aid daniel").withStatus("not done").build();
            elle =  new TaskBuilder().withName("Aid elle").withStatus("not done").build();
            fiona =  new TaskBuilder().withName("Aid fiona").withStatus("not done").build();
            george =  new TaskBuilder().withName("Aid george").withStatus("not done").build();

            //Manually added
            hoon =  new TaskBuilder().withName("Aid hoon").withStatus("not done").build();
            ida =  new TaskBuilder().withName("Aid ida").withStatus("not done").build();

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
