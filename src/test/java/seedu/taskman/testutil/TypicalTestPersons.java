package seedu.taskman.testutil;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.AddressBook;
import seedu.taskman.model.task.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice =  new PersonBuilder().withTitle("Alice Pauline").withAddress("123, Jurong West Ave 6, #08-111")
                    .withEmail("alice@gmail.com").withDeadline("85355255")
                    .withTags("friends").build();
            benson = new PersonBuilder().withTitle("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@gmail.com").withDeadline("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withTitle("Carl Kurz").withDeadline("95352563").withEmail("heinz@yahoo.com").withAddress("wall street").build();
            daniel = new PersonBuilder().withTitle("Daniel Meier").withDeadline("87652533").withEmail("cornelia@google.com").withAddress("10th street").build();
            elle = new PersonBuilder().withTitle("Elle Meyer").withDeadline("9482224").withEmail("werner@gmail.com").withAddress("michegan ave").build();
            fiona = new PersonBuilder().withTitle("Fiona Kunz").withDeadline("9482427").withEmail("lydia@gmail.com").withAddress("little tokyo").build();
            george = new PersonBuilder().withTitle("George Best").withDeadline("9482442").withEmail("anna@google.com").withAddress("4th street").build();

            //Manually added
            hoon = new PersonBuilder().withTitle("Hoon Meier").withDeadline("8482424").withEmail("stefan@mail.com").withAddress("little india").build();
            ida = new PersonBuilder().withTitle("Ida Mueller").withDeadline("8482131").withEmail("hans@google.com").withAddress("chicago ave").build();
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
        } catch (UniqueTaskList.DuplicatePersonException e) {
            assert false : "not possible";
        }
    }

    public TestPerson[] getTypicalPersons() {
        return new TestPerson[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
