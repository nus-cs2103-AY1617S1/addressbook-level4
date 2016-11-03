package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.IncorrectCommand;
import seedu.taskmanager.model.item.ItemType;
import seedu.taskmanager.model.item.Name;
import seedu.taskmanager.model.item.ItemDate;
import seedu.taskmanager.model.item.ItemTime;
import seedu.taskmanager.testutil.TestItem;
import seedu.taskmanager.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.logic.parser.Parser.MESSAGE_DATETIME_PARSE_FAILURE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one person
        TestItem[] currentList = td.getTypicalItems();
        TestItem itemToAdd = td.deadline4;
        assertAddSuccess(itemToAdd, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);

        //add another person
        itemToAdd = td.task4;
        assertAddSuccess(itemToAdd, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);

        //add duplicate person
        commandBox.runCommand(td.deadline4.getAddCommand(false, false, false, false, false));
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_ITEM);
        assertTrue(shortItemListPanel.isListMatching(currentList));

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

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    @Test
    public void addNlpCommand() {
        
        commandBox.runCommand("clear");
        try {
		    assertAddNlpSuccess("next Wednesday 3pm", "next Friday 5pm");
		} catch (IllegalValueException e) {
		    e.printStackTrace();
		}
        try {
		    assertAddNlpSuccess("now", "2 hours later");
		} catch (IllegalValueException e) {
		    e.printStackTrace();
		}
        try {
		    assertAddNlpSuccess("", "ten days later at 10pm");
		} catch (IllegalValueException e) {
		    e.printStackTrace();
		}
        
        
    }
    
    private void assertAddNlpSuccess(String startDateTime, String endDateTime) throws IllegalValueException {
        StringBuilder sb = new StringBuilder();
        String itemType = ItemType.EVENT_WORD;
        if (startDateTime == null || startDateTime.equals("")) {
            itemType = ItemType.DEADLINE_WORD;
        }
        String itemName = UUID.randomUUID().toString();
        itemName = itemName.replaceAll("\\-", "");
        sb.append(AddCommand.COMMAND_WORD + " " + itemType + " ");
        sb.append("n/" + itemName + " ");
        if (itemType.equals(ItemType.EVENT_WORD)) {
            sb.append("sdt/" + startDateTime + " ");
        }
        sb.append("edt/" + endDateTime);
        commandBox.runCommand(sb.toString());
        
        // Get Correct Datetime
        List<Date> startDateTimes = new PrettyTimeParser().parse(startDateTime);
        List<Date> endDateTimes = endDateTimes = new PrettyTimeParser().parse(endDateTime);
        // Just Take First Value for Start and End
        if (endDateTimes.isEmpty() || (startDateTimes.isEmpty() && itemType.equals(ItemType.EVENT_WORD))) {
        	assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_DATETIME_PARSE_FAILURE));
        }
        Date processedStartDateTime;
        Date processedEndDateTime = endDateTimes.get(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String startDate = "";
        String startTime = "";
        String endDate = dateFormat.format(processedEndDateTime);
        String endTime = timeFormat.format(processedEndDateTime);
        if (itemType.equals(ItemType.EVENT_WORD)) {
            processedStartDateTime = startDateTimes.get(0);
            if (processedEndDateTime.before(processedStartDateTime)) {
                assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Command.MESSAGE_END_DATE_TIME_BEFORE_START_DATE_TIME));
            }
            startDate = dateFormat.format(processedStartDateTime);
            startTime = timeFormat.format(processedStartDateTime);
	    }
        
        // Manually Construct TestItem
        TestItem itemToAdd = new TestItem();
		itemToAdd.setName(new Name(itemName));
        if (itemType == ItemType.EVENT_WORD) {
		    itemToAdd.setStartDate(new ItemDate(startDate));
		    itemToAdd.setStartTime(new ItemTime(startTime));
        } else {
        	itemToAdd.setStartDate(new ItemDate(""));
		    itemToAdd.setStartTime(new ItemTime(""));
        }
        itemToAdd.setEndDate(new ItemDate(endDate));
        itemToAdd.setEndTime(new ItemTime(endTime));
		itemToAdd.setItemType(new ItemType(itemType));
        
        TaskCardHandle addedCard = shortItemListPanel.navigateToItem(itemName);
        assertMatching(itemToAdd, addedCard);

    }

    private void assertAddSuccess(TestItem itemToAdd, TestItem... currentList) {
        commandBox.runCommand(itemToAdd.getAddCommand(false, false, false, false, false));

        //confirm the new card contains the right data
        TaskCardHandle addedCard = shortItemListPanel.navigateToItem(itemToAdd.getName().value);
        assertMatching(itemToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestItem[] expectedList = TestUtil.addItemsToList(currentList, itemToAdd);
        assertTrue(shortItemListPanel.isListMatching(expectedList));
    }
    
    private void assertAddSuccessWithDifferentCommand(TestItem itemToAdd,
    		boolean shortCommand, boolean shortItemType, boolean noNamePrefix, boolean noStartTime, boolean noEndTime,
    		TestItem... currentList) {
        commandBox.runCommand(itemToAdd.getAddCommand(shortCommand, shortItemType, noNamePrefix, noStartTime, noEndTime));

        //confirm the new card contains the right data
        TaskCardHandle addedCard = shortItemListPanel.navigateToItem(itemToAdd.getName().value);
        if (noStartTime && itemToAdd.getItemType().value.equals(ItemType.EVENT_WORD)) {
            try {
		        itemToAdd.setStartTime(new ItemTime(AddCommand.DEFAULT_START_TIME));
		    } catch (IllegalValueException e) {
		        e.printStackTrace();
		    }
        }
        if (noEndTime && (itemToAdd.getItemType().value.equals(ItemType.EVENT_WORD) || itemToAdd.getItemType().value.equals(ItemType.DEADLINE_WORD))) {
            try {
		        itemToAdd.setEndTime(new ItemTime(AddCommand.DEFAULT_END_TIME));
		    } catch (IllegalValueException e) {
		        e.printStackTrace();
		    }
        }
        assertMatching(itemToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestItem[] expectedList = TestUtil.addItemsToList(currentList, itemToAdd);
        assertTrue(shortItemListPanel.isListMatching(expectedList));
    }

}
