package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;

/*
 * Responsible for testing the correct execution of DeleteCommand
 */

public class DeleteCommandTest extends CommandTest{
    
    
    /*
     * DeleteCommand format: delete index/name
     * 
     * 
     * Equivalence partitions for index: negative integer,positive integer exists in the list,non-integer,
     * positive integer that does not exist in the list
     * 
     * Equivalence partitions for name: alphanumeric characters that exists in the list
     * , non-alphanumeric characters, valid alphanumeric character name that does not exist in the list
     */
    
    
   //-------------------------test for invalid commands------------------------------------------------
    
    
    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertAbsenceKeywordFormatBehaviorForCommand("delete", expectedMessage);
    }
    
    
    /*
     * Confirms the 'invalid argument index number behavior' for the given command
     * targeting a single task in the shown list, using visible index.
     */
    
    @Test
    public void execute_deleteInvalidIndexFormat_errorMessageShown() throws Exception{
        String expectedMessage=String.format(Messages.MESSAGE_INVALID_TASK_NAME);
        assertCommandBehavior("delete",String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertCommandBehavior("delete" + " +1", expectedMessage); //index should be unsigned
        assertCommandBehavior("delete"+ " -1", expectedMessage); //index should be unsigned
        assertCommandBehavior("delete" + " 0", expectedMessage); //index cannot be 0 
        assertCommandBehavior("delete"+" 0.5 ",expectedMessage);//index should be a positive integer
    }

    //positive integer index that does not exist in the list
    
    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }
    
    
    //test for valid alphanumeric names that does not exist in the list
    
    @Test
    public void execute_deleteValidNameNotFound_errorMessageShown() throws Exception{
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);

        assertAbsenceKeywordFormatBehaviorForCommand("delete go to school",Messages.MESSAGE_INVALID_TASK_NAME);
        
    }
    
    
    //------------------------------test for valid cases------------------------------------------------
    
    
    /*
     * Valid test scenarios
     * 
     * Assumptions: all input parameters are correct
     * 
     * Possible scenarios:
     *  - Delete a task or event by index in the last shown list
     *  - Delete a task or event by name with only one unique occurrence in the last shown list
     *  - Delete a task or event by name with multiple occurrences in the last shown list
     * 
     * Corresponding expected result: 
     *  - task manager list no longer contains the specified task or event
     *  - task manager list no longer contains the specified task or event
     *  - a list of tasks or events with the same name is shown, and the user is expected to 
     *  delete the task or event by index in the last shown list
     *  
     */
    
    //Test for the first scenario: delete a task or event by its valid index
    
    @Test
    public void execute_delete_removesCorrectTaskByIndex() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }
    
    //Test for the second scenario: delete a task or event by its exact name
    
    @Test
    public void execute_delete_removesCorrectTaskByUniqueName() throws Exception{
        TestDataHelper helper=new TestDataHelper();
        List<Task> fourTasks=helper.generateTaskList(3);
        fourTasks.add(helper.getFloatingTask());
        
        TaskManager expectedAB=helper.generateTaskManager(fourTasks);
        expectedAB.removeTask(fourTasks.get(3));
        helper.addToModel(model, fourTasks);
        
        assertCommandBehavior("delete Visit grandma", 
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS,fourTasks.get(3)),
                expectedAB,expectedAB.getTaskList());
    }
    
    //Test for the third scenario: delete a task or event with duplicated names

    @Test
    public void execute_delete_removesCorrectTaskByMultipleNameOccurrence() throws Exception{
        TestDataHelper helper=new TestDataHelper();
        List<Task> fiveTasks=helper.generateTaskList(3);
        fiveTasks.add(helper.getFloatingTask());
        fiveTasks.add(helper.getDuplicateDeadlineTask());
       
        TaskManager expectedAB = helper.generateTaskManager(fiveTasks);
        List<Task> expectedList = helper.generateTaskList(fiveTasks.get(3),fiveTasks.get(4));
        helper.addToModel(model, fiveTasks);

        assertCommandBehavior("delete Visit grandma",
                String.format(DeleteCommand.MESSAGE_DELETE_SAME_NAME),
                expectedAB,
                expectedList);
    }
    


}
