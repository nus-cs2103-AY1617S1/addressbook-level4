package seedu.address.testutil;

import jym.manager.commons.exceptions.IllegalValueException;
import jym.manager.model.AddressBook;
import jym.manager.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask doLaundry, doHomework, washCar, writeProgram, goShopping, eatWithFriends, unpackThings, buyFan, goToWork;

    public TypicalTestTasks() {
        try {
            doLaundry =  new TaskBuilder().withDescription("Do laundry")
            		.withLocation("Bishan")
            		.withDeadline("05-06-2017 12:30").withTags("friends").build();
       //     .withAddress("123, Jurong West Ave 6, #08-111")
         //           .withTags("friends").build();
            doHomework = new TaskBuilder().withDescription("Do Homework")
            		.withLocation("311, Clementi Ave 2, #02-25")
         //           .withEmail("johnd@gmail.com").withPhone("98765432")
                    .build();
            washCar = new TaskBuilder().withDescription("Wash Car").build();
            writeProgram = new TaskBuilder().withDescription("Write Program")
            		.withLocation("10th street")
            		.withDeadline("12-12-2017 14:20")
            		.build();
            goShopping = new TaskBuilder().withDescription("Go Shopping")
            		.withLocation("michegan ave")
            		.build();
            eatWithFriends = new TaskBuilder().withDescription("Eat Japanese food")
            		.withLocation("little tokyo")
            		.build();
            unpackThings = new TaskBuilder().withDescription("Unpack Apartment")
            		.withLocation("4th street")
            		.build();

            //Manually added
            buyFan = new TaskBuilder().withDescription("Buy fan")
            		.withLocation("little india")
            		.build();
            goToWork = new TaskBuilder().withDescription("Go to work")
            		.withLocation("chicago ave")
            		.build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addTask(new Task(doLaundry));
            ab.addTask(new Task(doHomework));
            ab.addTask(new Task(washCar));
            ab.addTask(new Task(writeProgram));
            ab.addTask(new Task(goShopping));
            ab.addTask(new Task(eatWithFriends));
            ab.addTask(new Task(unpackThings));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{doLaundry, doHomework, washCar, writeProgram, goShopping, eatWithFriends, unpackThings};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
