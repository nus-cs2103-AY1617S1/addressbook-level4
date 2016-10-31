package guitests;

import org.junit.Test;

import seedu.flexitrack.logic.commands.BlockCommand;
import seedu.flexitrack.model.task.DateTimeInfo;
import seedu.flexitrack.testutil.TestTask;
import seedu.flexitrack.testutil.TestUtil;
import seedu.flexitrack.testutil.TypicalTestTasks;

import static seedu.flexitrack.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flexitrack.logic.commands.BlockCommand.MESSAGE_SUCCESS;
import static seedu.flexitrack.logic.commands.BlockCommand.MESSAGE_DUPLICATE_TIME;

public class BlockCommandTest extends FlexiTrackGuiTest {
    
    TestTask[] currentList = td.getTypicalSortedTasks();
    TestTask taskToBlock;
    
    @Test
    public void addBlockSuccess() {
        TestTask[] currentList = td.getTypicalSortedTasks();
        taskToBlock = TypicalTestTasks.basketball;
        assertBlockSuccess(taskToBlock, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToBlock);
    }
    
    @Test
    public void addBlockFail() {
        TestTask[] currentList = td.getTypicalSortedTasks();
        taskToBlock = TypicalTestTasks.tutorial1;
        assertBlockFail(taskToBlock, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToBlock);
    }
    
    @Test
    public void addBlockOverlappingFail() {
        TestTask[] currentList = td.getTypicalSortedTasks();
        taskToBlock = TypicalTestTasks.tutorial3;
        assertBlockOverlappingFail(taskToBlock, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToBlock);
    }

    private void assertBlockSuccess(TestTask taskToBlock, TestTask[] currentList2) {
        commandBox.runCommand(taskToBlock.getBlockCommand());
        taskToBlock.setIsBlock(true);;
        assertResultMessage((String.format(MESSAGE_SUCCESS, taskToBlock)) + "\n" + DateTimeInfo
                .durationOfTheEvent(taskToBlock.getStartTime().toString(), taskToBlock.getEndTime().toString()));       
    }
    
    private void assertBlockFail(TestTask taskToBlock, TestTask[] currentList2) {
        commandBox.runCommand("block " + taskToBlock.getName().toString() + " by/ " + taskToBlock.getDueDate());
        //taskToBlock.setIsBlock(true);;
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BlockCommand.MESSAGE_USAGE));       
    }
    
    private void assertBlockOverlappingFail(TestTask taskToBlock, TestTask[] currentList2) {
        commandBox.runCommand(TypicalTestTasks.tutorial2.getAddCommand());
        commandBox.runCommand(taskToBlock.getBlockCommand());
        assertResultMessage(MESSAGE_DUPLICATE_TIME);       
    }
}
