package guitests;

import org.junit.Test;

import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.commons.util.TaskUtil;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestTaskList;

import static org.junit.Assert.assertTrue;
import static seedu.taskitty.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS_HEADER;

//@@author A0139052L
public class DeleteCommandTest extends TaskManagerGuiTest {

    @Test
    public void delete() {

        //display all tasks
        commandBox.runViewAllCommand();
        
        //delete using todo/default
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        
        int targetIndex = currentList.size();
        assertDeleteSuccess(targetIndex, currentList);

        //delete from deadline and event at once
        targetIndex = currentList.size('d');
        int targetIndex2 = currentList.size('e');
        assertDeleteSuccess(new int[] {targetIndex, targetIndex2}, new char[]{ 'd', 'e' }, currentList);
        
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
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param category the category in which to delete from
     * @param currentList A copy of the current list of tasks (before deletion).     
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, char category, final TestTaskList currentList) {
        TestTask taskToDelete = currentList.getTaskFromList(targetIndexOneIndexed - 1, category); //-1 because array uses zero indexing
        
        currentList.removeTaskFromList(targetIndexOneIndexed - 1, category);
        commandBox.runCommand("delete " + category + targetIndexOneIndexed);
        
        int categoryIndex = TaskUtil.getCategoryIndex(category);
        //confirm the list now contains all previous persons except the deleted person
        assertTrue(currentList.isListMatching(taskListPanel));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS_HEADER, Task.CATEGORIES[categoryIndex], taskToDelete));
    }
    
    /**
     * 
     * Runs the delete command to delete the tasks at specified index and confirms the result is correct.
     * @param targetIndexes the indexes in order to delete from
     * @param categories the categories in order in which to delete from
     * @param currentList A copy of the current list of tasks (before deletion).     
     */
    private void assertDeleteSuccess(int[] targetIndexes, char[] categories, final TestTaskList currentList) {
        
        StringBuilder commandText = new StringBuilder("delete ");
        StringBuilder resultMessage = new StringBuilder();
        
        for (int i = 0; i < targetIndexes.length; i++) {
            
            TestTask taskToDelete = currentList.getTaskFromList(targetIndexes[i] - 1, categories[i]); //-1 because array uses zero indexing
            currentList.removeTaskFromList(targetIndexes[i] - 1, categories[i]);
            
            commandText.append(categories[i]);
            commandText.append(targetIndexes[i] + " ");
            
            int categoryIndex = TaskUtil.getCategoryIndex(categories[i]);
            resultMessage.append(String.format(MESSAGE_DELETE_TASK_SUCCESS_HEADER, Task.CATEGORIES[categoryIndex], taskToDelete));
        }
        
        commandBox.runCommand(commandText.toString());
                
        //confirm the list now contains all previous persons except the deleted person
        assertTrue(currentList.isListMatching(taskListPanel));

        //confirm the result message is correct
        assertResultMessage(resultMessage.toString());
    }
    
}
