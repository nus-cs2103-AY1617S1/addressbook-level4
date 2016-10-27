package guitests;

import org.junit.Test;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.logic.commands.ClearCommand;
import seedu.taskmanager.logic.commands.RedoCommand;
import seedu.taskmanager.logic.commands.UndoCommand;
import seedu.taskmanager.model.item.ItemType;
import seedu.taskmanager.model.item.Name;
import seedu.taskmanager.testutil.TestItem;
import seedu.taskmanager.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

//@@author A0065571A
public class RedoCommandTest extends TaskManagerGuiTest {

    @Test
    public void redo() {
        
        // redo once
        TestItem[] currentList = td.getTypicalItems();
        currentList = assertRedoSuccess(1, currentList);

        // redo twice
        currentList = assertRedoSuccess(2, currentList);

        // redo thrice
        currentList = assertRedoSuccess(3, currentList);
    }
    
    @Test
    public void redoFailure() {
        // redo but can't
    	commandBox.runCommand("select 1");
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_DONE_FAILURE);
    }

    private TestItem[] assertRedoSuccess(int numTimes, TestItem... currentList) {
        for (int i=0; i<numTimes; i++) {
            String itemName = UUID.randomUUID().toString();
            itemName = itemName.replaceAll("\\-", "");
            commandBox.runCommand("add task " + itemName);
            TestItem itemToAdd = new TestItem();
	        try {
	            ItemType typeTask = new ItemType(ItemType.TASK_WORD);
	            itemToAdd.setItemType(typeTask);
	        } catch (IllegalValueException e) {
	            e.printStackTrace();
	        }
	        try {
	            Name nameOfTask = new Name(itemName);
	            itemToAdd.setName(nameOfTask);
	        } catch (IllegalValueException e) {
	            e.printStackTrace();
	        }
	        
	        currentList = TestUtil.addItemsToList(currentList, itemToAdd);
        }
        for (int i=0; i<numTimes; i++) {
            commandBox.runCommand("undo");
        }
        for (int i=0; i<numTimes; i++) {
            commandBox.runCommand("redo");
        }
        assertTrue(shortItemListPanel.isListMatching(currentList));
        return currentList;
    }    
    
}
