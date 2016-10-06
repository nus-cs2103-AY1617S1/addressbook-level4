package seedu.taskman.testutil;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.TaskMan;
import seedu.taskman.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withTitle("Alice Pauline").withStatus("123, Jurong West Ave 6, #08-111")
                    .withRecurrence("alice@gmail.com").withDeadline("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withTitle("Benson Meier").withStatus("311, Clementi Ave 2, #02-25")
                    .withRecurrence("johnd@gmail.com").withDeadline("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withTitle("Carl Kurz").withDeadline("95352563").withRecurrence("heinz@yahoo.com").withSchedule("wall street").build();
            daniel = new TaskBuilder().withTitle("Daniel Meier").withDeadline("87652533").withRecurrence("cornelia@google.com").withSchedule("10th street").build();
            elle = new TaskBuilder().withTitle("Elle Meyer").withDeadline("9482224").withRecurrence("werner@gmail.com").withSchedule("michegan ave").build();
            fiona = new TaskBuilder().withTitle("Fiona Kunz").withDeadline("9482427").withRecurrence("lydia@gmail.com").withSchedule("little tokyo").build();
            george = new TaskBuilder().withTitle("George Best").withDeadline("9482442").withRecurrence("anna@google.com").withSchedule("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withTitle("Hoon Meier").withDeadline("8482424").withRecurrence("stefan@mail.com").withSchedule("little india").build();
            ida = new TaskBuilder().withTitle("Ida Mueller").withDeadline("8482131").withRecurrence("hans@google.com").withSchedule("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManWithSampleData(TaskMan ab) {

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

    public TaskMan getTypicalTaskMan(){
        TaskMan ab = new TaskMan();
        loadTaskManWithSampleData(ab);
        return ab;
    }
}
