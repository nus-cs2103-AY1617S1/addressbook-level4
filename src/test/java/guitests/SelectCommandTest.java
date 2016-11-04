package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.stask.model.task.ReadOnlyTask;

public class SelectCommandTest extends TaskBookGuiTest {


    @Test
    public void selectDatedTask_nonEmptyList() {

        assertSelectionInvalid("B5"); //invalid index
        assertNoTaskSelected();

        assertSelectionSuccess("B1"); //first task in the list
        int datedTaskCount = td.getTypicalDatedTasks().length;
        assertSelectionSuccess("B"+datedTaskCount); //last task in the list
        int middleIndex = datedTaskCount / 2;
        assertSelectionSuccess("B"+middleIndex); // a task in the middle of the list
        
        assertSelectionInvalid("B" + (datedTaskCount+1)); //invalid index
        assertTaskSelected("B"+middleIndex); //assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectDatedTask_emptyList(){
        commandBox.runCommand("clear");
        assertDatedListSize(0);
        assertSelectionInvalid("B"+1); //invalid index
    }

    private void assertSelectionInvalid(String index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(String index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("Selected Task: "+index);
        assertTaskSelected(index);
    }

    private void assertTaskSelected(String index) {
        assertEquals(datedListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = datedListPanel.getSelectedTasks().get(0);
        assertEquals(datedListPanel.getTask(Integer.parseInt((index.trim().substring(1)))-1), selectedTask);

    }

    private void assertNoTaskSelected() {
        assertEquals(datedListPanel.getSelectedTasks().size(), 0);
    }

}
