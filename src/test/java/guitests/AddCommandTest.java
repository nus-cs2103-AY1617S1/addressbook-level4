package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.logic.commands.AddCommand;
import seedu.ggist.testutil.TestTask;
import seedu.ggist.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask TaskToAdd = td.hoon;
        assertAddSuccess(TaskToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, TaskToAdd);

        //add another task
        TaskToAdd = td.ida;
        assertAddSuccess(TaskToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, TaskToAdd);

        //add duplicate person
        commandBox.runCommand(td.hoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToPerson(personToAdd.getTaskName().taskName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addPersonsToList(currentList, personToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
