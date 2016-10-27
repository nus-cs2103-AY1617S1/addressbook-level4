package seedu.task.testutil;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.AddressBook;
import seedu.todolist.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask  eventWithoutParameters, eventWithLocation, eventWithRemarks, 
                            eventWithLocationAndRemarks, deadlineWithoutParameter,  deadlineWithLocation, 
                            event, deadline, taskOneToTestFind, taskTwoToTestFind, taskThreeToTestFind;

    //@@author A0138601M
    public TypicalTestTasks() {
        try {
            eventWithoutParameters = new TaskBuilder().withName("Event without parameters")
                    .withInterval("23/10/2016", "9:30am", "27/10/2016", "6:00pm")
                    .withLocation(null)
                    .withRemarks(null)
                    .withStatus(false).build();
            eventWithLocation = new TaskBuilder().withName("Event with location")
                    .withInterval("28 oct 2016", "11:00", "1 nov 2016", "23:59")
                    .withLocation("Office")
                    .withRemarks(null)
                    .withStatus(false).build();
            eventWithRemarks = new TaskBuilder().withName("Event withRemarks")
                    .withInterval("24/12/2016", "7:30pm", "29/12/2016", "9:00pm")
                    .withLocation(null)
                    .withRemarks("buy present")
                    .withStatus(false).build();
            eventWithLocationAndRemarks = new TaskBuilder().withName("Event with locationAndRemarks")
                    .withInterval("28 dec 2016", "7pm", "28 dec 2016", "8pm")
                    .withLocation("East Coast")
                    .withRemarks("buy present")
                    .withStatus(false).build();
            deadlineWithoutParameter = new TaskBuilder().withName("Deadline without parameter")
                    .withInterval(null, null, "29 dec 2016", "23:59")
                    .withLocation(null)
                    .withRemarks(null)
                    .withStatus(false).build();
            deadlineWithLocation = new TaskBuilder().withName("Deadline with location")
                    .withInterval(null, null, "30 dec 2016", null)
                    .withLocation("Fairprice")
                    .withRemarks(null)
                    .withStatus(false).build();
          
            //Manually added
            event = new TaskBuilder().withName("ABC project meeting")
                    .withInterval("1/12/2016", "10am", "1 dec 2016", "11:30am")
                    .withLocation("Orchard")
                    .withRemarks("prepare agenda")
                    .withStatus(false).build();
            deadline = new TaskBuilder().withName("Go gym")
                    .withInterval(null, null, "2 dec 2016", null)
                    .withLocation(null)
                    .withRemarks("bring towel")
                    .withStatus(false).build();
            
            //Task for testing FindCommand
            taskOneToTestFind = new TaskBuilder().withName("One two Three")
                    .withInterval(null, null, "24 dec 2016", null)
                    .withLocation(null)
                    .withRemarks("testing")
                    .withStatus(false).build();
            
            taskTwoToTestFind = new TaskBuilder().withName("one Three Two four")
                    .withInterval(null, null, "27 dec 2016", null)
                    .withLocation("NUS")
                    .withRemarks(null)
                    .withStatus(false).build();
            
            taskThreeToTestFind = new TaskBuilder().withName("Four One three two")
                    .withInterval(null, null, "30 dec 2016", null)
                    .withLocation("home")
                    .withRemarks(null)
                    .withStatus(false).build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }
    //@@author

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addTask(new Task(eventWithoutParameters));
            ab.addTask(new Task(eventWithLocation));
            ab.addTask(new Task(eventWithRemarks));
            ab.addTask(new Task(eventWithLocationAndRemarks));
            ab.addTask(new Task(deadlineWithoutParameter));
            ab.addTask(new Task(deadlineWithLocation));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{eventWithoutParameters, eventWithLocation, eventWithRemarks, 
                eventWithLocationAndRemarks, deadlineWithoutParameter, deadlineWithLocation};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
