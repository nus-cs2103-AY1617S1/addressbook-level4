package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.logic.commands.AddCommand;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskManagerGuiTest {
/*
    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.todo;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.deadline;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.deadline.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.deadline);

        //invalid command
        commandBox.runCommand("adds party");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToPerson(personToAdd.getName().fullName);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addPersonsToList(currentList, personToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }*/

}
