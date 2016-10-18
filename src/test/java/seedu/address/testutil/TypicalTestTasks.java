package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask someday, deadline, event, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            someday =  new TaskBuilder().withName("hw 1").withStatus("not done").withTaskType("someday").build();
            deadline =  new TaskBuilder().withName("hw 2").withStatus("not done").withTaskType("deadline").withEndDate("16:00 03-03-15").build();
            event =  new TaskBuilder().withName("hw 3").withStatus("not done").withTaskType("event").withStartDate("").withEndDate("").build();
            daniel =  new TaskBuilder().withName("Aid daniel").withStatus("not done").build();
            elle =  new TaskBuilder().withName("Aid elle").withStatus("not done").build();
            fiona =  new TaskBuilder().withName("Aid fiona").withStatus("not done").build();
            george =  new TaskBuilder().withName("Aid george").withStatus("not done").build();

            //Manually added
            hoon =  new TaskBuilder().withName("Aid hoon").withStatus("not done").build();
            ida =  new TaskBuilder().withName("Aid ida").withStatus("not done").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(someday));
            ab.addTask(new Task(deadline));
            ab.addTask(new Task(event));
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
    //STUB
    public TestTask[] getTodayTasks() {
        return new TestTask[] {};
    }
    //STUB
    public TestTask[] getTomorrowTasks() {
        return new TestTask[] {};
    }
    //STUB
    public TestTask[] getIn7DaysTasks() {
        return new TestTask[] {};
    }
    //STUB
    public TestTask[] getIn30DaysTasks() {
        return new TestTask[] {};
    }
    //STUB
    public TestTask[] getSomedayTasks() {
        return new TestTask[] {alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
