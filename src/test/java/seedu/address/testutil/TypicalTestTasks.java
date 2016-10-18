package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskScheduler;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida, overdue;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("Do CS2103 Pretut").withAddress("At Home")
                    .withEndDate("071016").withStartDate("011016").withTags("Event").build();
            benson = new TaskBuilder().withName("Do CS2103 Project").withAddress("At School")
                    .withEndDate("14 Oct 2016").withStartDate("071016").withTags("Event").build();
            carl = new TaskBuilder().withName("Eat Mcdonalds").withAddress("At Technoedge")
                    .withEndDate("211016").withStartDate("14 Oct 2016").withTags("Event").build();
            daniel = new TaskBuilder().withName("Flunk CS2103").withAddress("In the exam hall")
                    .withEndDate("211116").withStartDate("211116").withTags("Event").build();
            elle = new TaskBuilder().withName("Working at Mcdonalds").withAddress("At Mcdonalds")
                    .withEndDate("311247").withStartDate("010417").withTags("Event").build();
            fiona = new TaskBuilder().withName("Send kids to NUS").withAddress("At NUS")
                    .withEndDate("010451").withStartDate("010847").withTags("Event").build();
            elle = new TaskBuilder().withName("Make kids study CS2103").withAddress("At ICube Lecture Hall")
                    .withEndDate("011250").withStartDate("010849").withTags("Event").build();
            george = new TaskBuilder().withName("Make kids work at Mcdonalds").withAddress("At the same workplace")
                    .withEndDate("020491").withStartDate("020451").withTags("Event").build();
            //Manually added
            hoon = new TaskBuilder().withName("Regret working at Mcdonalds").withAddress("At the hospital")
                    .withEndDate("030452").withStartDate("020452").build();
            ida = new TaskBuilder().withName("Thinking about what happen if I fail CS2103").withAddress("At ICube Lecture Hall")
                    .withEndDate("071016").withStartDate("071016").build();
            overdue = new TaskBuilder().withName("Overdue Task Colour Test").withAddress("At ICube Lecture Hall")
                    .withEndDate("071016").withStartDate("071016").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskSchedulerWithSampleData(TaskScheduler ab) {

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

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskScheduler getTypicalTaskScheduler(){
        TaskScheduler ab = new TaskScheduler();
        loadTaskSchedulerWithSampleData(ab);
        return ab;
    }
}
