package guitests;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.testutil.TestItem;
import static org.junit.Assert.assertTrue;
//@@author A0065571A
public class DoneCommandTest extends TaskManagerGuiTest {

    @Test
    public void done() {
        // done task 1
        TestItem[] currentList = td.getTypicalItems();
        int targetIndex = 1;
        assertDoneSuccess(targetIndex);

        // done task 2
        targetIndex = 2;
        assertDoneSuccess(targetIndex);

        // done task 1
        targetIndex = 1;
        assertDoneSuccess(targetIndex);
        
        // done task 10
        targetIndex = 10;
        assertDoneSuccess(targetIndex);
    }
    
    @Test
    public void doneFailure() {
        // done task 11
        int targetIndex = 11;
        commandBox.runCommand("done " + targetIndex);
        assertResultMessage(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    private void assertDoneSuccess(int targetIndex) {
        commandBox.runCommand("done " + targetIndex);
        commandBox.runCommand("select " + targetIndex);
        ReadOnlyItem doneItem = itemListPanel.getPerson(0);
        assertTrue(doneItem.getDone());
    }    
    
}
