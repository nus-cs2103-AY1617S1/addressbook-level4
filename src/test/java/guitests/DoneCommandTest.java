package guitests;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.testutil.TestItem;
import static org.junit.Assert.assertTrue;

public class DoneCommandTest extends TaskManagerGuiTest {

    @Test
    public void done() {
        //add one person
        TestItem[] currentList = td.getTypicalItems();
        int targetIndex = 1;
        assertDoneSuccess(targetIndex, currentList);

        //add another person
        targetIndex = 2;
        assertDoneSuccess(targetIndex, currentList);

        //add another person
        targetIndex = 1;
        assertDoneSuccess(targetIndex, currentList);
    }
    
    @Test
    public void doneFailure() {
        //add one person
        TestItem[] currentList = td.getTypicalItems();
        int targetIndex = 11;
        commandBox.runCommand("done " + targetIndex);
        assertResultMessage(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    private void assertDoneSuccess(int targetIndex, TestItem... currentList) {
        commandBox.runCommand("done " + targetIndex);
        commandBox.runCommand("select " + targetIndex);
        TestItem doneTask = currentList[targetIndex-1];
        ReadOnlyItem addedCard = itemListPanel.getPerson(0);
        System.out.println(addedCard.getDone());
        assertTrue(addedCard.getDone());
    }    
    
}
