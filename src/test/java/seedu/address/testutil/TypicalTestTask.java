package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTask {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTask() {
        try {
            alice =  new TestBuilder().withName("Alice Pauline").withTags("friends").build();
            benson = new TestBuilder().withName("Benson Meier").withTags("owesMoney", "friends").build();
            carl = new TestBuilder().withName("Carl Kurz").build();
            daniel = new TestBuilder().withName("Daniel Meier").build();
            elle = new TestBuilder().withName("Elle Meyer").build();
            fiona = new TestBuilder().withName("Fiona Kunz").build();
            george = new TestBuilder().withName("George Best").build();

            //Manually added
            hoon = new TestBuilder().withName("Hoon Meier").build();
            ida = new TestBuilder().withName("Ida Mueller").build();
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
