package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.model.task.ReadOnlyTask;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class SelectCommandTest extends TaskManagerGuiTest {


    @Test
    public void selectTask_nonEmptyList() {
        assertSelectionInvalid(10); //invalid index
        assertNoTaskSelected();

        assertSelectionSuccess(1); //first task in the list
        
        int taskCount = td.getTypicalTasks().length;
        assertSelectionSuccess(taskCount); //last task in the list
        assertTaskNotSelected(1); // assert that previous selection is not selected
        
        int middleIndex = taskCount / 2;
        assertSelectionSuccess(middleIndex); // a task in the middle of the list
        assertTaskNotSelected(taskCount); // assert that previous selection is not selected

        assertSelectionInvalid(taskCount + 1); //invalid index
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
        ReadOnlyTask selectedTask = taskListPanel.getSelectedTasks().get(0);
        assertEquals(taskListPanel.getTask(index-1), selectedTask);
        
        TaskCardHandle selectedCard = taskListPanel.getTaskCardHandle(index - 1);
        assertTrue(selectedCard.isDetailsShown());
    }
    
    private void assertTaskNotSelected(int index) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = taskListPanel.getSelectedTasks().get(0);
        assertNotEquals(taskListPanel.getTask(index - 1), selectedTask);
        
        TaskCardHandle selectedCard = taskListPanel.getTaskCardHandle(index - 1);
        assertFalse(selectedCard.isDetailsShown());
    }

    private void assertNoTaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }
}
