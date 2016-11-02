package seedu.forgetmenot.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.testutil.TaskBuilder;
import seedu.forgetmenot.testutil.TestTask;
//@@author A0139671X
public class TaskTest {

//    @Test
//    public void checkOverdue_checkIfGivenTimeIsOverdue_trueIfOverdue() throws IllegalValueException {
//        TestDataHelper helper = new TestDataHelper();
//        ArrayList<TestTask> overdueTasks = helper.generateOverdueTasks();
//        ArrayList<TestTask> notOverdueTasks = helper.generateNotOverdueTasks();
//
//        for (int i = 0; i < overdueTasks.size(); i++)
//            assertTrue(overdueTasks.get(i).checkOverdue());
//        for (int i = 0; i < notOverdueTasks.size(); i++)
//            assertFalse(notOverdueTasks.get(i).checkOverdue());
//    }

//    @Test
//    public void isEventTask_checkIfGivenTaskIsNotAnEvent_falseIfNotAnEvent() throws IllegalValueException {
//        TestDataHelper helper = new TestDataHelper();
//        ArrayList<TestTask> deadlineTasks = helper.generateDeadlineTasks();
//        ArrayList<TestTask> startTimeOnlyTasks = helper.generateStartTimeOnlyTasks();
//        ArrayList<TestTask> floatingTasks = helper.generateFloatingTasks();
//        ArrayList<TestTask> eventTasks = helper.generateEventTasks();
//
//        for (int i = 0; i < deadlineTasks.size(); i++)
//            assertFalse(deadlineTasks.get(i).isEventTask());
//        for (int i = 0; i < startTimeOnlyTasks.size(); i++)
//            assertFalse(startTimeOnlyTasks.get(i).isEventTask());
//        for (int i = 0; i < floatingTasks.size(); i++)
//            assertFalse(floatingTasks.get(i).isEventTask());
//        for (int i = 0; i < eventTasks.size(); i++)
//            assertTrue(eventTasks.get(i).isEventTask());
//    }

//    @Test
//    public void isStartTask_checkIfGivenTaskOnlyContainsStartTime_trueIfOnlyStartTimeExists()
//            throws IllegalValueException {
//        TestDataHelper helper = new TestDataHelper();
//        ArrayList<TestTask> deadlineTasks = helper.generateDeadlineTasks();
//        ArrayList<TestTask> startTimeOnlyTasks = helper.generateStartTimeOnlyTasks();
//        ArrayList<TestTask> floatingTasks = helper.generateFloatingTasks();
//        ArrayList<TestTask> eventTasks = helper.generateEventTasks();
//
//        for (int i = 0; i < deadlineTasks.size(); i++)
//            assertFalse(deadlineTasks.get(i).isStartTask());
//        for (int i = 0; i < startTimeOnlyTasks.size(); i++)
//            assertTrue(startTimeOnlyTasks.get(i).isStartTask());
//        for (int i = 0; i < floatingTasks.size(); i++)
//            assertFalse(floatingTasks.get(i).isStartTask());
//        for (int i = 0; i < eventTasks.size(); i++)
//            assertFalse(eventTasks.get(i).isStartTask());
//    }

//    @Test
//    public void isDeadlineTask_checkIfGivenTaskOnlyContainsEndTime_trueIfOnlyEndTimeExists()
//            throws IllegalValueException {
//        TestDataHelper helper = new TestDataHelper();
//        ArrayList<TestTask> deadlineTasks = helper.generateDeadlineTasks();
//        ArrayList<TestTask> startTimeOnlyTasks = helper.generateStartTimeOnlyTasks();
//        ArrayList<TestTask> floatingTasks = helper.generateFloatingTasks();
//        ArrayList<TestTask> eventTasks = helper.generateEventTasks();
//
//        for (int i = 0; i < deadlineTasks.size(); i++)
//            assertTrue(deadlineTasks.get(i).isDeadlineTask());
//        for (int i = 0; i < startTimeOnlyTasks.size(); i++)
//            assertFalse(startTimeOnlyTasks.get(i).isDeadlineTask());
//        for (int i = 0; i < floatingTasks.size(); i++)
//            assertFalse(floatingTasks.get(i).isDeadlineTask());
//        for (int i = 0; i < eventTasks.size(); i++)
//            assertFalse(eventTasks.get(i).isDeadlineTask());
//    }
    
//    @Test
//    public void isFloatingTask_checkIfGivenTaskHasNoStartAndNoEnd_trueIfTaskHasNoStartTimeAndNoEndTime()
//            throws IllegalValueException {
//        TestDataHelper helper = new TestDataHelper();
//        ArrayList<TestTask> deadlineTasks = helper.generateDeadlineTasks();
//        ArrayList<TestTask> startTimeOnlyTasks = helper.generateStartTimeOnlyTasks();
//        ArrayList<TestTask> floatingTasks = helper.generateFloatingTasks();
//        ArrayList<TestTask> eventTasks = helper.generateEventTasks();
//
//        for (int i = 0; i < deadlineTasks.size(); i++)
//            assertTrue(deadlineTasks.get(i).isDeadlineTask());
//        for (int i = 0; i < startTimeOnlyTasks.size(); i++)
//            assertFalse(startTimeOnlyTasks.get(i).isDeadlineTask());
//        for (int i = 0; i < floatingTasks.size(); i++)
//            assertFalse(floatingTasks.get(i).isDeadlineTask());
//        for (int i = 0; i < eventTasks.size(); i++)
//            assertFalse(eventTasks.get(i).isDeadlineTask());
//    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        public ArrayList<TestTask> generateFloatingTasks() throws IllegalValueException {
            ArrayList<TestTask> floatingTasks = new ArrayList<TestTask>();

            floatingTasks.add(new TaskBuilder().withName("floating 1").withDone(false).withStartTime("").withEndTime("")
                    .withRecurrence("").build());
            floatingTasks.add(new TaskBuilder().withName("floating 2").withDone(false).withStartTime("").withEndTime("")
                    .withRecurrence("").build());
            floatingTasks.add(new TaskBuilder().withName("floating 3").withDone(false).withStartTime("").withEndTime("")
                    .withRecurrence("").build());
            return floatingTasks;
        }

        public ArrayList<TestTask> generateStartTimeOnlyTasks() throws IllegalValueException {
            ArrayList<TestTask> startTimeTasks = new ArrayList<TestTask>();

            startTimeTasks.add(new TaskBuilder().withName("start only 1").withDone(false).withStartTime("tomorrow 8am")
                    .withEndTime("").withRecurrence("").build());
            startTimeTasks.add(new TaskBuilder().withName("start only 2").withDone(false).withStartTime("next year 8am")
                    .withEndTime("").withRecurrence("").build());
            startTimeTasks.add(new TaskBuilder().withName("start only 3").withDone(false)
                    .withStartTime("three hours later").withEndTime("").withRecurrence("").build());
            return startTimeTasks;
        }

        public ArrayList<TestTask> generateEventTasks() throws IllegalValueException {
            ArrayList<TestTask> eventTasks = new ArrayList<TestTask>();

            eventTasks.add(new TaskBuilder().withName("event 1").withDone(false).withStartTime("tomorrow 8am")
                    .withEndTime("tmr 10am").withRecurrence("").build());
            eventTasks.add(new TaskBuilder().withName("event 2").withDone(false).withStartTime("three weeks later")
                    .withEndTime("four weeks later").withRecurrence("").build());
            eventTasks.add(new TaskBuilder().withName("event 3").withDone(false).withStartTime("two hours later")
                    .withEndTime("three hours later").withRecurrence("").build());
            return eventTasks;
        }

        public ArrayList<TestTask> generateDeadlineTasks() throws IllegalValueException {
            ArrayList<TestTask> deadlineTasks = new ArrayList<TestTask>();
            deadlineTasks.add(new TaskBuilder().withName("deadline 1").withDone(false).withStartTime("")
                    .withEndTime("tomorrow 9pm").withRecurrence("").build());
            deadlineTasks.add(new TaskBuilder().withName("deadline 2").withDone(false).withStartTime("")
                    .withEndTime("next year 7am").withRecurrence("").build());
            deadlineTasks.add(new TaskBuilder().withName("deadline 3").withDone(false).withStartTime("")
                    .withEndTime("three hours later").withRecurrence("").build());

            return deadlineTasks;
        }

        public ArrayList<TestTask> generateNotOverdueTasks() throws IllegalValueException {
            ArrayList<TestTask> notOverdueTasks = new ArrayList<TestTask>();

            notOverdueTasks.add(new TaskBuilder().withName("Not overdue start only").withDone(false)
                    .withStartTime("tomorrow").withEndTime("").withRecurrence("").build());
            notOverdueTasks.add(new TaskBuilder().withName("Not overdue deadline").withDone(false).withStartTime("")
                    .withEndTime("three days later").withRecurrence("").build());
            notOverdueTasks.add(new TaskBuilder().withName("Not overdue event").withDone(false)
                    .withStartTime("tomorrow").withEndTime("day after tomorrow").withRecurrence("").build());

            return notOverdueTasks;
        }

        public ArrayList<TestTask> generateOverdueTasks() throws IllegalValueException {
            ArrayList<TestTask> overdueTasks = new ArrayList<TestTask>();

            overdueTasks.add(new TaskBuilder().withName("overdue start time task").withDone(false)
                    .withStartTime("1/1/16").withEndTime("").withRecurrence("").build());
            overdueTasks.add(new TaskBuilder().withName("overdue deadline").withDone(false).withStartTime("")
                    .withEndTime("2/2/16").withRecurrence("").build());
            overdueTasks.add(new TaskBuilder().withName("overdue event task").withDone(false).withStartTime("1/12/15")
                    .withEndTime("2/12/15").withRecurrence("").build());

            return overdueTasks;
        }
    }

}
