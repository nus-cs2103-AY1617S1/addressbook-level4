package seedu.gtd.testutil;

import seedu.gtd.commons.exceptions.IllegalValueException;
import seedu.gtd.model.AddressBook;
import seedu.gtd.model.task.Task;
import seedu.gtd.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("Alice Pauline").withDueDate("12345").withAddress("123, Jurong West Ave 6, #08-111")
                    .withPriority("1")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withDueDate("12345").withAddress("311, Clementi Ave 2, #02-25")
                    .withPriority("2")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPriority("3").withDueDate("12345").withAddress("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPriority("4").withDueDate("12345").withAddress("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPriority("5").withDueDate("12345").withAddress("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPriority("4").withDueDate("12345").withAddress("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPriority("2").withDueDate("12345").withAddress("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPriority("1").withDueDate("12345").withAddress("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPriority("2").withDueDate("12345").withAddress("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

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

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}

