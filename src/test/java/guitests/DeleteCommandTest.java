package guitests;

import org.junit.Test;

import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.commons.util.TaskUtil;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestTaskList;

import static org.junit.Assert.assertTrue;
import static seedu.taskitty.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

public class DeleteCommandTest extends TaskManagerGuiTest {

    @Test
    public void delete() {

        //delete using todo/default
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        
        int targetIndex = currentList.size();
        assertDeleteSuccess(targetIndex, currentList);

        //delete from deadline
        targetIndex = currentList.size('d');
        assertDeleteSuccess(targetIndex, 'd', currentList);

        //delete from event
        targetIndex = currentList.size('e');
        assertDeleteSuccess(targetIndex, 'e', currentList);
        
        //invalid index
        commandBox.runCommand("delete t" + (currentList.size('t') + 1));
        assertResultMessage("The task index provided is invalid");
        
        //invalid command
        commandBox.runCommand("deletes e" + (currentList.size('e')));
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
    }
    
    /**
     * 
     * Runs the delete command to delete the person at specified index and confirms the result is correct 
     * with todo as the default category.
     * @param targetIndexOneIndexed e.g. to delete the first person in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of persons (before deletion).     
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTaskList currentList) {
        assertDeleteSuccess(targetIndexOneIndexed, 't', currentList);
    }
    
    /**
     * 
     * Runs the delete command to delete the person at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first person in the list, 1 should be given as the target index.
     * @param category the category in which to delete from
     * @param currentList A copy of the current list of persons (before deletion).     
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, char category, final TestTaskList currentList) {
        TestTask taskToDelete = currentList.getTaskFromList(targetIndexOneIndexed - 1, category); //-1 because array uses zero indexing
        
        currentList.removeTaskFromList(targetIndexOneIndexed - 1, category);
        commandBox.runCommand("delete " + category + targetIndexOneIndexed);
        
        int categoryIndex = TaskUtil.getCategoryIndex(category);
        //confirm the list now contains all previous persons except the deleted person
        assertTrue(currentList.isListMatching(taskListPanel));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, Task.CATEGORIES[categoryIndex], taskToDelete));
    }
}
