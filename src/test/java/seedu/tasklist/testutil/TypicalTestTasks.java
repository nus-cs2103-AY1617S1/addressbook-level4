package seedu.tasklist.testutil;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withTitle("Alice Pauline").withDueDate("020202")
                    .withDescription("studying in NUS").withStartDate("010101")
                    .withTags("friends").build();
            benson = new TaskBuilder().withTitle("Benson Meier").withDueDate("040404")
                    .withDescription("have a hard time in comsci").withStartDate("300303")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withTitle("Carl Kurz").withStartDate("050505").withDescription("play too hard with Bensen").withDueDate("060606").build();
            daniel = new TaskBuilder().withTitle("Daniel Meier").withStartDate("051114").withDescription("likes Carl").withDueDate("101118").build();
            elle = new TaskBuilder().withTitle("Elle Meyer").withStartDate("010101").withDescription("love triangle with Daniel").withDueDate("100412").build();
            fiona = new TaskBuilder().withTitle("Fiona Kunz").withStartDate("030303").withDescription("ditched Elle").withDueDate("050113").build();
            george = new TaskBuilder().withTitle("George Best").withStartDate("040404").withDescription("get together with Fiona").withDueDate("050603").build();

            //Manually added
            hoon = new TaskBuilder().withTitle("Hoon Meier").withStartDate("050601").withDescription("likes to study").withDueDate("010204").build();
            ida = new TaskBuilder().withTitle("Ida Mueller").withStartDate("070601").withDescription("spend too much time googling").withDueDate("030405").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(TaskList ab) {

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

    public TaskList getTypicalTaskList(){
        TaskList ab = new TaskList();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}
