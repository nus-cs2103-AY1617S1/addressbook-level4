package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ToDoList;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("Alice Pauline").withTillDate("123, Jurong West Ave 6, #08-111")
                    .withFromDate("alice@gmail.com").withDetail("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withTillDate("311, Clementi Ave 2, #02-25")
                    .withFromDate("johnd@gmail.com").withDetail("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withDetail("95352563").withFromDate("heinz@yahoo.com").withTillDate("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withDetail("87652533").withFromDate("cornelia@google.com").withTillDate("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withDetail("9482224").withFromDate("werner@gmail.com").withTillDate("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withDetail("9482427").withFromDate("lydia@gmail.com").withTillDate("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withDetail("9482442").withFromDate("anna@google.com").withTillDate("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withDetail("8482424").withFromDate("stefan@mail.com").withTillDate("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withDetail("8482131").withFromDate("hans@google.com").withTillDate("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadToDoListWithSampleData(ToDoList ab) {

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

    public ToDoList getTypicalToDoList(){
        ToDoList ab = new ToDoList();
        loadToDoListWithSampleData(ab);
        return ab;
    }
}
