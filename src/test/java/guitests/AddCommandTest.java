package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.toDoList.commons.core.Messages;
import seedu.toDoList.testutil.TestTask;
import seedu.toDoList.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import org.junit.Before;

//@@author A0142325R

/**
 * test for add command in gui
 * @author LiXiaowei
 *
 */

public class AddCommandTest extends TaskManagerGuiTest {
    
    TestTask[] currentList;
    TestTask taskToAdd;
    
    @Before
    public void setUpLists(){
        currentList=td.getTypicalTasks();
    }
    
    //------------------------------valid cases---------------------------------------------
    
    //add an event to list
    
    @Test
    public void add_eventToList_success(){
        
        taskToAdd = td.project;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
    }
    
    //add a task to list
    
    @Test
    public void add_taskToList_success(){
        taskToAdd = td.workshop;
        assertAddSuccess(taskToAdd, currentList);
        
    }
    
    //add item to an empty list
    
    @Test
    public void add_toEmptyList_success(){
        commandBox.runCommand("clear");
        assertAddSuccess(td.friend);
        
    }
    
    //use flexi add command
    
    @Test
    public void add_flexiCommandFormat_success(){
        
        taskToAdd = td.project;
        assertFlexiAddSuccess(taskToAdd, currentList);
    }
    
    //-----------------------------invalid cases--------------------------------------------
    
    //invalid command
    
    @Test
    public void add_invalidArgsFormat_fail(){
        
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    
    /**
     * assert add success for general addCommand with correct argument order
     * @param personToAdd
     * @param currentList
     */


    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        // confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(personToAdd.getName().taskName);
        assertMatching(personToAdd, addedCard);

        // confirm the list now contains all previous persons plus the new
        // person
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, personToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    /**
     * assert add success for flexi add command with arguments in any random order
     * @param personToAdd
     * @param currentList
     */

    private void assertFlexiAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getFlexiAddCommand());

        // confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(personToAdd.getName().taskName);
        assertMatching(personToAdd, addedCard);

        // confirm the list now contains all previous persons plus the new
        // person
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, personToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
