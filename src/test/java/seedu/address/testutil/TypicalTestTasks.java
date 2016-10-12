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
            alice =  new TaskBuilder().withName("Alice Pauline").withAddress("123, Jurong West Ave 6, #08-111")
                    .withStartTime("alice@gmail.com").withPhone("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withStartTime("johnd@gmail.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPhone("95352563").withStartTime("heinz@yahoo.com").withAddress("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPhone("87652533").withStartTime("cornelia@google.com").withAddress("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPhone("9482224").withStartTime("werner@gmail.com").withAddress("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPhone("9482427").withStartTime("lydia@gmail.com").withAddress("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPhone("9482442").withStartTime("anna@google.com").withAddress("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPhone("8482424").withStartTime("stefan@mail.com").withAddress("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPhone("8482131").withStartTime("hans@google.com").withAddress("chicago ave").build();
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
