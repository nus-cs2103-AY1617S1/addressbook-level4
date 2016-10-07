package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("Alice Pauline").withAddress("123, Jurong West Ave 6, #08-111")
                    .withTime("alice@gmail.com").withDate("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withTime("johnd@gmail.com").withDate("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withDate("95352563").withTime("heinz@yahoo.com").withAddress("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withDate("87652533").withTime("cornelia@google.com").withAddress("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withDate("9482224").withTime("werner@gmail.com").withAddress("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withDate("9482427").withTime("lydia@gmail.com").withAddress("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withDate("9482442").withTime("anna@google.com").withAddress("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withDate("8482424").withTime("stefan@mail.com").withAddress("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withDate("8482131").withTime("hans@google.com").withAddress("chicago ave").build();
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
