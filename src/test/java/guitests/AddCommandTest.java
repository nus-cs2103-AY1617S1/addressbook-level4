package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

//@@author A0142325R
public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        // add one person
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.project;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        // add another person
        taskToAdd = td.workshop;
        // currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertAddSuccess(taskToAdd, currentList);

        // add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.friend);

        // add project to current list with flexi command
        taskToAdd = td.project;
        assertFlexiAddSuccess(taskToAdd, td.friend);

        // invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        // confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(personToAdd.getName().taskName);
        assertMatching(personToAdd, addedCard);

        // confirm the list now contains all previous persons plus the new
        // person
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, personToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    private void assertFlexiAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getFlexiAddCommand());

        // confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(personToAdd.getName().taskName);
        assertMatching(personToAdd, addedCard);

        // confirm the list now contains all previous persons plus the new
        // person
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, personToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
