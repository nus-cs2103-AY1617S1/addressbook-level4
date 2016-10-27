package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.flexitrack.model.task.ReadOnlyTask;

public class SelectCommandTest extends FlexiTrackGuiTest {

    @Test
    public void selectPerson_nonEmptyList() {

        assertSelectionInvalid(10); // invalid index
        assertNoPersonSelected();

        assertSelectionSuccess(1); // first Task in the list
        int taskCount = td.getTypicalSortedTasks().length;
        assertSelectionSuccess(taskCount); // last Task in the list
        int middleIndex = taskCount / 2;
        assertSelectionSuccess(middleIndex); // a Task in the middle of the list

        assertSelectionInvalid(taskCount + 1); // invalid index
        assertPersonSelected(middleIndex); // assert previous selection remains

        /*
         * Testing other invalid indexes such as -1 should be done when testing
         * the SelectCommand
         */
    }

    @Test
    public void selectPerson_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); // invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("Selected Task: " + index);
        assertPersonSelected(index);
    }

    private void assertPersonSelected(int index) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedPerson = taskListPanel.getSelectedTasks().get(0);
        assertEquals(taskListPanel.getTask(index - 1), selectedPerson);
        // TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoPersonSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }

}
