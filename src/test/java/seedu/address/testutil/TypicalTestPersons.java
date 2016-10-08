package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.item.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice =  new PersonBuilder().withName("Alice Pauline").withTime("12:01")
                    .withDate("2016-08-08").withPhone("85355255")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withTime("01:01")
                    .withDate("2016-12-06").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withPhone("95352563").withDate("2015-01-01").withTime("00:00").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withPhone("87652533").withDate("2011-08-09").withTime("23:59").build();
            elle = new PersonBuilder().withName("Elle Meyer").withPhone("9482224").withDate("2019-09-09").withTime("10:30").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427").withDate("2016-01-01").withTime("13:59").build();
            george = new PersonBuilder().withName("George Best").withPhone("9482442").withDate("2017-01-03").withTime("15:00").build();

            //Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withPhone("8482424").withDate("2016-09-30").withTime("21:14").build();
            ida = new PersonBuilder().withName("Ida Mueller").withPhone("8482131").withDate("2016-12-31").withTime("09:00").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addItem(new Item(alice));
            ab.addItem(new Item(benson));
            ab.addItem(new Item(carl));
            ab.addItem(new Item(daniel));
            ab.addItem(new Item(elle));
            ab.addItem(new Item(fiona));
            ab.addItem(new Item(george));
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
