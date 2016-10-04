package guitests;

import guitests.guihandles.PersonCardHandle;
import org.junit.Test;

import tars.commons.core.Messages;
import tars.logic.commands.AddCommand;
import tars.testutil.TestPerson;
import tars.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TarsGuiTest {

    @Test
    public void add() {
        //add one task
        TestPerson[] currentList = td.getTypicalPersons();
        TestPerson taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.hoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestPerson taskToAdd, TestPerson... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        PersonCardHandle addedCard = taskListPanel.navigateToPerson(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestPerson[] expectedList = TestUtil.addPersonsToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
