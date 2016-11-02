//@@author A0146130W

package guitests;

import org.junit.Test;

import seedu.gtd.testutil.TestTask;

import static org.junit.Assert.assertTrue;
import static seedu.gtd.logic.commands.UndoCommand.MESSAGE_SUCCESS;
import static seedu.gtd.logic.commands.UndoCommand.MESSAGE_UNDO_LIMIT_REACHED;

import java.util.Stack;;

public class UndoCommandTest extends AddressBookGuiTest {

    @Test
    public void undo() {

        //undo the addition of the first task
        TestTask[] currentList = td.getTypicalTasks();
        Stack<TestTask[]> previousList = new Stack<TestTask[]>();
        previousList.push(currentList);
        commandBox.runCommand(td.george.getAddCommand());
        assertUndoSuccess(previousList.pop());

        //undo editing the dueDate of the last task in the list
        int targetIndex = currentList.length;
        String change = "d/2";
        previousList.push(currentList);
        commandBox.runCommand("edit " + targetIndex + " " + change);
        assertUndoSuccess(previousList.pop());
        
        //undo deleting a task from the middle of the list
        targetIndex = currentList.length/2;
        previousList.push(currentList);
        commandBox.runCommand("delete " + targetIndex);
        assertUndoSuccess(previousList.pop());
        
        //undo clearing list
        previousList.push(currentList);
        commandBox.runCommand("clear");
        assertUndoSuccess(previousList.pop());
        
        //undo marking the middle task as done
        previousList.push(currentList);
        commandBox.runCommand("done " + targetIndex);
        assertUndoSuccess(previousList.pop());
        
        /*
        //undo multiple times
        previousList.push(currentList);
        commandBox.runCommand("edit " + targetIndex + " " + change);
        previousList.push(currentList);
        commandBox.runCommand(td.george.getAddCommand());
        previousList.push(currentList);
        commandBox.runCommand("delete " + targetIndex);
        previousList.push(currentList);
        commandBox.runCommand("done " + targetIndex);
        previousList.push(currentList);
        commandBox.runCommand("clear");
        assertMultipleUndoSuccess(previousList);
        */
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
    
    private void assertMultipleUndoSuccess(final Stack<TestTask[]> previousList) {
    	
        //run undo multiple times and verify list each time
        for(int i=1; i < previousList.size(); i++) {
        	commandBox.runCommand("undo");
        	assertTrue(taskListPanel.isListMatching(previousList.pop()));
        	assertResultMessage(String.format(MESSAGE_SUCCESS));
        }
        
        //verify that the undo limit message is shown when undo limit is reached
        commandBox.runCommand("undo");
        assertResultMessage(String.format(MESSAGE_UNDO_LIMIT_REACHED));
    }

}
