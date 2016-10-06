package harmony.testutil;

import harmony.commons.exceptions.IllegalValueException;
import harmony.model.TaskManager;
import harmony.model.task.*;

/**
 *
 */
public class TypicalTestTask {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTask() {
        try {
            alice =  new TaskBuilder().withName("Alice Pauline")
                    .withEmail("alice@gmail.com").withPhone("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier")
                    .withEmail("johnd@gmail.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPhone("95352563").withEmail("heinz@yahoo.com").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPhone("87652533").withEmail("cornelia@google.com").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPhone("9482224").withEmail("werner@gmail.com").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPhone("9482427").withEmail("lydia@gmail.com").build();
            george = new TaskBuilder().withName("George Best").withPhone("9482442").withEmail("anna@google.com").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPhone("8482424").withEmail("stefan@mail.com").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPhone("8482131").withEmail("hans@google.com").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
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
