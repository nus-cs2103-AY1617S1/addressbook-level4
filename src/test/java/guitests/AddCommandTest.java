package guitests;

import guitests.guihandles.DeadlineCardHandle;
import guitests.guihandles.EventCardHandle;
import guitests.guihandles.FloatingTaskCardHandle;
import org.junit.Test;

import seedu.malitio.testutil.TestDeadline;
import seedu.malitio.testutil.TestEvent;
import seedu.malitio.testutil.TestFloatingTask;
import seedu.malitio.testutil.TestUtil;
import seedu.malitio.ui.DeadlineListPanel;
import seedu.malitio.ui.FloatingTaskListPanel;
import seedu.malitio.commons.core.Messages;
import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.logic.commands.AddCommand;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends MalitioGuiTest {

    @Test
    public void addTask() {
        //add one task
        TestFloatingTask[] currentList = td.getTypicalFloatingTasks();
        TestFloatingTask taskToAdd = td.manualFloatingTask1;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.manualFloatingTask2;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.manualFloatingTask1.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(floatingTaskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.floatingTask1);

        //invalid command
        commandBox.runCommand("adds run");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void addDeadline() {

          //add one deadline
        TestDeadline[] currentList = td.getTypicalDeadlines();
        TestDeadline deadlineToAdd = td.manualDeadline1;
        assertAddSuccess(deadlineToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, deadlineToAdd);

        //add another deadline
        deadlineToAdd = td.manualDeadline2;
        assertAddSuccess(deadlineToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, deadlineToAdd);

        //add duplicate deadline
        commandBox.runCommand(td.deadline1.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_DEADLINE);
        assertTrue(deadlineListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.deadline1);
       }
    
    @Test
    public void addEvent() throws IllegalArgumentException, IllegalValueException {

          //add one event
        TestEvent[] currentList = td.getTypicalEvents();
        TestEvent eventToAdd = td.manualEvent1;
        assertAddSuccess(eventToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, eventToAdd);

        //add another event
        eventToAdd = td.manualEvent2;
        assertAddSuccess(eventToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, eventToAdd);

        //add duplicate event
        commandBox.runCommand(td.manualEvent1.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_EVENT);
        assertTrue(eventListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.event1);
       }

    private void assertAddSuccess(TestFloatingTask taskToAdd, TestFloatingTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        FloatingTaskCardHandle addedCard = floatingTaskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestFloatingTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(floatingTaskListPanel.isListMatching(expectedList));
    }
    
    private void assertAddSuccess(TestDeadline deadlineToAdd, TestDeadline... currentList) {
        commandBox.runCommand(deadlineToAdd.getAddCommand());

        //confirm the new card contains the right data
        DeadlineCardHandle addedCard = deadlineListPanel.navigateToTask(deadlineToAdd.getName().fullName);
        assertMatching(deadlineToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestDeadline[] expectedList = TestUtil.addTasksToList(currentList, deadlineToAdd);
        assertTrue(deadlineListPanel.isListMatching(expectedList));
    }
    
    private void assertAddSuccess(TestEvent eventToAdd, TestEvent... currentList) throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(eventToAdd.getAddCommand());

        //confirm the new card contains the right data
        EventCardHandle addedCard = eventListPanel.navigateToTask(eventToAdd.getName().fullName);
        assertMatching(eventToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestEvent[] expectedList = TestUtil.addTasksToList(currentList, eventToAdd);
        assertTrue(eventListPanel.isListMatching(expectedList));
    }

}
