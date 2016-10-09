package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice =  new TaskBuilder().withName("Alice Pauline").withDeadline("123, Jurong West Ave 6, #08-111")
                    .withPriority("alice@gmail.com")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withDeadline("311, Clementi Ave 2, #02-25")
                    .withPriority("johnd@gmail.com")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPriority("heinz@yahoo.com").withDeadline("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPriority("cornelia@google.com").withDeadline("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPriority("werner@gmail.com").withDeadline("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPriority("lydia@gmail.com").withDeadline("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPriority("anna@google.com").withDeadline("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPriority("stefan@mail.com").withDeadline("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPriority("hans@google.com").withDeadline("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addPerson(new Task(alice));
            ab.addPerson(new Task(benson));
            ab.addPerson(new Task(carl));
            ab.addPerson(new Task(daniel));
            ab.addPerson(new Task(elle));
            ab.addPerson(new Task(fiona));
            ab.addPerson(new Task(george));
        } catch (UniquePersonList.DuplicatePersonException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
