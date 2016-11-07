package guitests;

import org.junit.Test;

import seedu.toDoList.model.task.ReadOnlyTask;

import static org.junit.Assert.assertEquals;

public class SelectCommandTest extends TaskManagerGuiTest {


    @Test
    public void selectItem_nonEmptyList() {

        assertSelectionInvalid(10); //invalid index
        assertNoItemSelected();

        assertSelectionSuccess(1); //first person in the list
        int personCount = td.getTypicalTasks().length;
        assertSelectionSuccess(personCount); //last person in the list
        int middleIndex = personCount / 2;
        assertSelectionSuccess(middleIndex); //a person in the middle of the list

        assertSelectionInvalid(personCount + 1); //invalid index
        assertItemSelected(middleIndex); //assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectItem_emptyList(){
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("Selected item: "+index);
        assertItemSelected(index);
    }

    private void assertItemSelected(int index) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedPerson = taskListPanel.getSelectedTasks().get(0);
        assertEquals(taskListPanel.getTask(index-1), selectedPerson);
    }

    private void assertNoItemSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }

}
