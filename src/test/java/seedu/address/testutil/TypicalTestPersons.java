package seedu.address.testutil;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.model.AddressBook;
import seedu.dailyplanner.model.task.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice =  new PersonBuilder().withName("Alice Pauline").withAddress("6pm")
                    .withEmail("3pm").withPhone("today")
                    .withTags("friends").withCompletion(false).build();
            
            benson = new PersonBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@gmail.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").withCompletion(false).build();
            carl = new PersonBuilder().withName("Carl Kurz").withPhone("95352563").withEmail("heinz@yahoo.com").withAddress("wall street").withCompletion(false).build();
            daniel = new PersonBuilder().withName("Daniel Meier").withPhone("87652533").withEmail("cornelia@google.com").withAddress("10th street").withCompletion(false).build();
            elle = new PersonBuilder().withName("Elle Meyer").withPhone("9482224").withEmail("werner@gmail.com").withAddress("michegan ave").withCompletion(false).build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427").withEmail("lydia@gmail.com").withAddress("little tokyo").withCompletion(false).build();
            george = new PersonBuilder().withName("George Best").withPhone("9482442").withEmail("anna@google.com").withAddress("4th street").withCompletion(false).build();

            //Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withPhone("today").withEmail("2pm").withAddress("5pm").withCompletion(false).build();
            ida = new PersonBuilder().withName("Ida Mueller").withPhone("tomorrow").withEmail("1am").withAddress("2am").withCompletion(false).build();
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
