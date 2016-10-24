package guitests;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.FloatingTaskCardHandle;
import guitests.guihandles.TaskCardHandle;
import seedu.address.testutil.TestActivity;

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
        
        // Edit Note - For all activity.
        
        // Edit Date - For Task
        
        // Edit Time - For Task
        
        // Edit Both, Date/Time - For Task
        
        // Edit From Date - For Event
        
        // Edit From Time - For Event
        
        // Edit To Date - For Event
        
        // Edit to Time - For Event. 
        
        // Edit invalid to/From date& Time. 
        // From date/time cannot be after than to date/time. - For Event
        
        // Edit with invalid params for date/time
        // Edit with invalid params for note 
        // Edit with invalid params for name
        
    }
    
    private void assertFloatingEditNameSuccess(TestActivity floating, int index, String changes) {
        commandBox.runCommand(floating.getEditNameCommand(index, changes));
        floating = activityListPanel.returnsUpdatedFloatingTask(changes); // Update floatingTask with new changes
        FloatingTaskCardHandle editedCard = activityListPanel.navigateToFloatingTask(floating); // Check against card.
        assertFloatingTaskMatching(floating, editedCard);
    }
    
    private void assertTaskEditNameSuccess(TestActivity task, int index, String changes) {
        commandBox.runCommand(task.getEditNameCommand(index, changes));
        task = activityListPanel.returnsUpdatedTask(changes); // Update Task with new changes
        TaskCardHandle editedCard = activityListPanel.navigateToTask(task); // Check against card.
        assertTaskMatching(task, editedCard);
    }
    
    private void assertEventEditNameSuccess(TestActivity event, int index, String changes) {
        commandBox.runCommand(event.getEditNameCommand(index, changes));
        event = activityListPanel.returnsUpdatedEvent(changes);
        EventCardHandle editedCard = activityListPanel.navigateToEvent(event);
        assertEventMatching(event, editedCard);
    }
}
