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
                            event, deadline;

    public TypicalTestTasks() {
        try {
            eventWithoutParameters = new TaskBuilder().withName("Event without parameters")
                    .withInterval("13/12/2016", "9:30am", "18/12/2016", "6:00pm")
                    .withLocation(null)
                    .withRemarks(null)
                    .withStatus(false).build();
            eventWithLocation = new TaskBuilder().withName("Event with location")
                    .withInterval("20 nov 2016", "11:00", "23 nov 2016", "23:59")
                    .withLocation("Office")
                    .withRemarks(null)
                    .withStatus(false).build();
            eventWithRemarks = new TaskBuilder().withName("Event withRemarks")
                    .withInterval("24/12/2016", "7:30pm", "24/12/2016", "9:00pm")
                    .withLocation(null)
                    .withRemarks("buy present")
                    .withStatus(false).build();
            eventWithLocationAndRemarks = new TaskBuilder().withName("Event with locationAndRemarks")
                    .withInterval("18 nov 2016", "7pm", "18 nov 2016", "8pm")
                    .withLocation("East Coast")
                    .withRemarks("buy present")
                    .withStatus(false).build();
            deadlineWithoutParameter = new TaskBuilder().withName("Deadline without parameter")
                    .withInterval(null, null, "24 nov 2016", "23:59")
                    .withLocation(null)
                    .withRemarks(null)
                    .withStatus(false).build();
            deadlineWithLocation = new TaskBuilder().withName("Deadline with location")
                    .withInterval(null, null, "28 nov 2016", null)
                    .withLocation("Fairprice")
                    .withRemarks(null)
                    .withStatus(false).build();
          
            //Manually added
            event = new TaskBuilder().withName("ABC project meeting")
                    .withInterval("12/10/2016", "10am", "12 oct 2016", "11:30am")
                    .withLocation("Orchard")
                    .withRemarks("prepare agenda")
                    .withStatus(false).build();
            deadline = new TaskBuilder().withName("Go gym")
                    .withInterval(null, null, "13 nov 2016", null)
                    .withLocation(null)
                    .withRemarks("bring towel")
                    .withStatus(false).build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

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
