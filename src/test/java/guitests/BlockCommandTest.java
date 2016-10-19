package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.cmdo.commons.core.Messages;
import seedu.cmdo.testutil.TestTask;
import seedu.cmdo.testutil.TestUtil;

/*
 * @@author A0141128R
 */
//missing block test case with only one time slot
//missing test case where you try to add a task to the blocked slot
//write test for no allowing to done block task

public class BlockCommandTest extends ToDoListGuiTest {

    @Test
    public void block() {
//        //block a time slot without range
        TestTask[] currentList = td.getTypicalTasks();
//        TestTask timeToBlock = td.meeting;
//        assertBlockSuccess(timeToBlock, currentList);
//        currentList = TestUtil.addTasksToList(currentList, timeToBlock);
//        
        //add task with date/time range
        TestTask timeToBlock = td.businessDeal;
        assertBlockSuccess(timeToBlock, currentList);
        currentList = TestUtil.addTasksToList(currentList, timeToBlock);
        
        //invalid detail parameter
        commandBox.runCommand("block 'ppp");
        assertResultMessage(Messages.MESSAGE_ENCAPSULATE_DETAIL_WARNING);
        commandBox.runCommand("block ppp'");
        assertResultMessage(Messages.MESSAGE_ENCAPSULATE_DETAIL_WARNING);
        commandBox.runCommand("block ''");
        assertResultMessage(Messages.MESSAGE_BLANK_DETAIL_WARNING);
        
        //unable to pass test case
        //invalid priority parameter
        commandBox.runCommand("block 'new' /yolo");
        assertResultMessage(Messages.MESSAGE_INVALID_PRIORITY);
        commandBox.runCommand("block 'new'/high");
        assertResultMessage(Messages.MESSAGE_INVALID_PRIORITY_SPACE);
        
        //add to empty list
        commandBox.runCommand("clear");
        assertBlockSuccess(td.deal);

        //invalid command
        commandBox.runCommand("block Johnny");
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
