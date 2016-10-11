package guitests;

import guitests.guihandles.FloatingTaskCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddFloatingCommand;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskListGuiTest {

    @Test
    public void add() {
        //add one floatingTask
        TestTask[] currentList = td.getTypicalTasks();
        TestTask floatingTaskToAdd = td.hoon;
        assertAddSuccess(floatingTaskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, floatingTaskToAdd);

        //add another floatingTask
        floatingTaskToAdd = td.ida;
        assertAddSuccess(floatingTaskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, floatingTaskToAdd);

        //add duplicate floatingTask
        commandBox.runCommand(td.hoon.getAddFloatingCommand());
        assertResultMessage(AddFloatingCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(floatingTaskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.trash);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask floatingTaskToAdd, TestTask... currentList) {
        commandBox.runCommand(floatingTaskToAdd.getAddFloatingCommand());

        //confirm the new card contains the right data
        FloatingTaskCardHandle addedCard = floatingTaskListPanel.navigateToTask(floatingTaskToAdd.getName().fullName);
        assertMatching(floatingTaskToAdd, addedCard);

        //confirm the list now contains all previous floatingTasks plus the new floatingTask
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, floatingTaskToAdd);
        assertTrue(floatingTaskListPanel.isListMatching(expectedList));
    }

}
