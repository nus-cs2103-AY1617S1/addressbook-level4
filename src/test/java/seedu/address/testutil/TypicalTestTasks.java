package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, aliceEvent,benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("Alice Pauline").withDeadline("11.10.2016")
                    .withTags("friends").build();//this is a task
            aliceEvent=new TaskBuilder().withName("Alice Pauline").withDeadline("11.10.2016-16")
            		.withTags("friends").build();//this is an event with the same name as task
            benson = new TaskBuilder().withName("Benson Meier").withDeadline("11.10.2016")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withDeadline("11.10.2016").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withDeadline("11.10.2016").build();
            elle = new TaskBuilder().withName("Elle Meyer").withDeadline("11.10.2016").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withDeadline("11.10.2016").build();
            george = new TaskBuilder().withName("George Best").withDeadline("11.10.2016").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withDeadline("11.10.2016").build();
            ida = new TaskBuilder().withName("Ida Mueller").withDeadline("11.10.2016").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        
            ab.addTask(new Task(alice));
            ab.addTask(new Task(aliceEvent));
            ab.addTask(new Task(benson));
            ab.addTask(new Task(carl));
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, aliceEvent,benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
