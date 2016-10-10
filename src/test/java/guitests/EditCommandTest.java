package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;


public class EditCommandTest extends AddressBookGuiTest {

    @Test
    public void edit() {
        //edit a task
        TestTask[] currentList = td.getTypicalPersons();
        int indexToEdit = 1;
        TestTask personToCopy = td.hoon;
        assertEditSuccess(indexToEdit, personToCopy, currentList);
        currentList[indexToEdit - 1] = personToCopy;
        currentList = TestUtil.addPersonsToList(currentList);
        
        //edit another task
        personToCopy = td.ida;
        indexToEdit = 3;
        assertEditSuccess(indexToEdit, personToCopy, currentList);
        currentList[indexToEdit - 1] = personToCopy;
        currentList = TestUtil.addPersonsToList(currentList);

        //edit with a duplicate task
        indexToEdit = 5;
        commandBox.runCommand("edit " + indexToEdit + " " + td.hoon.getTaskString());
        assertResultMessage(Command.MESSAGE_DUPLICATE_TASK);
        assertTrue(personListPanel.isListMatching(currentList));

        //edit in empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("edit " + indexToEdit + " " + td.hoon.getTaskString());
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        //invalid command
        commandBox.runCommand("edit eee " + td.ida.getTaskString());
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertEditSuccess(int indexToEdit, TestTask personToCopy, TestTask... currentList) {
        commandBox.runCommand("edit " + indexToEdit + " " + personToCopy.getTaskString());

        //confirm the edited card contains the right data
        TaskCardHandle editedCard = personListPanel.navigateToPerson(indexToEdit - 1);
        
        assertMatching(personToCopy, editedCard);

        //confirm the list now contains all previous tasks with the edited task
        TestTask[] expectedList = TestUtil.addPersonsToList(currentList);
        expectedList[indexToEdit - 1] = personToCopy;
        assertTrue(personListPanel.isListMatching(expectedList));
    }
}
