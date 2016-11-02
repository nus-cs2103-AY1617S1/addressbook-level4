package guitests;

import org.junit.Test;


import seedu.Tdoo.testutil.TestUtil;
import seedu.Tdoo.commons.core.LogsCenter;
import seedu.Tdoo.commons.exceptions.IllegalValueException;
import seedu.Tdoo.logic.commands.Command;
import seedu.Tdoo.logic.parser.Parser;
import seedu.Tdoo.model.task.Todo;
import seedu.Tdoo.model.task.attributes.*;
import seedu.Tdoo.testutil.DeadlineBuilder;
import seedu.Tdoo.testutil.EventBuilder;
import seedu.Tdoo.testutil.TaskBuilder;
import seedu.Tdoo.testutil.TestDeadline;
import seedu.Tdoo.testutil.TestEvent;
import seedu.Tdoo.testutil.TestTask;
import seedu.Tdoo.testutil.TestUtil;
import seedu.Tdoo.commons.exceptions.IllegalValueException;
import seedu.Tdoo.testutil.TestTask;


import static org.junit.Assert.assertTrue;
import static seedu.Tdoo.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

public class DeleteCommandTest extends ListGuiTest {

    @Test
    //@@author A0132157M reused
    public void delete() throws IllegalValueException {
        //Delete tasks in Todo list
        TestTask[] currentList = new TestTask[] {
                new TaskBuilder().withName("TODO 123").withStartDate("28-11-2016").withEndDate("29-11-2016").withPriority("1").withDone("false").build(),
                new TaskBuilder().withName("TODO 456").withStartDate("01-12-2016").withEndDate("02-12-2016").withPriority("1").withDone("false").build(),
                new TaskBuilder().withName("TODO 789").withStartDate("03-12-2016").withEndDate("04-12-2016").withPriority("1").withDone("false").build(),
                new TaskBuilder().withName("TODO 101112").withStartDate("03-12-2016").withEndDate("04-12-2016").withPriority("1").withDone("false").build()
        };
        addAllDummyTodoTasks(currentList);
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removetaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removetaskFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertDeleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("delete todo" + currentList.length + 1);
        assertResultMessage("Invalid command format! \n"
            + "delete: Deletes the task identified by the task type and the index number used in the last task listing.\n"
            + "Parameters: TASK_TYPE INDEX_NUMBER(must be a positive integer)\n"
            + "Example: " + "delete" + " todo 1\n"
            + "Example: " + "delete" + " event 1\n"
            + "Example: " + "delete" + " deadline 1");
        
        //Delete tasks in Event list
        TestEvent[] currentList1 = new TestEvent[] {
                new EventBuilder().withName("Event 123").withStartDate("11-12-2016").withEndDate("12-12-2016").withStartTime("01:00").withEndTime("01:30").withDone("false").build(),
                new EventBuilder().withName("Event 456").withStartDate("17-11-2016").withEndDate("18-11-2016").withStartTime("01:30").withEndTime("20:00").withDone("false").build(),
                new EventBuilder().withName("Eeambuilding 3").withStartDate("19-11-2016").withEndDate("20-11-2016").withStartTime("01:30").withEndTime("02:00").withDone("false").build(),
                new EventBuilder().withName("Essignment 4").withStartDate("11-12-2016").withEndDate("12-12-2016").withStartTime("01:00").withEndTime("01:30").withDone("false").build()
        };
        
        addAllDummyEventTasks(currentList1);
        int targetIndex1 = 1;
        assertDeleteEventSuccess(targetIndex1, currentList1); 

        //delete the last in the list
        currentList1 = TestUtil.removeEventFromList(currentList1, targetIndex1);
        targetIndex1 = currentList1.length;
        assertDeleteEventSuccess(targetIndex1, currentList1);

        //delete from the middle of the list
        currentList1 = TestUtil.removeEventFromList(currentList1, targetIndex1);
        targetIndex1 = currentList1.length/2;
        assertDeleteEventSuccess(targetIndex1, currentList1);

        //invalid index
        commandBox.runCommand("delete event" + currentList1.length + 1);
        assertResultMessage("Invalid command format! \n"
            + "delete: Deletes the task identified by the task type and the index number used in the last task listing.\n"
            + "Parameters: TASK_TYPE INDEX_NUMBER(must be a positive integer)\n"
            + "Example: " + "delete" + " todo 1\n"
            + "Example: " + "delete" + " event 1\n"
            + "Example: " + "delete" + " deadline 1");
        
        
        //delete deadlines in deadline list
        TestDeadline[] currentList2 = new TestDeadline[] {
                new DeadlineBuilder().withName("d 1").withStartDate("15-11-2017").withEndTime("10:00").withDone("false").build(),
                new DeadlineBuilder().withName("dd 1").withStartDate("16-11-2017").withEndTime("12:00").withDone("false").build(),
                new DeadlineBuilder().withName("ddd 3").withStartDate("17-11-2017").withEndTime("13:00").withDone("false").build(),
                new DeadlineBuilder().withName("dddd 3").withStartDate("18-11-2017").withEndTime("13:00").withDone("false").build()
        };
        
        addAllDummyDeadlineTasks(currentList2);
        int targetIndex2 = 1;
        assertDeleteDeadlineSuccess(targetIndex2, currentList2);

        //delete the last in the list
        currentList2 = TestUtil.removeDeadlineFromList(currentList2, targetIndex2);
        targetIndex2 = currentList2.length;
        assertDeleteDeadlineSuccess(targetIndex2, currentList2);

        //delete from the middle of the list
        currentList2 = TestUtil.removeDeadlineFromList(currentList2, targetIndex2);
        targetIndex2 = currentList2.length/2;
        assertDeleteDeadlineSuccess(targetIndex2, currentList2);

        //invalid index
        commandBox.runCommand("delete event" + currentList2.length + 1);
        assertResultMessage("Invalid command format! \n"
            + "delete: Deletes the task identified by the task type and the index number used in the last task listing.\n"
            + "Parameters: TASK_TYPE INDEX_NUMBER(must be a positive integer)\n"
            + "Example: " + "delete" + " todo 1\n"
            + "Example: " + "delete" + " event 1\n"
            + "Example: " + "delete" + " deadline 1");
    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     * @throws IllegalValueException 
     */
    //author A0132157M reused
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) throws IllegalValueException {
        TestTask taskToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removetaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete todo " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete.getName().name.toString() + "\n" +
                "Start Date: No Start Date" + "\n" + 
                "End Date: No End Date" + "\n" + 
                "Priority: " + new Priority(taskToDelete.getPriority())));
    }
    
  //author A0132157M reused
    private void assertDeleteEventSuccess(int targetIndexOneIndexed, final TestEvent[] currentList) throws IllegalValueException {
        TestEvent taskToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestEvent[] expectedRemainder = TestUtil.removeEventFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete event " + targetIndexOneIndexed);
        
        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(eventListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete.getName().name.toString() + "\n" +
                "Start Date: " + new StartDate(taskToDelete.getStartDate().date.toString()) + "\n" + 
                "End Date: " + new EndDate(taskToDelete.getEndDate()) + "\n" + 
                "StartTime: " + new StartTime(taskToDelete.getStartTime()) + "\n" +
                "EndTime: " + new EndTime(taskToDelete.getEndTime())));
    }
    
  //author A0132157M reused
    private void assertDeleteDeadlineSuccess(int targetIndexOneIndexed, final TestDeadline[] currentList) throws IllegalValueException {
        TestDeadline taskToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestDeadline[] expectedRemainder = TestUtil.removeDeadlineFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete deadline " + targetIndexOneIndexed);
        
        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(deadlineListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete.getName().name.toString() + "\n" +
                "Date: " + new StartDate(taskToDelete.getStartDate().date.toString()) + "\n" + 
                "EndTime: " + new EndTime(taskToDelete.getEndTime())));
    }
    
    private void addAllDummyTodoTasks(TestTask... currentList) {
        for(TestTask t:currentList ) {
            commandBox.runCommand(t.getAddCommand());
        }
    }
    private void addAllDummyEventTasks(TestEvent... currentList) {
        for(TestEvent t:currentList ) {
            commandBox.runCommand(t.getAddCommand());
        }
    }
    private void addAllDummyDeadlineTasks(TestDeadline... currentList) {
        for(TestDeadline t:currentList ) {
            commandBox.runCommand(t.getAddCommand());
        }
    }

}
