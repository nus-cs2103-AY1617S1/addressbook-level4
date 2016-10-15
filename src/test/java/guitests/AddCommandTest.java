package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.model.item.ItemType;
import seedu.taskmanager.model.item.Time;
import seedu.taskmanager.testutil.TestItem;
import seedu.taskmanager.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one person
        TestItem[] currentList = td.getTypicalItems();
        TestItem itemToAdd = td.deadline3;
        assertAddSuccess(itemToAdd, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);

        //add another person
        itemToAdd = td.task3;
        assertAddSuccess(itemToAdd, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);

        //add duplicate person
        commandBox.runCommand(td.deadline3.getAddCommand(false, false, false, false, false));
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_ITEM);
        assertTrue(personListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.event1);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    @Test
    public void addWithDifferentCommand() {
    	
    	commandBox.runCommand("clear");
    	
        //add one person
        TestItem[] currentList = new TestItem[]{};
        TestItem itemToAdd = td.deadline3;
        assertAddSuccess(itemToAdd, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);

        //add another deadline with 'a' instead of 'add'
        itemToAdd = td.task3;
        assertAddSuccessWithDifferentCommand(itemToAdd, true, false, false, false, false, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);

        //add another deadline with 't' instead of 'task'
        itemToAdd = td.task2;
        assertAddSuccessWithDifferentCommand(itemToAdd, false, true, false, false, false, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);
        
        //add another deadline with 'd' instead of 'deadline'
        itemToAdd = td.deadline2;
        assertAddSuccessWithDifferentCommand(itemToAdd, false, true, false, false, false, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);
        
        //add another deadline with 'e' instead of 'event'
        itemToAdd = td.event2;
        assertAddSuccessWithDifferentCommand(itemToAdd, false, true, false, false, false, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);
        
        //add another event without endtime and starttime
        itemToAdd = td.event3;
        assertAddSuccessWithDifferentCommand(itemToAdd, false, false, false, true, true, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);
        
        //add another event without starttime
        itemToAdd = td.event1;
        assertAddSuccessWithDifferentCommand(itemToAdd, false, false, false, true, false, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);
        
        //add another deadline without endtime
        itemToAdd = td.deadline1;
        assertAddSuccessWithDifferentCommand(itemToAdd, false, false, false, false, true, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);
        
        //add another task without name prefix
        itemToAdd = td.task1;
        assertAddSuccessWithDifferentCommand(itemToAdd, false, false, true, false, false, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestItem itemToAdd, TestItem... currentList) {
        commandBox.runCommand(itemToAdd.getAddCommand(false, false, false, false, false));

        //confirm the new card contains the right data
        TaskCardHandle addedCard = personListPanel.navigateToPerson(itemToAdd.getName().value);
        assertMatching(itemToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestItem[] expectedList = TestUtil.addItemsToList(currentList, itemToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }
    
    private void assertAddSuccessWithDifferentCommand(TestItem itemToAdd,
    		boolean shortCommand, boolean shortItemType, boolean noNamePrefix, boolean noStartTime, boolean noEndTime,
    		TestItem... currentList) {
        commandBox.runCommand(itemToAdd.getAddCommand(shortCommand, shortItemType, noNamePrefix, noStartTime, noEndTime));

        //confirm the new card contains the right data
        TaskCardHandle addedCard = personListPanel.navigateToPerson(itemToAdd.getName().value);
        if (noStartTime && itemToAdd.getItemType().value.equals(ItemType.EVENT_WORD)) {
            try {
		        itemToAdd.setStartTime(new Time(AddCommand.DEFAULT_START_TIME));
		    } catch (IllegalValueException e) {
		        e.printStackTrace();
		    }
        }
        if (noEndTime && (itemToAdd.getItemType().value.equals(ItemType.EVENT_WORD) || itemToAdd.getItemType().value.equals(ItemType.DEADLINE_WORD))) {
            try {
		        itemToAdd.setEndTime(new Time(AddCommand.DEFAULT_END_TIME));
		    } catch (IllegalValueException e) {
		        e.printStackTrace();
		    }
        }
        assertMatching(itemToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestItem[] expectedList = TestUtil.addItemsToList(currentList, itemToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
