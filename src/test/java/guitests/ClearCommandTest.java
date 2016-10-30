package guitests;

import org.junit.Test;


import guitests.guihandles.DeadlineCardHandle;
import guitests.guihandles.EventCardHandle;
import guitests.guihandles.TaskCardHandle;
import seedu.Tdoo.commons.exceptions.IllegalValueException;
import seedu.Tdoo.model.task.attributes.*;
import seedu.Tdoo.testutil.DeadlineBuilder;
import seedu.Tdoo.testutil.EventBuilder;
import seedu.Tdoo.testutil.TaskBuilder;
import seedu.Tdoo.testutil.TestDeadline;
import seedu.Tdoo.testutil.TestEvent;
import seedu.Tdoo.testutil.TestTask;
import seedu.Tdoo.testutil.TestUtil;



import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends ListGuiTest {
    
    @Test
  //@@author A0132157M reused
    public void clear() throws IllegalValueException {

        //verify a non-empty list can be cleared
        TestTask[] currentList = new TestTask[]{};
        TestTask taskToAdd = new TaskBuilder().withName("TODO 123").withStartDate("28-11-2016").withEndDate("29-11-2016").withPriority("1").withDone("false").build();
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertClearTodoCommandSuccess();
        
        //verify other commands can work after a clear command
        TestTask[] currentList1 = new TestTask[]{};
        TestTask TaskToAdd = new TaskBuilder().withName("TODO 456").withStartDate("28-11-2016").withEndDate("29-11-2016").withPriority("1").withDone("false").build();
        assertAddSuccess(TaskToAdd, currentList1);
        currentList1 = TestUtil.addTasksToList(currentList1, TaskToAdd);

        commandBox.runCommand("delete todo 1");
        assertListSize(0);
        
        //verify clear command works when the list is empty
        assertClearTodoCommandSuccess();
    
//------------------------------------------------------------------------
        //verify a non-empty list can be cleared
        TestEvent[] currentList2 = new TestEvent[]{};
        TestEvent taskToAdd1 = new EventBuilder().withName("EVENT 123").withStartDate("30-12-2017").withEndDate("31-12-2017").withStartTime("01:30").withEndTime("02:00").withDone("false").build();
        assertAddEventSuccess(taskToAdd1, currentList2);
        currentList2 = TestUtil.addEventsToList(currentList2, taskToAdd1);
        assertClearEventCommandSuccess();
          
        //verify other commands can work after a clear command
        TestEvent[] currentList3 = new TestEvent[]{};
        TestEvent TaskToAdd2 = new EventBuilder().withName("EVENT 456").withStartDate("30-12-2017").withEndDate("31-12-2017").withStartTime("01:30").withEndTime("02:00").withDone("false").build();
        assertAddEventSuccess(TaskToAdd2, currentList3);
        currentList3 = TestUtil.addEventsToList(currentList3, TaskToAdd2);

        commandBox.runCommand("delete event 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearEventCommandSuccess();
        
      //------------------------------------------------------------------------
        //verify a non-empty list can be cleared
        TestDeadline[] currentList4 = new TestDeadline[]{};
        TestDeadline taskToAdd4 = new DeadlineBuilder().withName("DEADLINE 123").withStartDate("31-12-2017").withEndTime("10:00").withDone("false").build();
        assertAddDeadlineSuccess(taskToAdd4, currentList4);
        currentList4 = TestUtil.addDeadlinesToList(currentList4, taskToAdd4);
        assertClearDeadlineCommandSuccess();
          
        //verify other commands can work after a clear command
        TestDeadline[] currentList5 = new TestDeadline[]{};
        TestDeadline TaskToAdd5 = new DeadlineBuilder().withName("DEADLINE 456").withStartDate("31-12-2017").withEndTime("10:00").withDone("false").build();
        assertAddDeadlineSuccess(TaskToAdd5, currentList5);
        currentList5 = TestUtil.addDeadlinesToList(currentList5, TaskToAdd5);

        commandBox.runCommand("delete deadline 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearEventCommandSuccess();
    }

    private void assertClearTodoCommandSuccess() {
        commandBox.runCommand("clear todo");
        assertListSize(0);
        assertResultMessage("TodoList has been cleared!");
    }
    //@@author A0132157M
    private void assertClearEventCommandSuccess() {
        commandBox.runCommand("clear event");
        assertListSize(0);
        assertResultMessage("EventList has been cleared!");
    }
  //@@author A0132157M
    private void assertClearDeadlineCommandSuccess() {
        commandBox.runCommand("clear deadline");
        assertListSize(0);
        assertResultMessage("DeadlineList has been cleared!");
    }
    
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        //addAllDummyTodoTasks(currentList);
        commandBox.runCommand(taskToAdd.getAddCommand());
        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateTotask(taskToAdd.getName().name);
        assertMatching(taskToAdd, addedCard);
        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
  //@@author A0132157M
    private void assertAddEventSuccess(TestEvent eventsToAdd, TestEvent... currentList) {
        //addAllDummyEventTasks(currentList);
        commandBox.runCommand(eventsToAdd.getAddCommand());
        //confirm the new card contains the right data
        EventCardHandle addedCard = eventListPanel.navigateToevent(eventsToAdd.getName().name);
        assertEventMatching(eventsToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestEvent[] expectedList = TestUtil.addEventsToList(currentList, eventsToAdd);
        assertTrue(eventListPanel.isListMatching(expectedList));
    }
    //@@author A0132157M
    private void assertAddDeadlineSuccess(TestDeadline eventsToAdd, TestDeadline... currentList) {
        commandBox.runCommand(eventsToAdd.getAddCommand());

        //confirm the new card contains the right data
        DeadlineCardHandle addedCard = deadlineListPanel.navigateToDeadline(eventsToAdd.getName().name);
        assertDeadlineMatching(eventsToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestDeadline[] expectedList = TestUtil.addDeadlinesToList(currentList, eventsToAdd);
        assertTrue(deadlineListPanel.isListMatching(expectedList));
    }
}
