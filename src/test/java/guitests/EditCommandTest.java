package guitests;

import static org.junit.Assert.assertTrue;

import java.awt.List;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Reminder;
import seedu.address.testutil.*;

//@@author A0125097A
public class EditCommandTest extends AddressBookGuiTest {

    @Test
    public void edit_activityParameters(){
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 1;
        assertEditActivityResult(index,currentList);
    }
    
    @Test
    public void edit_taskParameters(){
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 3;
        assertEditTaskResult(index,currentList);
    }
    


	@Test
    public void edit_eventParameters(){
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 5;
        assertEditEventResult(index,currentList);        
    }
    


	private void assertEditActivityResult(int index,TestActivity... currentList){
    	assertEditResult(index,"activity",currentList);
    }
    
	private void assertEditTaskResult(int index, TestActivity[] currentList) {
		assertEditResult(index,"task",currentList);	
	}
    
    private void assertEditEventResult(int index, TestActivity[] currentList) {
    	assertEditResult(index,"event",currentList);
	}
    
    	private void assertEditResult(int index,String type, TestActivity... currentList){
    	String newName = "Editted Name";
    	String newReminder = "2-02-2020 1212";
    	String newDuedate = "10-10-2020 1010";
    	String newPriority = "3";
    	String newStartTime = "2-02-2020 1212";
    	String newEndTime = "10-10-2020 1010";
        String editCommand = "Edit " + index;
    	
    	TestActivity activityToEdit = new TestActivity(currentList[index-1]);
    	TestActivity activityAfterEdit = null;

        editCommand += " n/" + newName ;
        switch (type) {
        case "task":
        {
        	try {
				activityAfterEdit = new TestTask(currentList[index-1]);
			} catch (IllegalValueException e) {
				e.printStackTrace();
			}
        ((TestTask) activityAfterEdit).setDuedate(newDuedate);
        ((TestTask) activityAfterEdit).setPriority(newPriority);
        editCommand += " d/" + newDuedate  + " p/" + newPriority;
        }
        break;
        
        case "event":
        {
        	try {
				activityAfterEdit = new TestEvent(currentList[index-1]);
			} catch (IllegalValueException e) {
				e.printStackTrace();
			}
        ((TestEvent) activityAfterEdit).setStartTime(newStartTime);
        ((TestEvent) activityAfterEdit).setEndTime(newEndTime);
        editCommand += " s/" + newStartTime  + " e/" + newEndTime;
        } 
        break;
        
        case "activity":
        	activityAfterEdit = new TestActivity(currentList[index-1]);
        }
        
        activityAfterEdit.setName(newName);
        activityAfterEdit.setReminder(newReminder);
        editCommand += " r/" + newReminder;
        commandBox.runCommand("edit " + index + " n/" + newName + " r/" + newReminder);
        
        assertResultMessage(String.format("Edited Task from: %1$s\nto: %2$s",
                activityToEdit.getAsText(),activityAfterEdit.getAsText()));
        
        assertTrue(activityListPanel.isListMatching(currentList));
 
    }
        
}
