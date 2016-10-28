package guitests;

//@@author A0142325R
import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RefreshCommand;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;


import static org.junit.Assert.assertTrue;

public class RefreshCommandTest extends TaskManagerGuiTest {

    @Test
    public void list_all_Tasks_Events() {
        TestTask[] currentList = td.getTypicalTasks();
        //refresh all non-recurring tasks
        assertRefreshResult("refresh",td.friend,td.friendEvent,td.lunch,
                td.book,td.work,td.movie,td.meeting,td.travel);
        assertResultMessage(String.format(RefreshCommand.MESSAGE_SUCCESS));
        
        //refresh all tasks including one recurring tasks
        TestTask taskToAdd=td.lecture;
        commandBox.runCommand(taskToAdd.getAddCommand());
     
        assertRefreshResult("refresh", td.friend,td.friendEvent,td.lunch,
                td.book,td.work,td.movie,td.meeting,td.travel,td.lectureVerifier);
        assertResultMessage(String.format(RefreshCommand.MESSAGE_SUCCESS));

      
        
        //invalid command
        commandBox.runCommand("refreshes");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        
    }
    
    

    private void assertRefreshResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
    
   

}

