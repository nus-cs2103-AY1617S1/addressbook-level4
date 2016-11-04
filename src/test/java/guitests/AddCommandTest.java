package guitests;

import guitests.guihandles.TaskCardHandle;
import seedu.stask.commons.core.Messages;
import seedu.stask.logic.commands.AddCommand;
import seedu.stask.testutil.TestTask;
import seedu.stask.testutil.TestUtil;
import seedu.stask.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AddCommandTest extends TaskBookGuiTest {

    @Test
    public void add() {
        //add datedOne task
        TestTask[] currentDatedList = td.getTypicalDatedTasks();
        TestTask taskToAdd = TypicalTestTasks.datedFour;
        assertAddSuccess(taskToAdd, currentDatedList);
        currentDatedList = TestUtil.addTasksToList(currentDatedList, taskToAdd);

        //add another task
        taskToAdd = TypicalTestTasks.datedFive;
        assertAddSuccess(taskToAdd, currentDatedList);
        currentDatedList = TestUtil.addTasksToList(currentDatedList, taskToAdd);
        assertTrue(datedListPanel.isListMatching(currentDatedList));

        //TODO add duplicate, add undated task
        
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTasks.datedOne);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = datedListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous task plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(datedListPanel.isListMatching(expectedList));
    }

}
