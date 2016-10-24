package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.cmdo.commons.core.Messages;
import seedu.cmdo.testutil.TestTask;
import seedu.cmdo.testutil.TestUtil;

/*
 * @@author A0141128R tested as of 20/10/16
 */

public class BlockCommandTest extends ToDoListGuiTest {

    @Test
    public void block() {
        //block a time slot without range
        TestTask[] currentList = td.getTypicalTasks();
        TestTask timeToBlock = td.meeting;
        assertBlockSuccess(timeToBlock, currentList);
        currentList = TestUtil.addTasksToList(currentList, timeToBlock);
        
        //add block a timeslot with date/time range
        timeToBlock = td.businessDeal;
        assertBlockSuccess(timeToBlock, currentList);
        currentList = TestUtil.addTasksToList(currentList, timeToBlock);
        
        //cannot add task to a blocked timeslot with same timing
        TestTask taskToAdd = td.eat;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertResultMessage(Messages.MESSAGE_TIMESLOT_BLOCKED);
        commandBox.runCommand("list all");//to go back to general list
        
        //cannot done a block task
        commandBox.runCommand("done 5");
        assertResultMessage(Messages.MESSAGE_CANNOT_DONE);
        
        //invalid detail parameter
        commandBox.runCommand("block 'ppp");
        assertResultMessage(Messages.MESSAGE_ENCAPSULATE_DETAIL_WARNING);
        commandBox.runCommand("block ppp'");
        assertResultMessage(Messages.MESSAGE_ENCAPSULATE_DETAIL_WARNING);
        commandBox.runCommand("block ''");
        assertResultMessage(Messages.MESSAGE_BLANK_DETAIL_WARNING);
        
        //block a time slot in an empty list
        commandBox.runCommand("clear");
        assertBlockSuccess(td.deal);

        //invalid command
        commandBox.runCommand("blocks meeting with OCBC");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertBlockSuccess(TestTask timeToBlock, TestTask... currentList) {
 
        commandBox.runCommand(timeToBlock.getBlockCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(timeToBlock.getDetail().details);
        assertMatching(timeToBlock, addedCard);

        //confirm the list now contains the new blocked slot
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, timeToBlock);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
