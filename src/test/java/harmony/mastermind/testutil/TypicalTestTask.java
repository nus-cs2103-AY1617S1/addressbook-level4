package harmony.mastermind.testutil;

import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.model.TaskManager;
import harmony.mastermind.model.task.*;

/**
 *
 */
public class TypicalTestTask {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTask() {
        /*
        try {
            alice =  new TaskBuilder().withName("Alice Pauline")
                    .withDate("alice@gmail.com").withTime("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier")
                    .withDate("johnd@gmail.com").withTime("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withTime("95352563").withDate("heinz@yahoo.com").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withTime("87652533").withDate("cornelia@google.com").build();
            elle = new TaskBuilder().withName("Elle Meyer").withTime("9482224").withDate("werner@gmail.com").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withTime("9482427").withDate("lydia@gmail.com").build();
            george = new TaskBuilder().withName("George Best").withTime("9482442").withDate("anna@google.com").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withTime("8482424").withDate("stefan@mail.com").build();
            ida = new TaskBuilder().withName("Ida Mueller").withTime("8482131").withDate("hans@google.com").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
        */
    }

    public static void loadAddressBookWithSampleData(TaskManager ab) {

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

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalAddressBook(){
        TaskManager ab = new TaskManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
