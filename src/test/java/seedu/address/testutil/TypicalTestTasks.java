package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask one, two, three;

    public TypicalTestTasks() {
        try {
            one = new DatedTaskBuilder().withName("buy milk").withDescription("lots of it")
                    .withDate("11-11-2017").withTime("1111").withStatus("NONE").build();
            two = new DatedTaskBuilder().withName("buy some milk").withDescription("not so much")
                    .withDate("11-11-2017").withTime("1111").withStatus("NONE").build();
            three = new DatedTaskBuilder().withName("buy some milk").withDescription("just a little")
                    .withDate("12-12-2017").withTime("1111").withStatus("NONE").build();
            
            
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addPerson(new Task(one));
            //ab.addPerson(new Task(two));
            //ab.addPerson(new Task(three));
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
