//@@author A0146130W

package guitests;

import org.junit.Test;

import seedu.gtd.testutil.TestTask;
import seedu.gtd.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.gtd.logic.commands.UndoCommand.MESSAGE_SUCCESS;;

public class UndoCommandTest extends AddressBookGuiTest {

    @Test
    public void undo() {

        //undo the addition of the first task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask[] previousList = currentList;
        commandBox.runCommand(td.george.getAddCommand());
        assertUndoSuccess(previousList);

        //undo editing the dueDate of the last task in the list
        int targetIndex = currentList.length;
        String change = "d/2";
        previousList = currentList;
        commandBox.runCommand("edit " + targetIndex + " " + change);
        assertUndoSuccess(previousList);
        
        //undo deleting a task from the middle of the list
        targetIndex = currentList.length/2;
        previousList = currentList;
        commandBox.runCommand("delete " + targetIndex);
        assertUndoSuccess(previousList);
        
        //undo clearing list
        previousList = currentList;
        commandBox.runCommand("clear");
        assertUndoSuccess(previousList);
        
        //undo marking the middle task as done
        previousList = currentList;
        commandBox.runCommand("done " + targetIndex);
        assertUndoSuccess(previousList);
    }

    /**
     * Runs the undo command to undo the last change to the task list
     */
    private void assertUndoSuccess(final TestTask[] previousList) {
        commandBox.runCommand("undo");

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(previousList));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_SUCCESS));
    }

}
