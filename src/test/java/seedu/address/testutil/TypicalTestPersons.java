package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestEntry alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice =  new PersonBuilder().withName("Alice Pauline").withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").build();
            daniel = new PersonBuilder().withName("Daniel Meier").build();
            elle = new PersonBuilder().withName("Elle Meyer").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").build();
            george = new PersonBuilder().withName("George Best").build();

            //Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").build();
            ida = new PersonBuilder().withName("Ida Mueller").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addPerson(new FloatingTask(alice));
            ab.addPerson(new FloatingTask(benson));
            ab.addPerson(new FloatingTask(carl));
            ab.addPerson(new FloatingTask(daniel));
            ab.addPerson(new FloatingTask(elle));
            ab.addPerson(new FloatingTask(fiona));
            ab.addPerson(new FloatingTask(george));
        } catch (UniquePersonList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestEntry[] getTypicalPersons() {
        return new TestEntry[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
