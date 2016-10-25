package guitests;

import org.junit.Test;

import seedu.address.model.task.TaskComponent;

import static org.junit.Assert.assertEquals;

public class SelectCommandTest extends TaskMasterGuiTest {


    @Test
    public void selectTask_nonEmptyList() {

        assertSelectionInvalid(20); //invalid index
        assertNoTaskSelected();

        assertSelectionSuccess(1); //first floatingTask in the list
        int floatingTaskCount = td.getTypicalTasks().length;
        assertSelectionSuccess(floatingTaskCount); //last floatingTask in the list
        int middleIndex = floatingTaskCount / 2;
        assertSelectionSuccess(middleIndex); //a floatingTask in the middle of the list

        assertSelectionInvalid(floatingTaskCount + 1); //invalid index
        assertTaskSelected(middleIndex); //assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectTask_emptyList(){
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("Selected Task: "+index);
        assertTaskSelected(index);
    }

    private void assertTaskSelected(int index) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 1);
        TaskComponent selectedTask = taskListPanel.getSelectedTasks().get(0);
        assertEquals(taskListPanel.getTask(index-1), selectedTask);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoTaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }

}
