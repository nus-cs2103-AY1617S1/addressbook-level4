package guitests;

import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DoneCommand.MESSAGE_DONE_TASK_SUCCESS;
import static seedu.address.logic.commands.DoneCommand.MESSAGE_DONE_TASKS_SUCCESS;

import java.util.ArrayList;
import java.util.List;

//@@author A0139498J
public class DoneCommandTest extends DearJimGuiTest {

    TestTask[] currentList;
    TestTask[] currentDoneList;
    List<Integer> targetIndexesList;
    
    @Before
    public void setUp() {
        currentList = td.getTypicalUndoneTasks();
        currentDoneList = new TestTask[]{};
        targetIndexesList = new ArrayList<Integer>();
    }
    
    @Test
    public void doneCommand_doneFirstIndexInList_archivesFirstTaskinList() {
        int targetIndex = 1;
        assertDoneSuccess(targetIndex, currentList, currentDoneList);
    }

    @Test
    public void doneCommand_doneMiddleIndexInList_archiveMiddleTaskinList() {
        int targetIndex = currentList.length/2;
        assertDoneSuccess(targetIndex, currentList, currentDoneList);
    }
    
    @Test
    public void doneCommand_doneLastIndexInList_archiveLastTaskinList() {   
        int targetIndex = currentList.length;
        assertDoneSuccess(targetIndex, currentList, currentDoneList);
    }
    
    @Test
    public void doneCommand_doneSingleInvalidIndex_showsInvalidTaskIndexMessage() {
        commandBox.runCommand("done " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
    }
    
    @Test
    public void doneCommand_doneFirstAndSecondIndexesInList_archiveFirstAndSecondTasksinList() {   
        targetIndexesList.add(1);
        targetIndexesList.add(2);
        assertDoneSuccessForIndexesList(targetIndexesList, currentList, currentDoneList);
    }
    
    @Test
    public void doneCommand_doneFirstAndLastIndexesInList_archiveFirstAndLastTasksinList() {   
        targetIndexesList.add(1);
        targetIndexesList.add(currentList.length);
        assertDoneSuccessForIndexesList(targetIndexesList, currentList, currentDoneList);
    }
    
    @Test
    public void doneCommand_doneValidAndInvalidIndexesInList_archiveValidIndexesTasksinList() {   
        targetIndexesList.add(1);
        targetIndexesList.add(2);
        targetIndexesList.add(currentList.length+1);
        assertDoneSuccessForIndexesList(targetIndexesList, currentList, currentDoneList);
    }
    
    @Test
    public void doneCommand_doneAllInvalidIndexesInList_showsInvalidTaskIndexMessage() {   
        int invalidIndexOne = currentList.length + 1;
        int invalidIndexTwo = currentList.length + 2;
        int invalidIndexThree = currentList.length + 3;
        targetIndexesList.add(invalidIndexOne);
        targetIndexesList.add(invalidIndexTwo);
        targetIndexesList.add(invalidIndexThree);
        commandBox.runCommand("done " + invalidIndexOne
                + " " + invalidIndexTwo 
                + " " + invalidIndexThree);
        assertResultMessage("The task index provided is invalid");
    }
    
    
    /**
     * Runs the done command to archive the task at specified index and confirms the result is correct.
     * 
     * @param targetIndexOneIndexed To archive the first task in the list, 1 should be given as the target index.
     * @param currentList           A copy of the current list of tasks (before archiving).
     * @param currentDoneList       A copy of the current list of done tasks (before archiving).
     */
    private void assertDoneSuccess(int targetIndexOneIndexed, final TestTask[] currentList, final TestTask[] currentDoneList) {
        TestTask taskToDone = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);
        TestTask[] expectedDoneTaskList = TestUtil.addFloatingTasksToList(currentDoneList, taskToDone);
        commandBox.runCommand("done " + targetIndexOneIndexed);

        // confirm the list now contains all previous tasks except the archived task
        assertTrue(personListPanel.isListMatching(expectedRemainder));
        
        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DONE_TASK_SUCCESS, TestUtil.generateDisplayString(taskToDone)));
        
        // confirm the done list now contains all previous done tasks plus the new done task
        commandBox.runCommand("list done");
        assertTrue(personListPanel.isListMatching(expectedDoneTaskList));
        
        // switch back to normal list view
        commandBox.runCommand("list");
    }
    
    /**
     * Runs the done command to archive the tasks at specified indexes and confirms the result is correct.
     * 
     * @param targetIndexesOneIndexed To archive the first task in the list, 1 should be given as the target index.
     * @param currentList             A copy of the current list of tasks (before archiving).
     * @param currentDoneList         A copy of the current list of done tasks (before archiving).
     */
    private void assertDoneSuccessForIndexesList(List<Integer> targetIndexesOneIndexed, final TestTask[] currentList, final TestTask[] currentDoneList) {
        TestTask taskToDone;
        
        // copy over arrays
        TestTask[] expectedRemainder = new TestTask[currentList.length];
        TestTask[] expectedDoneTaskList = new TestTask[currentDoneList.length];
        System.arraycopy(currentList, 0, expectedRemainder, 0, currentList.length);
        System.arraycopy(currentDoneList, 0, expectedDoneTaskList, 0, currentDoneList.length);
        
        // build up command format and expected task lists
        String command = "done";
        List<TestTask> doneTasks = new ArrayList<TestTask>();
        int offset = 0;
        for (Integer targetIndexOneIndexed : targetIndexesOneIndexed) {
            command += " " + targetIndexOneIndexed;
            if (targetIndexOneIndexed-1 > expectedRemainder.length) {
                continue;
            }
            taskToDone = expectedRemainder[targetIndexOneIndexed-offset-1];
            expectedRemainder = TestUtil.removeTaskFromList(expectedRemainder, targetIndexOneIndexed-offset);
            expectedDoneTaskList = TestUtil.addFloatingTasksToList(expectedDoneTaskList, taskToDone);
            offset++;
            doneTasks.add(taskToDone);
        }
        
        // run the done command with multiple indexes
        commandBox.runCommand(command);
        
        // confirm the list now contains all previous tasks except the archived tasks
        assertTrue(personListPanel.isListMatching(expectedRemainder));
       
        // confirm the result message is correct
        TestTask[] doneTasksArray = doneTasks.toArray(new TestTask[doneTasks.size()]);
        assertResultMessage(String.format(MESSAGE_DONE_TASKS_SUCCESS, 
                TestUtil.generateDisplayString(doneTasksArray)));
       
        // confirm the done list now contains all previous done tasks plus the new done tasks
        commandBox.runCommand("list done");
        assertTrue(personListPanel.isListMatching(expectedDoneTaskList));
       
        // switch back to normal list view
        commandBox.runCommand("list");
    }

}