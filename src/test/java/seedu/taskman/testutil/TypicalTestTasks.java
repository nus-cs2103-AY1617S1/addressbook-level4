package seedu.taskman.testutil;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.AddressBook;
import seedu.taskman.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withTitle("Alice Pauline").withAddress("123, Jurong West Ave 6, #08-111")
                    .withEmail("alice@gmail.com").withDeadline("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withTitle("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@gmail.com").withDeadline("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withTitle("Carl Kurz").withDeadline("95352563").withEmail("heinz@yahoo.com").withAddress("wall street").build();
            daniel = new TaskBuilder().withTitle("Daniel Meier").withDeadline("87652533").withEmail("cornelia@google.com").withAddress("10th street").build();
            elle = new TaskBuilder().withTitle("Elle Meyer").withDeadline("9482224").withEmail("werner@gmail.com").withAddress("michegan ave").build();
            fiona = new TaskBuilder().withTitle("Fiona Kunz").withDeadline("9482427").withEmail("lydia@gmail.com").withAddress("little tokyo").build();
            george = new TaskBuilder().withTitle("George Best").withDeadline("9482442").withEmail("anna@google.com").withAddress("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withTitle("Hoon Meier").withDeadline("8482424").withEmail("stefan@mail.com").withAddress("little india").build();
            ida = new TaskBuilder().withTitle("Ida Mueller").withDeadline("8482131").withEmail("hans@google.com").withAddress("chicago ave").build();
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
