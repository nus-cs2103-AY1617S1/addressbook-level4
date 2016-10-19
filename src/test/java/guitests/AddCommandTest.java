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
  /*
          //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.relax;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.prepare;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.relax.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
    */
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.event4);
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
    
    private void assertAddSuccess(TestDeadline taskToAdd, TestDeadline... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        DeadlineCardHandle addedCard = deadlineListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestDeadline[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(deadlineListPanel.isListMatching(expectedList));
    }
    
    private void assertAddSuccess(TestEvent taskToAdd, TestEvent... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        EventCardHandle addedCard = eventListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestEvent[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(eventListPanel.isListMatching(expectedList));
    }

}
