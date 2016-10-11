package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskBook;
import seedu.address.model.task.Task;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice;

    public static TestTask benson;

    public static TestTask carl;

    public static TestTask daniel;

    public static TestTask elle;

    public static TestTask fiona;

    public static TestTask george;

    public static TestTask hoon;

    public static TestTask ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline").build();
            benson = new TaskBuilder().withName("Benson Meier").build();
            carl = new TaskBuilder().withName("Carl Kurz").build();
            daniel = new TaskBuilder().withName("Daniel Meier").build();
            elle = new TaskBuilder().withName("Elle Meyer").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").build();
            george = new TaskBuilder().withName("George Best").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").build();
            ida = new TaskBuilder().withName("Ida Mueller").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskBook ab) {
        ab.addTask(new Task(alice));
        ab.addTask(new Task(benson));
        ab.addTask(new Task(carl));
        ab.addTask(new Task(daniel));
        ab.addTask(new Task(elle));
        ab.addTask(new Task(fiona));
        ab.addTask(new Task(george));
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskBook getTypicalAddressBook() {
        TaskBook ab = new TaskBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
