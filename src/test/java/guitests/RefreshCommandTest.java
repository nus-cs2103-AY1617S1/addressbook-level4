package guitests;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.RefreshCommand;
import seedu.address.testutil.TestTask;

import static org.junit.Assert.assertTrue;

//@@author A0142325R

/**
 * test for refresh command on gui
 * 
 * @author LiXiaowei
 *
 */
public class RefreshCommandTest extends TaskManagerGuiTest {

    //-------------------------------------valid cases----------------------------------------
   
    //test for valid cases
    
    @Test
    public void refreshAllTasksAndEvents_successful() {
        
        TestTask[] currentList = td.getTypicalTasks();
        // refresh all non-recurring tasks
        assertRefreshResult("refresh", td.friend, td.friendEvent, td.lunch, td.book, td.work, td.movie, td.meeting,
                td.travel);
        assertResultMessage(String.format(RefreshCommand.MESSAGE_SUCCESS));

        // refresh all tasks including one recurring tasks
        TestTask taskToAdd = td.lecture;
        commandBox.runCommand(taskToAdd.getAddCommand());

        assertRefreshResult("refresh", td.friend, td.friendEvent, td.lunch, td.book, td.work, td.movie, td.meeting,
                td.travel, td.lectureVerifier);
        assertResultMessage(String.format(RefreshCommand.MESSAGE_SUCCESS));

    }
    
    //-----------------------------------invalid cases-------------------------------------
    
    //test for invalid command
    
    @Test
    public void refreshInvalidCommand_fail(){
        commandBox.runCommand("refreshes");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
    }

    private void assertRefreshResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

}
