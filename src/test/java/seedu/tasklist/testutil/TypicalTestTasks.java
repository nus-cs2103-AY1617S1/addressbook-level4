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
            alice = new TaskBuilder().withTitle("Alice Pauline").withEndDateTime("02022002")
                    .withDescription("studying in NUS").withStartDateTime("01012001").withTags("friends").build();
            benson = new TaskBuilder().withTitle("Benson Meier").withEndDateTime("04042004")
                    .withDescription("have a hard time in comsci").withStartDateTime("30032003")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withTitle("Carl Kurz").withStartDateTime("05052005")
                    .withDescription("likes to play too hard with Bensen").withEndDateTime("06062006").build();
            daniel = new TaskBuilder().withTitle("Daniel Meier Failure").withStartDateTime("05112014")
                    .withDescription("likes Carl").withEndDateTime("10112018").build();
            elle = new TaskBuilder().withTitle("Elle Meyer").withStartDateTime("01012001")
                    .withDescription("love triangle with Daniel").withEndDateTime("10042012").build();
            fiona = new TaskBuilder().withTitle("Fiona Kunz Failure").withStartDateTime("03032003")
                    .withDescription("ditched Elle").withEndDateTime("05012013").build();
            george = new TaskBuilder().withTitle("George Best").withStartDateTime("04042004")
                    .withDescription("get together with Fiona").withEndDateTime("05062003").build();

            // Manually added
            hoon = new TaskBuilder().withTitle("Hoon Meier").withStartDateTime("05062001").withDescription("likes to study")
                    .withEndDateTime("01022004").build();
            ida = new TaskBuilder().withTitle("Ida Mueller").withStartDateTime("07062001")
                    .withDescription("spend too much time googling").withEndDateTime("03042005").build();
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
        return new TestTask[] { alice, benson, carl, daniel, elle, fiona, george };
    }

    public TaskList getTypicalTaskList() {
        TaskList ab = new TaskList();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}
