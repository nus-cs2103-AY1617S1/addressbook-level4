package seedu.task.testutil;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.AddressBook;
import seedu.todolist.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask  campaign, dinnerMum, dinnerDad, dinnerChristmas, meeting, wedding;

    public TypicalTestTasks() {
        try {
            campaign = new TaskBuilder().withName("Student Campaign")
                    .withInterval("13/10/2016", "9:30am", "18/10/2016", "6pm")
                    .withLocation("NUS")
                    .withRemarks("print flyers").build();
            dinnerMum = new TaskBuilder().withName("Dinner with mum")
                    .withInterval("14 oct 2016", "7pm", "14 oct 2016", "8:30pm")
                    .withLocation("Star Vista")
                    .withRemarks("make reservation").build();
            dinnerDad = new TaskBuilder().withName("Dinner with dad")
                    .withInterval("24 oct 2016", "7pm", "24 oct 2016", "8:30pm")
                    .withLocation("Buona Vista")
                    .withRemarks("make reservation").build();
            dinnerChristmas = new TaskBuilder().withName("Christmas dinner")
                    .withInterval("25 dec 2016", "7pm", "25 dec 2016", "8:30pm")
                    .withLocation("Orchard")
                    .withRemarks("make reservation").build();
          
            //Manually added
            meeting = new TaskBuilder().withName("ABC project meeting")
                    .withInterval("12 oct 2016", "10:00am", "12 oct 2016", "11am")
                    .withLocation("Orchard")
                    .withRemarks("prepare agenda").build();
            wedding = new TaskBuilder().withName("Jimmy wedding")
                    .withInterval("15 oct 2016", "6:30pm", "15 oct 2016", "11pm")
                    .withLocation("Orchard")
                    .withRemarks("buy gifts").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addTask(new Task(campaign));
            ab.addTask(new Task(dinnerMum));
            ab.addTask(new Task(dinnerDad));
            ab.addTask(new Task(dinnerChristmas));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{campaign, dinnerMum, dinnerDad, dinnerChristmas};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
