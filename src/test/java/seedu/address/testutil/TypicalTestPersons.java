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
            alice =  new PersonBuilder().withType("event").withName("Alice").withStartDate("2016-06-06").withStartTime("18:00")
                    .withEndDate("2016-07-07").withEndTime("19:00")
                    .withTags("friends").build();
            benson = new PersonBuilder().withType("event").withName("Benson").withStartDate("2016-07-07").withStartTime("08:00")
                    .withEndDate("2016-09-16").withEndTime("13:00")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withType("event").withName("Carl").withStartDate("2016-10-07").withStartTime("12:00")
                    .withEndDate("2016-11-22").withEndTime("18:00").build();
            daniel = new PersonBuilder().withType("deadline").withName("Daniel").withStartDate("").withStartTime("")
                    .withEndDate("2016-11-28").withEndTime("23:00").build();
            elle = new PersonBuilder().withType("deadline").withName("Elle").withStartDate("").withStartTime("")
                    .withEndDate("2016-09-20").withEndTime("23:59").build();
            fiona = new PersonBuilder().withType("task").withName("Fiona").withStartDate("").withStartTime("")
                    .withEndDate("").withEndTime("").build();
            george = new PersonBuilder().withType("task").withName("George").withStartDate("").withStartTime("")
                    .withEndDate("").withEndTime("").build();

            //Manually added
            hoon = new PersonBuilder().withType("event").withName("Hoon").withStartDate("2016-12-10").withStartTime("08:00")
                    .withEndDate("2016-12-10").withEndTime("23:59").build();
            ida = new PersonBuilder().withType("event").withName("Ida").withStartDate("2017-01-07").withStartTime("08:00")
                    .withEndDate("2017-01-30").withEndTime("13:00").build();
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
