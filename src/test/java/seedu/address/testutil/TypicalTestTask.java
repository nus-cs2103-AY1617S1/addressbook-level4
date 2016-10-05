package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.person.*;

/**
 *
 */
public class TypicalTestTask {

    public static TestTask homeworkTask, groceryTask, meetingTask, packTask, shoppingTask, dinnerTask, fuelTask, homework2Task, meeting2Task;

    public TypicalTestTask() {
        try {
            homeworkTask =  new TaskBuilder().withName("CS2103 Homework Due").build();
            groceryTask = new TaskBuilder().withName("Buy Grocery").build();
            meetingTask = new TaskBuilder().withName("Meeting with Friends").build();
            packTask = new TaskBuilder().withName("Pack Luggage").build();
            shoppingTask = new TaskBuilder().withName("Shop for Clothes").build();
            dinnerTask = new TaskBuilder().withName("Dinner appointment").build();
            fuelTask = new TaskBuilder().withName("Top up fuel for car").build();

            //Manually added
            homework2Task = new TaskBuilder().withName("Math Homework due").build();
            meeting2Task = new TaskBuilder().withName("Consultation with prof").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskManager ab) {

        try {
            ab.addPerson(new Task(homeworkTask));
            ab.addPerson(new Task(groceryTask));
            ab.addPerson(new Task(meetingTask));
            ab.addPerson(new Task(packTask));
            ab.addPerson(new Task(shoppingTask));
            ab.addPerson(new Task(dinnerTask));
            ab.addPerson(new Task(fuelTask));
        } catch (UniqueTaskList.DuplicatePersonException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{homeworkTask, groceryTask, meetingTask, packTask, shoppingTask, dinnerTask, fuelTask};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
