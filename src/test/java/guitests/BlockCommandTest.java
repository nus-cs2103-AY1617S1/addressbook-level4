package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddFloatingCommand;
import seedu.address.logic.commands.AddNonFloatingCommand;
import seedu.address.logic.commands.BlockCommand;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class BlockCommandTest extends TaskListGuiTest {

    @Test
    public void block() {
        //block one slot
        TestTask[] currentList = td.getTypicalTasks();
        TestTask slotToBlock = td.block1;
        assertBlockSuccess(slotToBlock, currentList);
        currentList = TestUtil.addTasksToList(currentList, slotToBlock);

        //block slot is overlapped with tasks
        commandBox.runCommand(td.block2.getBlockCommand());
        assertResultMessage(BlockCommand.MESSAGE_TIMESLOT_OCCUPIED);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //block slot is illegal
        commandBox.runCommand("block from 2 oct 2pm to 2 oct 1pm");
        assertResultMessage(BlockCommand.MESSAGE_ILLEGAL_TIME_SLOT);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //add to empty list
        commandBox.runCommand("clear");
        assertBlockSuccess(td.block1);

        //invalid command
        commandBox.runCommand("blocks from 2 oct 2pm to 2 oct 3pm");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertBlockSuccess(TestTask slotToBlock, TestTask... currentList) {
        commandBox.runCommand(slotToBlock.getBlockCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(slotToBlock.getName().fullName);
        assertMatching(slotToBlock, addedCard);

        //confirm the list now contains all previous floatingTasks plus the new floatingTask
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, slotToBlock);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
}
