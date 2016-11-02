package guitests;

import static org.junit.Assert.assertTrue;

import java.awt.List;

import org.junit.Test;

import seedu.address.model.activity.Reminder;
import seedu.address.testutil.*;

public class EditCommandTest extends AddressBookGuiTest {

    @Test
    public void edit_activityParameters(){
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 1;
        assertEditNameResult(index,currentList);
        assertEditReminderResult(index,currentList);

        
    }
    
    @Test
    public void edit_taskParameters(){
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 3;
        assertEditNameResult(index,currentList);
        assertEditReminderResult(index,currentList);
        assertEditDuedateResult(index,currentList);
        
        index = 2;
        assertEditPriorityResult(index,currentList);
        

        
    }
    
    private void assertEditReminderResult(int index,TestActivity... currentList){
        String newReminder = "12-12-2222 2222";
        TestActivity activityToEdit = new TestActivity(currentList[index-1]);
        commandBox.runCommand("edit " + index + " r/" + newReminder);
        
        TestActivity activityAfterEdit = currentList[index-1];
        activityAfterEdit.setReminder(newReminder);
        
        assertResultMessage(String.format("Edited Task from: %1$s\nto: %2$s",
                activityToEdit.getAsText(),activityAfterEdit.getAsText()));
        
        assertTrue(activityListPanel.isListMatching(currentList));
 
    }
    
    private void assertEditNameResult(int index,TestActivity... currentList){
        String newName = "Editted Name";
        TestActivity activityToEdit = new TestActivity(currentList[index-1]);
        commandBox.runCommand("edit " + index + " n/" + newName);
        
        TestActivity activityAfterEdit = currentList[index-1];
        activityAfterEdit.setName(newName);
        
        assertResultMessage(String.format("Edited Task from: %1$s\nto: %2$s",
                activityToEdit.getAsText(),activityAfterEdit.getAsText()));
        
        assertTrue(activityListPanel.isListMatching(currentList));
 
    }
    
    private void assertEditDuedateResult(int index, TestActivity... currentList) {
        String newDuedate = "22-12-2222 2222";
        TestActivity activityToEdit = new TestActivity(currentList[index-1]);
        commandBox.runCommand("edit " + index + " d/" + newDuedate);
        
        TestActivity activityAfterEdit = currentList[index-1];
        ((TestTask) activityAfterEdit).setDuedate(newDuedate);
        
        assertResultMessage(String.format("Edited Task from: %1$s\nto: %2$s",
                activityToEdit.getAsText(),activityAfterEdit.getAsText()));
        
        assertTrue(activityListPanel.isListMatching(currentList));
    }
    
    private void assertEditPriorityResult(int index, TestActivity[] currentList) {
        String newPriority = "3";
        TestActivity activityToEdit = new TestActivity(currentList[index-1]);
        commandBox.runCommand("edit " + index + " p/" + newPriority);
        
        TestActivity activityAfterEdit = currentList[index-1];
        ((TestTask) activityAfterEdit).setPriority(newPriority);
        
        assertResultMessage(String.format("Edited Task from: %1$s\nto: %2$s",
                activityToEdit.getAsText(),activityAfterEdit.getAsText()));
        
        assertTrue(activityListPanel.isListMatching(currentList));
    }
}
