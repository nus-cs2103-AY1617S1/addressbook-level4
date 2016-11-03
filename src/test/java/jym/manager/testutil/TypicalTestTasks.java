package jym.manager.testutil;

import jym.manager.commons.exceptions.IllegalValueException;
import jym.manager.model.TaskManager;
import jym.manager.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask doLaundry, doHomework, washCar, writeProgram, goShopping, eatWithFriends, unpackThings, buyFan, goToWork;
//@@author A0153440R
    public TypicalTestTasks() {
        try {
            doLaundry =  new TaskBuilder().withDescription("Do laundry")
            		.withLocation("Bishan")
            		.withDeadline("05-06-2017 12:30").withTags("friends").build();
       //     .withAddress("123, Jurong West Ave 6, #08-111")
         //           .withTags("friends").build();
            doHomework = new TaskBuilder().withDescription("Do Homework")
            		.withLocation("311, Clementi Ave 2, #02-25")
            		.withDeadline("05-06-2017 12:30")
                    .build();
            washCar = new TaskBuilder().withDescription("Wash Car")
            		.withLocation("10th street")
            		.withDeadline("12-12-2017 14:20")
            		.build();
            writeProgram = new TaskBuilder().withDescription("finish homework and Write Program")
            		.withLocation("10th street")
            		.withDeadline("12-12-2017 14:20")
            		.build();
            goShopping = new TaskBuilder().withDescription("Go Shopping")
            		.withLocation("michegan ave")
            		.withDeadline("07-06-2016 12:30")
            		.build();
            eatWithFriends = new TaskBuilder().withDescription("Eat Japanese food")
            		.withLocation("little tokyo")
            		.withDeadline("01-01-1023 14:10")
            		.build();
            unpackThings = new TaskBuilder().withDescription("Unpack Apartment")
            		.withLocation("4th street")
            		.withDeadline("09-09-2011 09:10")
            		.build();

            //Manually added
            buyFan = new TaskBuilder().withDescription("Buy fan")
            		.withLocation("little india")
            		.withDeadline("01-01-2134 12:30")
            		.build();
            goToWork = new TaskBuilder().withDescription("Go to work")
            		.withLocation("chicago ave")
            		.withDeadline("tomorrow 9AM")
            		.build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }
//@author
    public static void loadAddressBookWithSampleData(TaskManager ab) {

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

    public TaskManager getTypicalAddressBook(){
        TaskManager ab = new TaskManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
