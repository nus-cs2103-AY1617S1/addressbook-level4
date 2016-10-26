package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.cmdo.commons.core.Messages;
import seedu.cmdo.testutil.TestTask;
import seedu.cmdo.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.cmdo.logic.commands.EditCommand.MESSAGE_EDITED_TASK_SUCCESS;

/**
 * 
 * @author A0141128R
 *
 */
public class EditCommandTest extends ToDoListGuiTest {

    @Test
    public void edit() {

        //edit the time of the first task in the list  
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        String change = "'Eat Buffet'";
        TestTask editedTask = td.editedGrocery;
        assertEditSuccess(targetIndex, currentList,change,editedTask);
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask, targetIndex);


        //edit the priority of the last task in the list
        targetIndex = currentList.length;
        change = "/low";
        editedTask = td.editedZika;
        assertEditSuccess(targetIndex, currentList,change,editedTask);
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask, targetIndex);
        
        //make last task floating
        targetIndex = 1;
        change = "floating";
        editedTask = td.floatingGrocery;
        assertEditSuccess(targetIndex, currentList,change,editedTask);
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex);
        
        //change tags of last task
        targetIndex = currentList.length;
        change = "-dangerous";
        editedTask = td.taggedZika;
        assertEditSuccess(targetIndex, currentList,change,editedTask);
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex);
        
        //remove priority of first task
        targetIndex = 1;
        change = "remove priority";
        editedTask = td.noPriorityGrocery;
        assertEditSuccess(targetIndex, currentList,change,editedTask);
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex);
        
        //change time of task 2
        targetIndex = 2;
        change = "1120";
        editedTask = td.editedHouse1;
        assertEditSuccess(targetIndex, currentList,change,editedTask);
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex);
        
        //change date of task 2
        targetIndex = 2;
        change = "10/20/2016";
        editedTask = td.editedHouse2;
        assertEditSuccess(targetIndex, currentList,change,editedTask);
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex);
        
        //change task 3 to a range task
        targetIndex = 3;
        change = "11/12/2016 1300 to 12/12/2016 1500";
        editedTask = td.editedHouse2;
        assertEditSuccess(targetIndex, currentList,change,editedTask);
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex);
        
        

        //invalid detail parameter
        commandBox.runCommand("edit 1 'ppp");
        assertResultMessage(Messages.MESSAGE_ENCAPSULATE_DETAIL_WARNING);
        commandBox.runCommand("edit 1 ppp'");
        assertResultMessage(Messages.MESSAGE_ENCAPSULATE_DETAIL_WARNING);
        commandBox.runCommand("edit 1 ''");
        assertResultMessage(Messages.MESSAGE_BLANK_DETAIL_WARNING);
        
        //invalid priority parameter
        commandBox.runCommand("edit 1 'new' /yolo");
        assertResultMessage(Messages.MESSAGE_INVALID_PRIORITY);
        commandBox.runCommand("edit 1 'new'/high");
        assertResultMessage(Messages.MESSAGE_INVALID_PRIORITY_SPACE);
        
        //invalid index
        commandBox.runCommand("edit " + currentList.length + 1 + " /high");
        assertResultMessage("The task index provided is invalid");
        
        //edit something from an empty list
        commandBox.runCommand("clear");
        targetIndex = 1;
        commandBox.runCommand("edit " + targetIndex + " /high");
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertEditSuccess(int targetIndexOneIndexed, final TestTask[] currentList, String change, TestTask ed) {
        TestTask editedTask = ed;
 
        commandBox.runCommand("edit " + targetIndexOneIndexed +" " + change);
        
        TestTask[] expectedRemainder = TestUtil.replaceTaskFromList(currentList, editedTask ,targetIndexOneIndexed);
        
        //confirm the new card contains the right data
        TaskCardHandle EditedCard = taskListPanel.navigateToTask(editedTask.getDetail().details);
        assertMatching(editedTask, EditedCard);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));
        
        //confirm the result message is correct
        assertResultMessage(MESSAGE_EDITED_TASK_SUCCESS);
    }

}