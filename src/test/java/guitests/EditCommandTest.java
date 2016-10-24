package guitests;

import static seedu.menion.logic.commands.CompleteCommand.MESSAGE_COMPLETED_ACTIVITY_SUCCESS;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.FloatingTaskCardHandle;
import guitests.guihandles.TaskCardHandle;
import seedu.address.testutil.TestActivity;
import seedu.menion.logic.commands.EditCommand;

//@@author: A0139164A

public class EditCommandTest extends ActivityManagerGuiTest {
    
    @Test
    public void Edit() {
        
        TestActivity floating = td.floatingTask;
        TestActivity task = td.task;
        TestActivity event = td.event;
        
        // Edit Name - For all activity.
        commandBox.runCommand("clear");
        commandBox.runCommand(floating.getAddCommand());
        commandBox.runCommand(task.getAddCommand());
        commandBox.runCommand(event.getAddCommand());

        assertFloatingEditNameSuccess(floating, 1, "Floating Hello World");
        assertTaskEditNameSuccess(task, 1, "Task Hello World");
        assertEventEditNameSuccess(event, 1, "Event Hello World");
        refresh(3); // Undo the changes made.
        
        // Edit Note - For all activity.
        assertFloatingEditNoteSuccess(floating, 1, "Floating Hello Note");
        assertTaskEditNoteSuccess(task, 1, "Task Hello Note");
        assertEventEditNoteSuccess(event, 1, "Event Hello Note");
        
        // Edit Date - For Task
        assertTaskEditDateTimeSuccess(task, 1, "30-11-1994");
        
        // Edit Time - For Task
        assertTaskEditDateTimeSuccess(task, 1, "2359");

        
        // Edit Both, Date/Time - For Task
        assertTaskEditBothDateTimeSuccess(task, 1, "10-10-2016 1000");

        // Edit From Date - For Event
        assertEventEditFromDateTimeSuccess(event, 1, "30-11-1994");
       
        // Edit From Time - For Event
        assertEventEditFromDateTimeSuccess(event, 1, "2359");

        // Edit Both, From Date/Time - For Event
        assertEventEditFromDateTimeSuccess(event, 1, "01-01-2016 1000");
        
        // Edit To Date - For Event
        assertEventEditToDateTimeSuccess(event, 1, "20-02-2016");
        
        // Edit to Time - For Event. 
        assertEventEditToDateTimeSuccess(event, 1, "1200");

        // Edit Both, To Date/Time - For Event
        assertEventEditToDateTimeSuccess(event, 1, "02-02-2016 1100");
       
        
        // Current from: 01-01-2016 1000. Current to: 02-02-2016 1100.
        // Edit invalid to/From date& Time.
        // From date/time cannot be after than to date/time. - For Event
        assertinvalidEventFromDateTime(event, 1, "02-02-2016, 1101");
        assertinvalidEventFromDateTime(event, 1, "03-02-2016, 1100");
        assertinvalidEventToDateTime(event, 1, "01-01-2016 0959");
        assertinvalidEventtoDateTime(event, 1, "02-01-2016 100");
        
        // Edit with invalid params for date/time
        // Edit with invalid params for note 
        // Edit with invalid params for name
        
    }
    
    private void refresh(int numTimes) {
        for (int i = 0; i < numTimes; i++) {
            commandBox.runCommand("undo");
        }
    }
    
    private void assertFloatingEditNameSuccess(TestActivity floating, int index, String changes) {
        commandBox.runCommand(floating.getEditNameCommand(index, changes));
        floating = activityListPanel.returnsUpdatedFloatingTask(changes); // Update floatingTask with new changes
        FloatingTaskCardHandle editedCard = activityListPanel.navigateToFloatingTask(floating); // Check against card.
        assertFloatingTaskMatching(floating, editedCard);
        
        // Confirms the result message is correct
        assertResultMessage(String.format(EditCommand.MESSAGE_EDITTED_ACTIVITY_SUCCESS, floating));
    }
    
    private void assertTaskEditNameSuccess(TestActivity task, int index, String changes) {
        commandBox.runCommand(task.getEditNameCommand(index, changes));
        task = activityListPanel.returnsUpdatedTask(changes);
        TaskCardHandle editedCard = activityListPanel.navigateToTask(task);
        assertTaskMatching(task, editedCard);
        
        // Confirms the result message is correct
        assertResultMessage(String.format(EditCommand.MESSAGE_EDITTED_ACTIVITY_SUCCESS, task));
    }
    
    private void assertEventEditNameSuccess(TestActivity event, int index, String changes) {
        commandBox.runCommand(event.getEditNameCommand(index, changes));
        event = activityListPanel.returnsUpdatedEvent(changes);
        EventCardHandle editedCard = activityListPanel.navigateToEvent(event);
        assertEventMatching(event, editedCard);
        
        // Confirms the result message is correct
        assertResultMessage(String.format(EditCommand.MESSAGE_EDITTED_ACTIVITY_SUCCESS, event));
    }
    
    private void assertFloatingEditNoteSuccess(TestActivity floating, int index, String changes) {
        commandBox.runCommand(floating.getEditNoteCommand(index, changes));
        floating = activityListPanel.returnsUpdatedFloatingTask(floating.getActivityName().fullName); // Update floatingTask with new changes
        FloatingTaskCardHandle editedCard = activityListPanel.navigateToFloatingTask(floating); // Check against card.
        assertFloatingTaskMatching(floating, editedCard);
        
        // Confirms the result message is correct
        assertResultMessage(String.format(EditCommand.MESSAGE_EDITTED_ACTIVITY_SUCCESS, floating));
    }
    
    private void assertTaskEditNoteSuccess(TestActivity task, int index, String changes) {
        commandBox.runCommand(task.getEditNoteCommand(index, changes));
        task = activityListPanel.returnsUpdatedTask(task.getActivityName().fullName);
        TaskCardHandle editedCard = activityListPanel.navigateToTask(task);
        assertTaskMatching(task, editedCard);
        
        // Confirms the result message is correct
        assertResultMessage(String.format(EditCommand.MESSAGE_EDITTED_ACTIVITY_SUCCESS, task));
    }
    
    private void assertEventEditNoteSuccess(TestActivity event, int index, String changes) {
        commandBox.runCommand(event.getEditNoteCommand(index, changes));
        event = activityListPanel.returnsUpdatedEvent(event.getActivityName().fullName);
        EventCardHandle editedCard = activityListPanel.navigateToEvent(event);
        assertEventMatching(event, editedCard);
        
        // Confirms the result message is correct
        assertResultMessage(String.format(EditCommand.MESSAGE_EDITTED_ACTIVITY_SUCCESS, event));
    }
    
    private void assertTaskEditDateTimeSuccess(TestActivity task, int index, String changes) {
        commandBox.runCommand(task.getEditTaskDateTimeCommand(index, changes));
        task = activityListPanel.returnsUpdatedTask(task.getActivityName().fullName); // Update Task with new changes
        TaskCardHandle editedCard = activityListPanel.navigateToTask(task); // Check against card.
        assertTaskMatching(task, editedCard);
        
        // Confirms the result message is correct
        assertResultMessage(String.format(EditCommand.MESSAGE_EDITTED_ACTIVITY_SUCCESS, task));
    }
    
    private void assertTaskEditBothDateTimeSuccess(TestActivity task, int index, String changes) {
        commandBox.runCommand(task.getEditTaskDateTimeCommand(index, changes));
        task = activityListPanel.returnsUpdatedTask(task.getActivityName().fullName); // Update Task with new changes
        TaskCardHandle editedCard = activityListPanel.navigateToTask(task); // Check against card.
        assertTaskMatching(task, editedCard);
        
        // Confirms the result message is correct
        assertResultMessage(String.format(EditCommand.MESSAGE_EDITTED_ACTIVITY_SUCCESS, task));
    }
    
    private void assertEventEditFromDateTimeSuccess(TestActivity event, int index, String changes) {
        commandBox.runCommand(event.getEditEventFromDateTimeCommand(index, changes));
        event = activityListPanel.returnsUpdatedEvent(event.getActivityName().fullName);
        EventCardHandle editedCard = activityListPanel.navigateToEvent(event);
        assertEventMatching(event, editedCard);
        
        // Confirms the result message is correct
        assertResultMessage(String.format(EditCommand.MESSAGE_EDITTED_ACTIVITY_SUCCESS, event));
    }
    
    private void assertEventEditToDateTimeSuccess(TestActivity event, int index, String changes) {
        commandBox.runCommand(event.getEditEventToDateTimeCommand(index, changes));
        event = activityListPanel.returnsUpdatedEvent(event.getActivityName().fullName);
        EventCardHandle editedCard = activityListPanel.navigateToEvent(event);
        assertEventMatching(event, editedCard);
        
        // Confirms the result message is correct
        assertResultMessage(String.format(EditCommand.MESSAGE_EDITTED_ACTIVITY_SUCCESS, event));
        
    }
}
