package guitests;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.testutil.TestItem;
import static org.junit.Assert.assertFalse;

public class NotDoneCommandTest extends TaskManagerGuiTest {

    @Test
    public void notDone() {
        // not done task 1
        TestItem[] currentList = td.getTypicalItems();
        int targetIndex = 1;
        assertNotDoneSuccess(targetIndex, currentList);

        // not done task 2
        targetIndex = 2;
        assertNotDoneSuccess(targetIndex, currentList);

        // not done task 1
        targetIndex = 1;
        assertNotDoneSuccess(targetIndex, currentList);
        
        // not done task 10
        targetIndex = 10;
        assertNotDoneSuccess(targetIndex, currentList);
    }
    
    @Test
    public void doneFailure() {
        //add one person
        TestItem[] currentList = td.getTypicalItems();
        int targetIndex = 11;
        commandBox.runCommand("notdone " + targetIndex);
        assertResultMessage(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    private void assertNotDoneSuccess(int targetIndex, TestItem... currentList) {
    	commandBox.runCommand("done " + targetIndex); // make it done first
    	commandBox.runCommand("notdone " + targetIndex);
        commandBox.runCommand("select " + targetIndex);
        TestItem doneTask = currentList[targetIndex-1];
        ReadOnlyItem addedCard = itemListPanel.getPerson(0);
        System.out.println(addedCard.getDone());
        assertFalse(addedCard.getDone());
    }    
    
}
