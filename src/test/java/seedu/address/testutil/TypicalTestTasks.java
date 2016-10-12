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
            alice =  new TaskBuilder().withName("Alice Pauline").withEndTime("123, Jurong West Ave 6, #08-111")
                    .withStartTime("alice@gmail.com").withDate("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withEndTime("311, Clementi Ave 2, #02-25")
                    .withStartTime("johnd@gmail.com").withDate("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withDate("95352563").withStartTime("heinz@yahoo.com").withEndTime("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withDate("87652533").withStartTime("cornelia@google.com").withEndTime("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withDate("9482224").withStartTime("werner@gmail.com").withEndTime("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withDate("9482427").withStartTime("lydia@gmail.com").withEndTime("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withDate("9482442").withStartTime("anna@google.com").withEndTime("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withDate("8482424").withStartTime("stefan@mail.com").withEndTime("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withDate("8482131").withStartTime("hans@google.com").withEndTime("chicago ave").build();
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
