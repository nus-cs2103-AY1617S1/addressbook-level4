package seedu.taskscheduler.testutil;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.TaskScheduler;
import seedu.taskscheduler.model.task.*;

//@@author A0148145E

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, event, ida, overdue, floating, deadline;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("Career Fair").withAddress("NUS COM1")
                    .withStartDate("07 Oct 2016").withEndDate("11 Oct 2016").withType("EVENT").build();
            benson = new TaskBuilder().withName("Groupwork Project").withAddress("")
                    .withStartDate("").withEndDate("14 Oct 2016 at 8 am").withType("DEADLINE").withTags("Deadline").build(); // deadline task
            carl = new TaskBuilder().withName("Project Discussion").withAddress("Technoedge")
                    .withStartDate("14 Oct 2016 at 1 pm").withEndDate("21 Oct 2016").withType("EVENT").withTags("Event").build();
            daniel = new TaskBuilder().withName("Groupwork Discussion").withAddress("ICube Lecture Hall")
                    .withStartDate("01 Jan 2018").withEndDate("31 Dec 2018").withType("EVENT").withTags("Event").build();
            elle = new TaskBuilder().withName("CS2103 Lecture").withAddress("ICube Lecture Hall")
                    .withStartDate("01 Jan 2018").withEndDate("31 Dec 2018").withType("EVENT").withTags("Event").build();
            fiona = new TaskBuilder().withName("Send kid to NUS").withAddress("NUS")
                    .withStartDate("01 Jan 2034").withEndDate("01 Apri 2038").withType("EVENT").withTags("Event").build();
            george = new TaskBuilder().withName("Project Briefing").withAddress("")
                    .withStartDate("").withEndDate("").withType("FLOATING").withTags().build(); // floating task
            //Manually added
            ida = new TaskBuilder().withName("University Graduation").withAddress("University Cultural Centre")
                    .withStartDate("7 July 2016").withEndDate("15 July 2016").withType("EVENT").build();
            overdue = new TaskBuilder().withName("Overdue Task Colour Test").withAddress("At ICube Lecture Hall")
                    .withStartDate("10 years ago").withEndDate("10 years ago").withType("EVENT").build();
            event = new TaskBuilder().withName("Wildlife Photoshoot").withAddress("Wildlife Reserves")
                    .withStartDate("yesterday").withEndDate("today").withType("EVENT").build(); // event task
            floating = new TaskBuilder().withName("Floating Task").withAddress("")
                    .withStartDate("").withEndDate("").withType("FLOATING").withTags().build(); // floating task
            deadline = new TaskBuilder().withName("Deadline Task").withAddress("")
                    .withStartDate("").withEndDate("tomorrow").withType("DEADLINE").withTags("Deadline").build(); // deadline task

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskSchedulerWithSampleData(TaskScheduler ab) {

        try {
            ab.addTask(new EventTask(alice));
            ab.addTask(new DeadlineTask(benson));
            ab.addTask(new Task(carl));
            ab.addTask(new FloatingTask(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] {alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskScheduler getTypicalTaskScheduler(){
        TaskScheduler ab = new TaskScheduler();
        loadTaskSchedulerWithSampleData(ab);
        return ab;
    }
}
