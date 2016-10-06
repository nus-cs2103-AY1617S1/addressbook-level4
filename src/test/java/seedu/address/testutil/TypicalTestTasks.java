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
            alice =  new TaskBuilder().withName("Alice Pauline").withDate("123, Jurong West Ave 6, #08-111")
                    .withEndDateTime("alice@gmail.com").withStartDateTime("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withDate("311, Clementi Ave 2, #02-25")
                    .withEndDateTime("johnd@gmail.com").withStartDateTime("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withStartDateTime("95352563").withEndDateTime("heinz@yahoo.com").withDate("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withStartDateTime("87652533").withEndDateTime("cornelia@google.com").withDate("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withStartDateTime("9482224").withEndDateTime("werner@gmail.com").withDate("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withStartDateTime("9482427").withEndDateTime("lydia@gmail.com").withDate("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withStartDateTime("9482442").withEndDateTime("anna@google.com").withDate("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withStartDateTime("8482424").withEndDateTime("stefan@mail.com").withDate("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withStartDateTime("8482131").withEndDateTime("hans@google.com").withDate("chicago ave").build();
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
