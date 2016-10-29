package guitests;

import guitests.guihandles.TaskCardHandle;

import org.junit.Test;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.testutil.TestTask;
import seedu.savvytasker.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

//@@author A0139915W
public class ModifyCommandTest extends SavvyTaskerGuiTest {

    @Test
    public void add() {
        //modify task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToModify = currentList[0];
        taskToModify.setStartDateTime(getDate("30/12/2016"));
        taskToModify.setEndDateTime(getDate("31/12/2016"));
        assertModifySuccess("modify 1 s/30-12-2016 e/31-12-2016", taskToModify, currentList);
        currentList = TestUtil.replaceTaskFromList(currentList, taskToModify);
        
        taskToModify.setStartDateTime(null);
        taskToModify.setEndDateTime(null);
        assertModifySuccess("modify 1 s/ e/", taskToModify, currentList);
        currentList = TestUtil.replaceTaskFromList(currentList, taskToModify);

        //modify invalid index
        commandBox.runCommand("modify " + currentList.length + "1" + " s/sat");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX));
        
        //modify with invalid end date
        commandBox.runCommand("modify 1 s/31-12-2016 e/30-12-2016");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_START_END));
    }

    private void assertModifySuccess(String command, TestTask taskToModify, TestTask... currentList) {
        commandBox.runCommand(command);

        //confirm the new card contains the right data
        TaskCardHandle modifiedCard = taskListPanel.navigateToTask(taskToModify.getTaskName());
        assertMatching(taskToModify, modifiedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.replaceTaskFromList(currentList, taskToModify);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private Date getDate(String ddmmyyyy) {
        try {
            return format.parse(ddmmyyyy);
        } catch (Exception e) {
            assert false; //should not get an invalid date....
        }
        return null;
    }

}
//@@author
