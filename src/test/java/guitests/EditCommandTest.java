package guitests;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Name;
import seedu.address.testutil.TestItem;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_ITEM_SUCCESS;
import static seedu.address.logic.commands.EditCommand.MESSAGE_USAGE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

public class EditCommandTest extends TaskManagerGuiTest {

    @Test
    public void edit() throws IllegalValueException {
        String firstTestName = "Survive 2103";
        
        //edit the first in the list
        TestItem[] currentList = td.getTypicalPersons();
        int targetIndex = 1;
        TestItem itemToEdit = currentList[targetIndex-1];
        assertEditSuccess(targetIndex, firstTestName, currentList);
        itemToEdit.setName(new Name(firstTestName));
        currentList = TestUtil.replaceItemFromList(currentList, itemToEdit, targetIndex-1);


        //edit the last in the list
        targetIndex = currentList.length;
        String secondTestName = "Survive the semester";
        itemToEdit = currentList[targetIndex-1];
        assertEditSuccess(targetIndex, secondTestName, currentList);
        itemToEdit.setName(new Name(secondTestName));
        currentList = TestUtil.replaceItemFromList(currentList, itemToEdit, targetIndex-1);

        //edit from the middle of the list
        targetIndex = currentList.length/2;
        String thirdTestName = "Survive university";
        itemToEdit = currentList[targetIndex-1];
        itemToEdit.setName(new Name(secondTestName));
        assertEditSuccess(targetIndex, thirdTestName, currentList);

        //invalid commands
        commandBox.runCommand("edit notAnIndex");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        
        commandBox.runCommand("edits 1");
        assertResultMessage(MESSAGE_UNKNOWN_COMMAND);
        
        //invalid index
        commandBox.runCommand("edit " + currentList.length + 1 + " n/valid name");
        assertResultMessage(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    /**
     * Runs the edit command to edit the item at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to edit the first item in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of items (before editing).
     */
    private void assertEditSuccess(int targetIndexOneIndexed, String name, final TestItem[] currentList) throws IllegalValueException {
        TestItem itemToEdit = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        itemToEdit.setName(new Name(name));

        TestItem[] expectedList = TestUtil.replaceItemFromList(currentList, itemToEdit, targetIndexOneIndexed-1);
        
        commandBox.runCommand("edit " + targetIndexOneIndexed + " n/" + name);

        //confirm the list now contains all items including the edited item
        assertTrue(personListPanel.isListMatching(expectedList));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_ITEM_SUCCESS, itemToEdit));
    }

}
