package guitests;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ListCommand;
import seedu.address.testutil.TestTask;

import static org.junit.Assert.assertTrue;

import org.junit.Before;

//@@author A0142325R
/**
 * Test GUI for list command
 * @author LiXiaowei
 * Command format: list, list tasks/events/done/undone
 * 
 * Equivalence partitions: emply parameter, tasks, events, done, undone, otherInvalidData
 *
 */
public class ListCommandTest extends TaskManagerGuiTest {
    private TestTask[] currentList;
    
    @Before
    public void setUpLists(){
        currentList=td.getTypicalTasks();
    }
    
    //----------------------------valid cases-----------------------------------------------
    
    //list all tasks

    @Test
    public void listAllTasks_successful(){
        assertListResult("list tasks",td.friend,td.friendEvent,td.lunch,
                td.book,td.work,td.movie);
        assertResultMessage(String.format(ListCommand.MESSAGE_TASK_SUCCESS));
        
    }
    
    //list all events
    
    @Test
    public void listAllEvents_successful(){
        assertListResult("list events", td.meeting,td.travel);
        assertResultMessage(String.format(ListCommand.MESSAGE_EVENT_SUCCESS));
        
    }
    
    //list all tasks and events
    
    @Test
    public void listAllTasksAndEvents_successful(){
        assertListResult("list",currentList);
        assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS));
        
        
    }
    
    //list all done items
    
    @Test
    public void listAllDoneTasksAndEvents_successful(){
        commandBox.runCommand("done 1");
        td.friend.markAsDone();
        assertListResult("list done", td.friend);
        assertResultMessage(String.format(ListCommand.MESSAGE_LIST_DONE_TASK_SUCCESS));
        
    }
    
    //list all undone items
    
    @Test
    public void listAllUndoneTasksAndEvents_successful(){
        
        assertListResult("list undone", td.friendEvent, td.lunch, td.book, td.work, td.movie, td.meeting, td.travel);
        assertResultMessage(String.format(ListCommand.MESSAGE_LIST_UNDONE_TASK_SUCCESS));
    }
    
    //list empty list
    
    @Test
    public void listEmptyLists_successful(){
        commandBox.runCommand("clear");
        assertListResult("list");
        
        
    }
    
    //----------------------------invalid cases-------------------------------------------
    
    //invalid command
    
    @Test
    public void listInvalidDataField_fail(){
        commandBox.runCommand("lists");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        commandBox.runCommand("list unknown");
        assertResultMessage(ListCommand.MESSAGE_INVALID_LIST_COMMAND);
        
        
    }
   
    private void assertListResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

}
