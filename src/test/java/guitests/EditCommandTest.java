package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

//@@author A0148145E

public class EditCommandTest extends TaskSchedulerGuiTest {

    @Test
    public void edit() {

        int indexToEdit = 1;
        assertNameChangeSuccess(indexToEdit, "change name");

        assertAddressChangeSuccess(indexToEdit, "a changed location");

        assertDateChangeSuccess(indexToEdit, "05-Oct-2016", "15-Nov-2016");

        assertEditWithoutIndex(indexToEdit);
    }

    private void assertEditWithoutIndex(int prevIndex) {
        String changedName = "new name";
        commandBox.runCommand("edit " + changedName);
        assertEquals(taskListPanel.navigateToTask(prevIndex - 1).getFullName(), changedName);
    }
    
    private void assertDateChangeSuccess(int indexToEdit, String changedStartDate, String changedEndDate) {
        //edit the date
        commandBox.runCommand("edit " + indexToEdit + " from " + changedStartDate + " to " + changedEndDate);
        //confirm the editted card contains the right data
        assertTrue(taskListPanel.navigateToTask(indexToEdit - 1).getStartDate().contains(changedStartDate));
        assertTrue(taskListPanel.navigateToTask(indexToEdit - 1).getEndDate().contains(changedEndDate));
    }

    private void assertAddressChangeSuccess(int indexToEdit, String changedAddress) {
        //edit the address
        commandBox.runCommand("edit " + indexToEdit + " at " + changedAddress);
        //confirm the editted card contains the right data
        assertEquals(taskListPanel.navigateToTask(indexToEdit - 1).getLocation(), changedAddress);
    }

    private void assertNameChangeSuccess(int indexToEdit, String changedName) {
        //edit the name
        commandBox.runCommand("edit " + indexToEdit + " " + changedName);
        //confirm the editted card contains the right data
        assertEquals(taskListPanel.navigateToTask(indexToEdit - 1).getFullName(), changedName);
    }
}