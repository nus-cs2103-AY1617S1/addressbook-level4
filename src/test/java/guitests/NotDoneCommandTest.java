package guitests;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.testutil.TestItem;
import static org.junit.Assert.assertFalse;

//@@author A0065571A
public class NotDoneCommandTest extends TaskManagerGuiTest {

    @Test
    public void notDone() {
        // not done task 1
        int targetIndex = 1;
        assertNotDoneSuccess(targetIndex);

        // not done task 2
        targetIndex = 2;
        assertNotDoneSuccess(targetIndex);

        // not done task 1
        targetIndex = 1;
        assertNotDoneSuccess(targetIndex);
        
        // not done task 10
        targetIndex = 10;
        assertNotDoneSuccess(targetIndex);
    }
    
    @Test
    public void doneFailure() {
        // not done task 11
        int targetIndex = 11;
        commandBox.runCommand("notdone " + targetIndex);
        assertResultMessage(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    private void assertNotDoneSuccess(int targetIndex) {
    	commandBox.runCommand("done " + targetIndex); // make it done first
    	commandBox.runCommand("notdone " + targetIndex);
        commandBox.runCommand("select " + targetIndex);
        ReadOnlyItem doneItem = itemListPanel.getPerson(0);
        assertFalse(doneItem.getDone());
    }    
    
}
