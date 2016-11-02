package guitests;

import static org.junit.Assert.assertTrue;

import java.awt.List;

import org.junit.Test;

import seedu.address.model.activity.Reminder;
import seedu.address.testutil.TestActivity;

public class EditCommandTest extends AddressBookGuiTest {

    @Test
    public void edit_activityParameters(){
        TestActivity[] currentList = td.getTypicalPersons();
        int index = 1;
        assertEditReminderResult(index,currentList);
        
        
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
}
