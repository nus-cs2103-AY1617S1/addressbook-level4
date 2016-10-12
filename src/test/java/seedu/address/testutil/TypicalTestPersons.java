package seedu.address.testutil;

import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.model.AddressBook;
import seedu.emeraldo.model.task.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice =  new PersonBuilder().withName("Alice Pauline").withAddress("123, Jurong West Ave 6, #08-111")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withAddress("wall street").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withAddress("10th street").build();
            elle = new PersonBuilder().withName("Elle Meyer").withAddress("michegan ave").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withAddress("little tokyo").build();
            george = new PersonBuilder().withName("George Best").withAddress("4th street").build();

            //Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withAddress("little india").build();
            ida = new PersonBuilder().withName("Ida Mueller").withAddress("chicago ave").build();
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
        } catch (UniquePersonList.DuplicateTaskException e) {
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
