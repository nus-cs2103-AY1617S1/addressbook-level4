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
            alice =  new TaskBuilder().withName("Alice Pauline").withDueDate("21-10-2016").withAddress("123, Jurong West Ave 6, #08-111")
                    .withPriority("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withDueDate("21-10-2016").withAddress("311, Clementi Ave 2, #02-25")
                    .withPriority("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPriority("95352563").withDueDate("21-10-2016").withAddress("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPriority("87652533").withDueDate("21-10-2016").withAddress("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPriority("9482224").withDueDate("21-10-2016").withAddress("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPriority("9482427").withDueDate("21-10-2016").withAddress("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPriority("9482442").withDueDate("21-10-2016").withAddress("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPriority("8482424").withDueDate("21-10-2016").withAddress("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPriority("8482131").withDueDate("21-10-2016").withAddress("chicago ave").build();
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

