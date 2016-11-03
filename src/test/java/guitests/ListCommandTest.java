package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.TestActivity;

//@@author A0125097A
public class ListCommandTest extends AddressBookGuiTest  {

    @Test
    public void list_fromEmptyPanel() {
        TestActivity[] currentList = td.getTypicalActivities();
        commandBox.runCommand("find nothing");
        
        commandBox.runCommand("list");
        assertTrue(activityListPanel.isListMatching(currentList));  
    }
    
    @Test
    public void listTask_fromAllPanelsShown() {
        TestActivity[] currentList = td.getTypicalActivities();
        TestActivity[] expectedList = td.getTasksOnly();
        
        commandBox.runCommand("list task");  
        assertTrue(activityListPanel.isListMatching(expectedList));  
    }
    
    @Test
    public void listActivities_fromAllPanelsShown() {
        TestActivity[] currentList = td.getTypicalActivities();
        TestActivity[] expectedList = td.getActivitiesOnly();
        
        commandBox.runCommand("list activity");  
        assertTrue(activityListPanel.isListMatching(expectedList));  
    }
    
    @Test
    public void listEvents_fromAllPanelsShown() {
        TestActivity[] currentList = td.getTypicalActivities();
        TestActivity[] expectedList = td.getEventsOnly();
        
        commandBox.runCommand("list event");  
        assertTrue(activityListPanel.isListMatching(expectedList));  
    }
    
    
}
