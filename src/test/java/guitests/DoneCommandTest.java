package guitests;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.testutil.TestTask;

import static org.junit.Assert.assertTrue;

import org.junit.Before;

//@@author A0146123R

/**
 * test for done command execution on gui
 * @author LiXiaowei
 * 
 * use scenarios: 1) mark a task as done by index as the last shown list
 *                2) mark a task as done by name
 *
 */

public class DoneCommandTest extends TaskManagerGuiTest {
    
    TestTask[] currentList;
    
    @Before
    public void setUpLists(){
        currentList=td.getTypicalTasks();
    }
    
    //---------------------------------valid cases--------------------------------------------

    //test for scenario1: mark done by index
    
    @Test
    public void markDoneByIndex_successful() {
        
        currentList[0].markAsDone();
        assertDoneSuccess("done 1", currentList);
        
    }
    
    //test for scenario 2: mark done by name
    
    @Test
    public void markDoneByName_successful(){
        
        // Mark task as done by name
        currentList[4].markAsDone();
        commandBox.runCommand("done Work");
        assertResultMessage(DoneCommand.MULTIPLE_TASK_SATISFY_KEYWORD);
        assertDoneSuccess("done 1", currentList);
        
        // Mark task as done by name with multiple satisfied
        currentList[2].markAsDone();
        commandBox.runCommand("done friends");
        assertResultMessage(DoneCommand.MULTIPLE_TASK_SATISFY_KEYWORD);
        assertDoneSuccess("done 3", currentList);
        
        
    }
    
    //-------------------------------------invalid cases---------------------------------------
    
    //test for invalid index
    
    @Test
    public void markDoneByInvalidIndex_fail(){
        commandBox.runCommand("done 12");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
    }
    
    //test for nonexistant name
    
    @Test
    public void markDoneByNonExistantName_fail(){
        
        commandBox.runCommand("done dinner");
        assertResultMessage(DoneCommand.TASK_NOT_FOUND);
    }

    private void assertDoneSuccess(String command, TestTask... currentList) {
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(currentList));
    }

}
