package guitests;

import org.junit.Test;

import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.logic.commands.Command;
import seedu.taskitty.logic.commands.DeleteCommand;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestTaskList;

import static org.junit.Assert.assertTrue;
import static seedu.taskitty.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@@author A0139052L
public class DeleteCommandTest extends TaskManagerGuiTest {

    @Test
    public void delete() {
        
        //delete using todo/default
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        
        int targetIndex = currentList.size();
        assertDeleteSuccess(targetIndex, currentList);

        //delete from deadline and event at once
        targetIndex = currentList.size('d');
        int targetIndex2 = currentList.size('e');
        assertDeleteSuccess(new int[] {targetIndex, targetIndex2}, new char[]{ 'd', 'e' }, currentList);
        
        //index out of range
        commandBox.runCommand("delete t" + (currentList.size('t') + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX + ": t1");
        
        //invalid index
        commandBox.runCommand("delete tt");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                Command.MESSAGE_FORMAT + DeleteCommand.MESSAGE_PARAMETER));
        
        //duplicate index provided
        commandBox.runCommand("delete e1 e1");
        assertResultMessage(Messages.MESSAGE_DUPLICATE_INDEXES_PROVIDED + ": e1");
        
        //invalid command
        commandBox.runCommand("deletes e" + (currentList.size('e')));
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
    }
    
    @Test 
    public void deleteContinous() {
        
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        
        assertDeleteSuccess(1, 2, 'e', currentList);
        
        //invalid second index larger than first
        commandBox.runCommand("delete t3-1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                Command.MESSAGE_FORMAT + DeleteCommand.MESSAGE_PARAMETER));       
        
        //invalid second index
        commandBox.runCommand("delete e1-t");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Command.MESSAGE_FORMAT + DeleteCommand.MESSAGE_PARAMETER));       
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
        
        TestTask taskToRemove = currentList.getTaskFromList(targetIndexOneIndexed - 1, category);
        currentList.removeTaskFromList(targetIndexOneIndexed - 1, category);
        commandBox.runCommand("delete " + category + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(currentList.isListMatching(taskListPanel));

        //confirm the result message is correct
        resultMessage.append(taskToRemove.getName());
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
            TestTask taskToRemove = currentList.getTaskFromList(targetIndexes[i] - 1, categories[i]);
            currentList.removeTaskFromList(targetIndexes[i] - 1, categories[i]);
            
            commandText.append(categories[i]);
            commandText.append(targetIndexes[i] + " ");
            
            resultMessage.append(taskToRemove.getName() + ", ");
        }
        
        commandBox.runCommand(commandText.toString());
                
        //confirm the list now contains all previous persons except the deleted person
        assertTrue(currentList.isListMatching(taskListPanel));

        //confirm the result message is correct
        assertResultMessage(resultMessage.substring(0, resultMessage.length() - 2));
    }
    

    /**
     * Runs the delete command to delete the tasks at the category given from the first index to second index
     * @param firstIndex the index to start deleting from
     * @param secondIndex the final index to finish deleting at
     * @param category the category to decide the list to delete from
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int firstIndex, int secondIndex, char category, final TestTaskList currentList) {
        commandBox.runViewAllCommand();
        
        StringBuilder resultMessage = new StringBuilder(String.format
                (DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS_HEADER, secondIndex - firstIndex + 1));
        
        for (int i = firstIndex; i <= secondIndex; i++) {
            TestTask taskToRemove = currentList.getTaskFromList(firstIndex - 1, category);
            currentList.removeTaskFromList(firstIndex - 1, category);
            
            resultMessage.append(taskToRemove.getName() + ", ");
        }
        
        commandBox.runCommand("delete " + category + firstIndex + "-" + secondIndex);
                
        //confirm the list now contains all previous persons except the deleted person
        assertTrue(currentList.isListMatching(taskListPanel));

        //confirm the result message is correct
        assertResultMessage(resultMessage.substring(0, resultMessage.length() - 2));
    }
}
