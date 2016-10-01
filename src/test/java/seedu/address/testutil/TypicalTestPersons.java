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
            alice =  new PersonBuilder().withName("Alice Pauline").withAddress("123, Jurong West Ave 6, #08-111")
                    .withEmail("alice@gmail.com").withType("task")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@gmail.com").withType("task")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withType("task").withEmail("heinz@yahoo.com").withAddress("wall street").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withType("task").withEmail("cornelia@google.com").withAddress("10th street").build();
            elle = new PersonBuilder().withName("Elle Meyer").withType("task").withEmail("werner@gmail.com").withAddress("michegan ave").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withType("task").withEmail("lydia@gmail.com").withAddress("little tokyo").build();
            george = new PersonBuilder().withName("George Best").withType("task").withEmail("anna@google.com").withAddress("4th street").build();

            //Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withType("task").withEmail("stefan@mail.com").withAddress("little india").build();
            ida = new PersonBuilder().withName("Ida Mueller").withType("task").withEmail("hans@google.com").withAddress("chicago ave").build();
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
