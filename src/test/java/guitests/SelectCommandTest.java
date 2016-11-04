package guitests;

import org.junit.Test;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.logic.commands.SelectCommand;
import seedu.oneline.model.task.ReadOnlyTask;

import static org.junit.Assert.assertEquals;

public class SelectCommandTest extends TaskBookGuiTest {

    @Test
    public void selectCommand_emptyList_noTaskSelected(){
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }
    
    @Test
    public void selectCommand_invalidIndex_noTaskSelected() {
        assertSelectionInvalid(10); //invalid index
        assertNoTaskSelected();
    }

    @Test
    public void selectCommand_negativeIndex_noTaskSelected() {
        commandBox.runCommand("select -1");
        assertResultMessage(Messages.getInvalidCommandFormatMessage(SelectCommand.MESSAGE_USAGE));
        assertNoTaskSelected();
    }
    
    @Test
    public void selectCommand_firstIndex_taskSelected() {
        assertSelectionSuccess(1); //first task in the list
    }
    
    @Test
    public void selectCommand_lastIndex_taskSelected() {
        int taskCount = td.getTypicalTasks().length;
        assertSelectionSuccess(taskCount); //last task in the list
    }
    
    @Test
    public void selectCommand_middleIndex_taskSelected() {
        int middleIndex = td.getTypicalTasks().length / 2;
        assertSelectionSuccess(middleIndex); //a task in the middle of the list
    }
    
    @Test
    public void selectCommand_secondSelectInvalid_taskSelectedRemains() {
        commandBox.runCommand("select 2");
        int taskCount = td.getTypicalTasks().length;
        commandBox.runCommand("select " + (taskCount + 1));
        assertTaskSelected(2); //assert previous selection remains
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage(String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, index));
        assertTaskSelected(index);
    }

    private void assertTaskSelected(int index) {
        assertEquals(taskPane.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = taskPane.getSelectedTasks().get(0);
        assertEquals(taskPane.getTask(index-1), selectedTask);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoTaskSelected() {
        assertEquals(taskPane.getSelectedTasks().size(), 0);
    }

}
