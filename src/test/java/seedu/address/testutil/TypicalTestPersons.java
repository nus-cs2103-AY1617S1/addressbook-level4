package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice =  new TaskBuilder().withName("Alice Pauline").withDeadline("12-36-08")
                    .withPriority("1")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withDeadline("311202")
                    .withPriority("2")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPriority("3").withDeadline("121212").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPriority("4").withDeadline("101010").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPriority("5").withDeadline("111111").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPriority("2").withDeadline("000000").build();
            george = new TaskBuilder().withName("George Best").withPriority("1").withDeadline("456789").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPriority("1").withDeadline("000000").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPriority("1").withDeadline("999999").build();
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
