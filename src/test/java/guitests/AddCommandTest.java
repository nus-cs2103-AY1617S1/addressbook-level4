package guitests;

import guitests.guihandles.DeadlineCardHandle;
import guitests.guihandles.EventCardHandle;
import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import seedu.todoList.commons.core.LogsCenter;
import seedu.todoList.commons.core.Messages;
import seedu.todoList.logic.commands.*;
import seedu.todoList.testutil.TestUtil;
import seedu.todoList.testutil.TypicalTestTask;
import seedu.todoList.testutil.TestDeadline;
import seedu.todoList.testutil.TestEvent;
import seedu.todoList.testutil.TestTask;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends ListGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicaltasks();
        TestTask taskToAdd = TypicalTestTask.a1;
        //LogsCenter.getLogger(AddCommandTest.class).info("taskToAdd: " + taskToAdd.getName().toString());
        //LogsCenter.getLogger(AddCommandTest.class).info("taskToAddList: " + currentList.toString());
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add one event
        TestEvent[] currentEventList = ed.getTypicalEvent();
        TestEvent eventToAdd = ed.e6;
        assertAddEventSuccess(eventToAdd, currentEventList);
        currentEventList = TestUtil.addEventsToList(currentEventList, eventToAdd);
        
        //add one deadline
        TestDeadline[] currentDeadlineList = dd.getTypicalDeadline();
        TestDeadline ddToAdd = dd.d6;
        assertAddDeadlineSuccess(ddToAdd, currentDeadlineList);
        currentDeadlineList = TestUtil.addDeadlinesToList(currentDeadlineList, ddToAdd);

        //add another task
        taskToAdd = td.a7;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add another event
        eventToAdd = ed.e7;
        assertAddEventSuccess(eventToAdd, currentEventList);
        currentEventList = TestUtil.addEventsToList(currentEventList, eventToAdd);
        
        //add another deadline
        ddToAdd = dd.d7;
        assertAddDeadlineSuccess(ddToAdd, currentDeadlineList);
        currentDeadlineList = TestUtil.addDeadlinesToList(currentDeadlineList, ddToAdd);

        //add duplicate task
        commandBox.runCommand(td.a6.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //add duplicate event
        commandBox.runCommand(ed.e6.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(eventListPanel.isListMatching(currentEventList));
        
        //add duplicate deadline
        commandBox.runCommand(dd.d6.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(deadlineListPanel.isListMatching(currentEventList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.a1);
        //assertAddEventSuccess(ed.e1);

        //invalid command
        commandBox.runCommand("adds assignment 66");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        //LogsCenter.getLogger(AddCommandTest.class).info("task.length add command: " + taskToAdd.getName().name.toString());

        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateTotask(taskToAdd.getName().name);
        LogsCenter.getLogger(AddCommandTest.class).info("XXX: " + taskToAdd.getName().name.toString());

        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    private void assertAddEventSuccess(TestEvent eventsToAdd, TestEvent... currentList) {
        commandBox.runCommand(eventsToAdd.getAddCommand());

        //confirm the new card contains the right data
        EventCardHandle addedCard = eventListPanel.navigateToevent(eventsToAdd.getName().name);
        assertEventMatching(eventsToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestEvent[] expectedList = TestUtil.addEventsToList(currentList, eventsToAdd);
        assertTrue(eventListPanel.isListMatching(expectedList));
    }
    
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
