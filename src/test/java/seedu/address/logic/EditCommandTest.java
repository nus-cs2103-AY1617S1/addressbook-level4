package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandTest.TestDataHelper;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Task;

//@@author A0138717X

/*
 * Responsible for testing the correct execution of editCommand
 */


public class EditCommandTest extends CommandTest {

    /*
     * Format: edit EVENT_NAME [s/START_DATE] [e/END_DATE] [r/RECURRING_EVENT] [p/PRIORITY_LEVEL] or
     * 		   TASK_NAME [d/DEADLINE] [r/RECURRING_EVENT] [p/PRIORITY_LEVEL]
     * Equivalence partitions for parameters: empty parameter, invalid
     * parameter, s/START_DATE, e/END_DATE, d/DEADLINE, r/RECURRING, p/PRIORITY
     *
     */

    //----------------------------Invalid execution------------------------------------------

    //test for valid command format

	@Test
    public void execute_listInvalidFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        assertAbsenceKeywordFormatBehaviorForCommand("edit", expectedMessage);
    }


    /*
     * Confirms the 'duplicate task name' for the given command and
     * 'invalid argument index number' for the given command
     */

    @Test
    public void execute_editInvalidNameFormat_errorMessageShown() throws Exception{
        String expectedMessage = EditCommand.MESSAGE_TASK_NOT_IN_LIST;
        assertCommandBehavior("edit",String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        assertCommandBehavior("edit" + " Read book" + " d/20.10.2016", expectedMessage); //name not found in list
    }

    //positive integer index that does not exist in the list

    @Test
    public void execute_editIndexNotFound_errorMessageShown() throws Exception {
    	assertCommandBehavior("Edit", Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    //test for valid alphanumeric names that does not exist in the list

    @Test
    public void execute_editValidNameNotFound_errorMessageShown() throws Exception{
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);

        assertAbsenceKeywordFormatBehaviorForCommand("edit Go to school n/Do homework", EditCommand.MESSAGE_TASK_NOT_IN_LIST);

    }

    //------------------------------test for valid cases------------------------------------------------

    /*
     * Valid test scenarios
     *
     * Assumptions: all input parameters are correct
     *
     * Possible scenarios:
     *  - Edit a task or event field by name in the list with only one unique occurrence in the shown list
     *  - Edit a task or event field by name and index with multiple occurrences in the last shown list
     *
     * Corresponding expected result:
     *  - task manager list shows changes made to the specified task or event field
     *  - a list of tasks or events with one or some of the input parameters is shown, then user
     *  is expected to edit by index in the last shown list
     *
     */

    //Test for the first scenario: edit a task or event by a valid unique name in the list

    @Test
    public void execute_edit_byName() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task t1 = helper.generateUndoneEventWithName("Read book");
        Task t2 = helper.generateUndoneEventWithName("Study CS2103");
        Task t3 = helper.generateUndoneEventWithName("Assignment CS3230");
        List<Task> threeTasks = helper.generateTaskList(t1,t2,t3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        expectedAB.editTask(threeTasks.get(1), "deadline", "11.10.2016");
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("edit" + " Study CS2103" + " d/11.10.2016",
                String.format(EditCommand.MESSAGE_EVENT_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }

    //Test for the second scenario: edit a task or event with duplicated names

    @Test
    public void execute_edit_byMultipleNameOccurrence() throws Exception{
        TestDataHelper helper=new TestDataHelper();
        List<Task> fiveTasks=helper.generateTaskList(3);
        fiveTasks.add(helper.getFloatingTask());
        fiveTasks.add(helper.getDuplicateDeadlineTask());

        TaskManager expectedAB = helper.generateTaskManager(fiveTasks);
        List<Task> expectedList = helper.generateTaskList(fiveTasks.get(3),fiveTasks.get(4));
        helper.addToModel(model, fiveTasks);

        assertCommandBehavior("edit Visit grandma d/11.10.2016",
                String.format(EditCommand.MESSAGE_EDIT_SAME_NAME),
                expectedAB,
                expectedList);
    }

    //Test for the first scenario: edit a task or event with duplicate names, therefore uses index to edit

    @Test
    public void execute_edit_duplicateNameByIndex() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task t1 = helper.generateUndoneEventWithName("Read book");
        Task t2 = helper.generateUndoneEventWithName("Read book");
        Task t3 = helper.generateUndoneEventWithName("Assignment CS3230");
        List<Task> threeTasks = helper.generateTaskList(t1,t2,t3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        expectedAB.editTask(threeTasks.get(1), "deadline", "11.10.2016");
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("edit" + " Read book" + " d/11.10.2016" + " i/2",
                String.format(EditCommand.MESSAGE_EVENT_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }

}
