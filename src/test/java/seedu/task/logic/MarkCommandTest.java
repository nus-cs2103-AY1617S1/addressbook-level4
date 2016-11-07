package seedu.task.logic;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import seedu.task.logic.commands.MarkCommand;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Task;

/**
 * @@author A0121608N
 * Responsible for testing the execution of MarkCommand
 * 
 */

public class MarkCommandTest extends CommandTest{

    
    //------------------------Tests for invalid arguments format----------------
    /*
     * Command input: "mark (index)"
     * Equivalence partitions for index: valid arguments, word, 
     *          signed integers, 0
     * 
     * Valid arguments for index: unsigned integer
     * Invalid arguments for index to test: "+1", "-1", "0", "not_a_number"
     * 
     * The test cases below test 1 invalid argument at a time
     */
    
    @Test
    public void mark_invalidArgs_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        assertTaskIncorrectIndexFormatBehaviorForCommand("mark", expectedMessage);
    }
    
    
    //------------------------Tests for index not found----------------
    /*
     * Command input: "mark (index)"
     * 
     * Valid arguments for index: unsigned integer (max = size of last shown list)
     * Invalid arguments for index: max + 1
     * 
     * The test cases below test for an index that is out of bounds for the task list
     */
    
    @Test
    public void mark_indexNotFound_errorMessageShown() throws Exception {
        assertTaskIndexNotFoundBehaviorForCommand("mark");
    }
    
    
    //------------------------Test for correct execution of mark command----------------
    /*
     * Assumptions: arguments are all valid
     * 
     * Scenario of mark command:
     * - marks a specified command (status = !status)
     * 
     * Corresponding expected result:
     * - specified task in task list changes in status
     * 
     * The test cases below tests the scenario and validates the result
     */
    
    @Test
    public void mark_secondIndex_success() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        
        Task t1 = helper.generateTask(1);
        Task t2 = helper.generateTask(2);
        Task t3 = helper.generateTask(3);
        
        List<Task> threeTasks = helper.generateTaskList(3);
        List<Task> expectedTBList = helper.generateTaskList(3);
        List<Task> expectedList = helper.generateTaskList(t1, t3);
        helper.addTaskToModel(model, threeTasks);

        TaskBook expectedTB = helper.generateTaskBook_Tasks(expectedTBList);
        expectedTB.markTask(t2);


        assertTaskCommandBehavior("mark 2",
                String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, 2),
                expectedTB,expectedList);
    }
}
