package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.AddressBook;
import seedu.task.model.person.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

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
            ab.addPerson(new Person(alice));
            ab.addPerson(new Person(benson));
            ab.addPerson(new Person(carl));
            ab.addPerson(new Person(daniel));
            ab.addPerson(new Person(elle));
            ab.addPerson(new Person(fiona));
            ab.addPerson(new Person(george));
        } catch (UniquePersonList.DuplicatePersonException e) {
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
