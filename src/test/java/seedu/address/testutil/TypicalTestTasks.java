package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask one, two, three, four;

    public TypicalTestTasks() {
        try {
            one = new DatedTaskBuilder().withName("buy milk").withDescription("lots of it")
                    .withDate("11.11.11").withTime("1111").build();

            
            
            
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addPerson(new Task(one));
//            ab.addPerson(new Task(two));
//            ab.addPerson(new Task(three));
//            ab.addPerson(new Task(four));
        } catch (UniquePersonList.DuplicatePersonException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{one};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
