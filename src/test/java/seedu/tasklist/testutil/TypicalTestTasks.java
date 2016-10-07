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
            alice =  new TaskBuilder().withTitle("Alice Pauline").withDueDate("123, Jurong West Ave 6, #08-111")
                    .withDescription("alice@gmail.com").withStartDate("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withTitle("Benson Meier").withDueDate("311, Clementi Ave 2, #02-25")
                    .withDescription("johnd@gmail.com").withStartDate("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withTitle("Carl Kurz").withStartDate("95352563").withDescription("heinz@yahoo.com").withDueDate("wall street").build();
            daniel = new TaskBuilder().withTitle("Daniel Meier").withStartDate("87652533").withDescription("cornelia@google.com").withDueDate("10th street").build();
            elle = new TaskBuilder().withTitle("Elle Meyer").withStartDate("9482224").withDescription("werner@gmail.com").withDueDate("michegan ave").build();
            fiona = new TaskBuilder().withTitle("Fiona Kunz").withStartDate("9482427").withDescription("lydia@gmail.com").withDueDate("little tokyo").build();
            george = new TaskBuilder().withTitle("George Best").withStartDate("9482442").withDescription("anna@google.com").withDueDate("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withTitle("Hoon Meier").withStartDate("8482424").withDescription("stefan@mail.com").withDueDate("little india").build();
            ida = new TaskBuilder().withTitle("Ida Mueller").withStartDate("8482131").withDescription("hans@google.com").withDueDate("chicago ave").build();
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
