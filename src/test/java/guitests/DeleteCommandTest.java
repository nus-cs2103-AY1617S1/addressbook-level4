package guitests;

import org.junit.Test;

import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.logic.commands.DeleteCommand;
import seedu.taskitty.testutil.TestTaskList;

import static org.junit.Assert.assertTrue;

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
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX + ": t1 ");
        
        //duplicate index provided
        commandBox.runCommand("delete e1 e1");
        assertResultMessage(Messages.MESSAGE_DUPLICATE_INDEXES_PROVIDED + ": e1 ");
        
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
        commandBox.runViewAllCommand();
        
        StringBuilder resultMessage = new StringBuilder(String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS_HEADER, 1));
        
        currentList.removeTaskFromList(targetIndexOneIndexed - 1, category);
        commandBox.runCommand("delete " + category + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(currentList.isListMatching(taskListPanel));

        //confirm the result message is correct
        resultMessage.append(category);
        resultMessage.append(targetIndexOneIndexed + " ");
        assertResultMessage(resultMessage.toString());
    }
    
    /**
     * 
     * Runs the delete command to delete the tasks at specified index and confirms the result is correct.
     * @param targetIndexes the indexes in order to delete from
     * @param categories the categories in order in which to delete from
     * @param currentList A copy of the current list of tasks (before deletion).     
     */
    private void assertDeleteSuccess(int[] targetIndexes, char[] categories, final TestTaskList currentList) {
        commandBox.runViewAllCommand();
        
        StringBuilder commandText = new StringBuilder("delete ");
        StringBuilder resultMessage = new StringBuilder(String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS_HEADER, targetIndexes.length));
        
        for (int i = 0; i < targetIndexes.length; i++) {
            
            currentList.removeTaskFromList(targetIndexes[i] - 1, categories[i]);
            
            commandText.append(categories[i]);
            commandText.append(targetIndexes[i] + " ");
            
            resultMessage.append(categories[i]);
            resultMessage.append(targetIndexes[i] + " ");
        }
        
        commandBox.runCommand(commandText.toString());
                
        //confirm the list now contains all previous persons except the deleted person
        assertTrue(currentList.isListMatching(taskListPanel));

        //confirm the result message is correct
        assertResultMessage(resultMessage.toString());
    }
    
}
