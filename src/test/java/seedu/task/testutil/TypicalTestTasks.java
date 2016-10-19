package seedu.task.testutil;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.AddressBook;
import seedu.todolist.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask  campaign, dinnerMum, dinnerFriend, dinnerChristmas, proposal, buyMilk, meeting, goGym;

    public TypicalTestTasks() {
        try {
            campaign = new TaskBuilder().withName("Student Campaign")
                    .withInterval("13/10/2016", "9:30am", "18/10/2016", "6:00pm")
                    .withLocation("NUS")
                    .withRemarks("print flyers").build();
            dinnerMum = new TaskBuilder().withName("Dinner with mum")
                    .withInterval("14 oct 2016", "7pm", "14 oct 2016", "8pm")
                    .withLocation("Star Vista")
                    .withRemarks("make reservation").build();
            dinnerFriend = new TaskBuilder().withName("Dinner with a friend")
                    .withInterval("18 nov 2016", "7pm", "18 nov 2016", "8pm")
                    .withLocation("East Coast")
                    .withRemarks(null).build();
            dinnerChristmas = new TaskBuilder().withName("Dinner for christmas")
                    .withInterval("24/12/2016", "7:30pm", "24/12/2016", "9:00pm")
                    .withLocation("RWS")
                    .withRemarks("buy present").build();
            proposal = new TaskBuilder().withName("Submit proposal")
                    .withInterval(null, null, "24 nov 2016", "23:59")
                    .withLocation(null)
                    .withRemarks(null).build();
            buyMilk = new TaskBuilder().withName("Buy milk")
                    .withInterval(null, null, "28 nov 2016", null)
                    .withLocation("Fairprice")
                    .withRemarks(null).build();
          
            //Manually added
            meeting = new TaskBuilder().withName("ABC project meeting")
                    .withInterval("12/10/2016", "10am", "12 oct 2016", "11:30am")
                    .withLocation("Orchard")
                    .withRemarks("prepare agenda").build();
            goGym = new TaskBuilder().withName("Go gym")
                    .withInterval(null, null, "13 nov 2016", null)
                    .withLocation(null)
                    .withRemarks("bring towel").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addTask(new Task(campaign));
            ab.addTask(new Task(dinnerMum));
            ab.addTask(new Task(dinnerFriend));
            ab.addTask(new Task(dinnerChristmas));
            ab.addTask(new Task(proposal));
            ab.addTask(new Task(buyMilk));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{campaign, dinnerMum, dinnerFriend, dinnerChristmas, proposal, buyMilk};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
