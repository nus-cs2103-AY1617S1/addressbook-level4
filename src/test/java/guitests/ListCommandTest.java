package guitests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import seedu.malitio.commons.exceptions.IllegalValueException;

public class ListCommandTest extends MalitioGuiTest {

    //@@author A0153006W
    @Test
    public void list() {

        //verify list shows all items after panels are empty
        commandBox.runCommand("find x");
        assertListCommandSuccess();
        assertTrue(floatingTaskListPanel.isListMatching(td.getTypicalFloatingTasks()));
        assertTrue(deadlineListPanel.isListMatching(td.getTypicalDeadlines()));
        try {
            assertTrue(eventListPanel.isListMatching(td.getTypicalEvents()));
        } catch (IllegalArgumentException | IllegalValueException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listTasks() {

        //verify list tasks only updates the tasks panel
        commandBox.runCommand("find x");
        assertListCommandSuccess("floating tasks");
        assertTrue(floatingTaskListPanel.isListMatching(td.getTypicalFloatingTasks()));
        assertDeadlineListSize(0);
        assertEventListSize(0);

        //verify list tasks [DATETIME] updates to all floating tasks
        commandBox.runCommand("find x");
        assertListCommandSuccess("floating tasks", "10-31 2am");
        assertTrue(floatingTaskListPanel.isListMatching(td.getTypicalFloatingTasks()));
    }

    @Test
    public void listDeadlines() {

        //verify list deadlines only updates the deadlines panel
        commandBox.runCommand("find x");
        assertListCommandSuccess("deadlines");
        assertTrue(deadlineListPanel.isListMatching(td.getTypicalDeadlines()));
        assertFloatingTaskListSize(0);
        assertEventListSize(0);

        //verify list deadlines [DATETIME] updates to deadlines with due dates on or after [DATETIME]
        assertListCommandSuccess();
        assertListCommandSuccess("deadlines", "12-27 midnight");
        assertTrue(deadlineListPanel.isListMatching(td.deadline5));
        assertTrue(floatingTaskListPanel.isListMatching(td.getTypicalFloatingTasks()));
        try {
            assertTrue(eventListPanel.isListMatching(td.getTypicalEvents()));
        } catch (IllegalArgumentException | IllegalValueException e) {
            fail();
        }
    }

    @Test
    public void listEvents() {

        //verify list events only updates the events panel
        commandBox.runCommand("find x");
        assertListCommandSuccess("events");
        try {
            assertTrue(eventListPanel.isListMatching(td.getTypicalEvents()));
        } catch (IllegalArgumentException | IllegalValueException e) {
            fail();
        }
        assertFloatingTaskListSize(0);
        assertDeadlineListSize(0);
        
        //verify list events [DATETIME] updates to events that start on or after [DATETIME]
        assertListCommandSuccess();
        assertListCommandSuccess("events", "12-31-17 0000");
        try {
            assertTrue(eventListPanel.isListMatching(td.event6));
        } catch (IllegalArgumentException | IllegalValueException e) {
            fail();
        }
        assertTrue(floatingTaskListPanel.isListMatching(td.getTypicalFloatingTasks()));
        assertTrue(deadlineListPanel.isListMatching(td.getTypicalDeadlines()));
    }
    
    @Test
    public void listDateTime() {
        
        //verify list [DATETIME] updates both deadlines and events
        commandBox.runCommand("find x");
        assertListCommandSuccess("", "12-27");
        assertFloatingTaskListSize(0);
        assertTrue(deadlineListPanel.isListMatching(td.deadline5));
        try {
            assertTrue(eventListPanel.isListMatching(td.getTypicalEvents()));
        } catch (IllegalArgumentException | IllegalValueException e) {
            fail();
        }
    }

    private void assertListCommandSuccess() {
        commandBox.runCommand("list");
        assertResultMessage("Listed all tasks");
    }

    private void assertListCommandSuccess(String taskType) {
        commandBox.runCommand("list " + taskType);
        assertResultMessage("Listed " + taskType);
    }

    private void assertListCommandSuccess(String taskType, String dateTime) {
        commandBox.runCommand("list " + taskType + " " + dateTime);
        if (taskType.isEmpty()) {
            assertResultMessage("Listed all tasks");
        }
        else {
            assertResultMessage("Listed " + taskType);
        }
    }
}
