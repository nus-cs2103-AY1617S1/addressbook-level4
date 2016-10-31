package guitests;

import org.junit.Test;

import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.SelectTaskCommand;
import seedu.task.model.item.Flag;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;
import seedu.taskcommons.core.Messages;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;

//@@author A0127570H
public class SelectCommandTest extends TaskBookGuiTest {

    @Test
    public void selectTask_nonEmptyList() {

        assertSelectionInvalid(10,Flag.taskPresenceFlag, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX); //invalid index
        assertNoTaskSelected();

        assertTaskSelectionSuccess(1,Flag.taskPresenceFlag); //first task in the list
        int taskCount = td.getTypicalTasks().length;
        assertTaskSelectionSuccess(taskCount,Flag.taskPresenceFlag); //last task in the list
        int middleIndex = taskCount / 2;
        assertTaskSelectionSuccess(middleIndex,Flag.taskPresenceFlag); //a task in the middle of the list

        assertSelectionInvalid(taskCount + 1, Flag.taskPresenceFlag, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX); //invalid index
        assertTaskSelected(middleIndex); //assert previous selection remains

    }
    
    @Test
    public void selectEvent_nonEmptyList() {

        assertSelectionInvalid(10,Flag.eventPresenceFlag, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX); //invalid index
        assertNoEventSelected();

        assertEventSelectionSuccess(1,Flag.eventPresenceFlag); //first event in the list
        int eventCount = te.getTypicalNotCompletedEvents().length;
        assertEventSelectionSuccess(eventCount,Flag.eventPresenceFlag); //last event in the list
        int middleIndex = eventCount / 2;
        assertEventSelectionSuccess(middleIndex,Flag.eventPresenceFlag); //a event in the middle of the list

        assertSelectionInvalid(eventCount + 1, Flag.eventPresenceFlag, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX); //invalid index
        assertEventSelected(middleIndex); //assert previous selection remains

    }
    
    @Test
    public void incorrectSelectCommand() {
        //Invalid command input
        commandBox.runCommand("select lol");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,SelectCommand.MESSAGE_USAGE));
        
        //Invalid index
        commandBox.runCommand("select /t lol");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,SelectCommand.MESSAGE_USAGE));
        
        commandBox.runCommand("select /e lol");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,SelectCommand.MESSAGE_USAGE));
        
    }

    @Test
    public void selectTask_emptyList(){
        commandBox.runCommand("clear /a");
        assertTaskListSize(0);
        assertSelectionInvalid(1, Flag.taskPresenceFlag, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX); //invalid index
    }
    
    @Test
    public void selectEvent_emptyList(){
        commandBox.runCommand("clear /a");
        assertEventListSize(0);
        assertSelectionInvalid(1, Flag.eventPresenceFlag, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX); //invalid index
    }

    private void assertSelectionInvalid(int index, String prefix, String expectedMessage) {
        commandBox.runCommand("select " + prefix + " " + index);
        assertResultMessage(expectedMessage);
    }

    private void assertTaskSelectionSuccess(int index, String prefix) {
        commandBox.runCommand("select " + prefix + " " + index);
        assertResultMessage("Selected Task: "+index);
        assertTaskSelected(index);
    }
    
    private void assertEventSelectionSuccess(int index, String prefix) {
        commandBox.runCommand("select " + prefix + " " + index);
        assertResultMessage("Selected Event: "+index);
        assertEventSelected(index);
    }

    private void assertTaskSelected(int index) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = taskListPanel.getSelectedTasks().get(0);
        assertEquals(taskListPanel.getTask(index-1), selectedTask);
    }
    
    private void assertEventSelected(int index) {
        assertEquals(eventListPanel.getSelectedEvents().size(), 1);
        ReadOnlyEvent selectedEvent = eventListPanel.getSelectedEvents().get(0);
        assertEquals(eventListPanel.getEvent(index-1), selectedEvent);
    }

    private void assertNoTaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }
    
    private void assertNoEventSelected() {
        assertEquals(eventListPanel.getSelectedEvents().size(), 0);
    }

}
