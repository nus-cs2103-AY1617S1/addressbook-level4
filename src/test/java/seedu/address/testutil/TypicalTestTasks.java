package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask one, two, three;

    public TypicalTestTasks() {
        try {
            one = new DatedTaskBuilder().withName("buy milk").withDescription("lots of it")
                    .withDatetime("11-NOV-2017 11:11").withStatus("NONE").build();
            two = new DatedTaskBuilder().withName("buy some milk").withDescription("not so much")
                    .withDatetime("12-NOV-2017 12:00").withStatus("NONE").build();
            three = new DatedTaskBuilder().withName("buy some milk").withDescription("just a little")
                    .withDatetime("13-DEC-2017 21:33").withStatus("NONE").build();
            
            
            
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
        } catch (UniqueTaskList.DuplicateTaskException e) {
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
