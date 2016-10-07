package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice =  new PersonBuilder().withName("Alice Pauline").withAddress("123, Jurong West Ave 6, #08-111")
                    .withPhone("85355255")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withPhone("95352563").withAddress("wall street").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withPhone("87652533").withAddress("10th street").build();
            elle = new PersonBuilder().withName("Elle Meyer").withPhone("9482224").withAddress("michegan ave").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427").withAddress("little tokyo").build();
            george = new PersonBuilder().withName("George Best").withPhone("9482442").withAddress("4th street").build();

            //Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withPhone("8482424").withAddress("little india").build();
            ida = new PersonBuilder().withName("Ida Mueller").withPhone("8482131").withAddress("chicago ave").build();
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
