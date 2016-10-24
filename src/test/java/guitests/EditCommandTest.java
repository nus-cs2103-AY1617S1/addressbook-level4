package guitests;

import org.junit.Test;

import seedu.address.testutil.TestActivity;

//@@author: A0139164A

public class EditCommandTest extends AddressBookGuiTest {
    
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
    
    private void assertNameEditSuccess(TestActivity toEdit, int index, String changes) {
        
    }
}
