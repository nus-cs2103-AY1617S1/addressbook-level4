package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskScheduler;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice =  new TaskBuilder().withName("Do CS2103 Pretut").withAddress("At Home")
                    .withEndDate("07102016").withStartDate("01102016").build();
            benson = new TaskBuilder().withName("Do CS2103 Project").withAddress("At School")
                    .withEndDate("14102016").withStartDate("07102016").build();
            carl = new TaskBuilder().withName("Eat Mcdonalds").withAddress("At Technoedge")
                    .withEndDate("21102016").withStartDate("14102016").build();
            daniel = new TaskBuilder().withName("Flunk CS2103").withAddress("In the exam hall")
                    .withEndDate("21112016").withStartDate("21112016").build();
            elle = new TaskBuilder().withName("Working at Mcdonalds").withAddress("At Mcdonalds")
                    .withEndDate("31122047").withStartDate("01042017").build();
            fiona = new TaskBuilder().withName("Send kids to NUS").withAddress("At NUS")
                    .withEndDate("01042051").withStartDate("01082047").build();
            elle = new TaskBuilder().withName("Make kids study CS2103").withAddress("At ICube Lecture Hall")
                    .withEndDate("01122050").withStartDate("01082049").build();
            george = new TaskBuilder().withName("Make kids work at Mcdonalds").withAddress("At the same workplace")
                    .withEndDate("02042091").withStartDate("02042051").build();
            //Manually added
            hoon = new TaskBuilder().withName("Regret working at Mcdonalds").withAddress("At the hospital")
                    .withEndDate("03042052").withStartDate("02042052").build();
            ida = new TaskBuilder().withName("Thinking about what happen if I fail CS2103").withAddress("At ICube Lecture Hall")
                    .withEndDate("07102016").withStartDate("07102016").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskScheduler ab) {

        try {
            ab.addTask(new Task(alice));
            ab.addTask(new Task(benson));
            ab.addTask(new Task(carl));
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskScheduler getTypicalAddressBook(){
        TaskScheduler ab = new TaskScheduler();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
