package seedu.task.logic;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.DeleteEventCommand;
import seedu.task.logic.commands.DeleteTaskCommand;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Event;
import seedu.task.model.item.Task;

/**
 * @@author A0121608N
 * Responsible for testing the execution of DeleteCommand
 * 
 */

public class DeleteCommandTest extends CommandTest {
    
    //------------------------Tests for invalid arguments format----------------
    /*
     * Command input: "delete (type) (index)"
     * Equivalence partitions for type: valid arguments, word,
     *          invalid flags
     * Equivalence partitions for index: valid arguments, word, 
     *          signed integers, 0
     * 
     * 
     * Valid arguments
     * type: "/t", "/e"
     * index: unsigned integer
     * 
     * Invalid arguments to test: 
     * type: "rAndOm", "/r", "/ t"
     * index: "+1", "-1", "0", "not_a_number"
     * 
     * The test cases below test 1 invalid argument at a time
     */
    
    @Test
    public void delete_invalidArgs_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectTypeFormatBehaviorForCommand("delete rAndOm", expectedMessage);
        assertIncorrectTypeFormatBehaviorForCommand("delete /r", expectedMessage);
        assertIncorrectTypeFormatBehaviorForCommand("delete / t", expectedMessage);
    }
    
    @Test
    public void deleteTask_invalidIndex_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE);
        assertTaskIncorrectIndexFormatBehaviorForCommand("delete /t", expectedMessage);
    }

    @Test
    public void deleteEvent_invalidIndex_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE);
        assertEventIncorrectIndexFormatBehaviorForCommand("delete /e", expectedMessage);
    }
    
  //------------------------Tests for index not found----------------
    /*
     * Command input: "delete (type) (index)"
     * 
     * Valid arguments for index: unsigned integer (max = size of last shown list)
     * Invalid arguments for index: max + 1
     * 
     * The test cases below test for an index that is out of bounds for both tasks and events
     */
    
    @Test
    public void deleteTask_indexNotFound_errorMessageShown() throws Exception {
        assertTaskIndexNotFoundBehaviorForCommand("delete /t");
    }
    
    @Test
    public void deleteEvent_indexNotFound_errorMessageShown() throws Exception {
        assertEventIndexNotFoundBehaviorForCommand("delete /e");
    }

    
  //------------------------Test for correct execution of delete command----------------
    /*
     * Assumptions: arguments are all valid
     * 
     * Possible scenario for delete command:
     * - delete a specified task in the last shown task list
     * - delete a specified event in the last shown event list
     * 
     * Corresponding expected result:
     * - task list no longer contains specified task
     * - event list no longer contains specified event
     * 
     * The test cases below test each possible scenario and validates the result
     */
    
    @Test
    public void deleteTask_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBook expectedTB = helper.generateTaskBook_Tasks(threeTasks);
        expectedTB.removeTask(threeTasks.get(1));
        helper.addTaskToModel(model, threeTasks);

        assertTaskCommandBehavior("delete /t 2",
                String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedTB,
                expectedTB.getTaskList());
    }
    

    @Test
    public void deleteEvent_removesCorrectEvent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Event> threeEvents = helper.generateEventList(3);

        TaskBook expectedTB = helper.generateTaskBook_Events(threeEvents);
        expectedTB.removeEvent(threeEvents.get(1));
        helper.addEventToModel(model, threeEvents);

        assertTaskCommandBehavior("delete /e 2",
                String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, threeEvents.get(1)),
                expectedTB,
                expectedTB.getTaskList());
    }
}
