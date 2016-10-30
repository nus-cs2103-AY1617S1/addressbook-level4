package guitests;

import org.junit.Test;

import seedu.todo.logic.commands.UndoCommand;
import seedu.todo.testutil.TestTask;
import seedu.todo.testutil.TestUtil;


import static org.junit.Assert.assertTrue;

//@@author A0093896H
public class UndoCommandTest extends ToDoListGuiTest {
    
    @Test
    public void undo_command_test() {
        
        //initially no undo
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_PREVIOUS_STATE);
        
        TestTask[] currentList = td.getTypicalTasks();
        for (TestTask t : currentList) {
            commandBox.runCommand(t.getAddCommand());
        }
        
        TestTask[] reverseList = td.getTypicalTasksReverse();
        
        //undo add
        reverseList = TestUtil.removePersonFromList(reverseList, 1);
        commandBox.runCommand("undo");
        assertUndoSuccess(reverseList);
        
        //undo update
        commandBox.runCommand("update 1 changeName");
        commandBox.runCommand("undo");
        assertUndoSuccess(reverseList);
        
        //undo delete
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo");
        assertUndoSuccess(reverseList);
}
    
    
    /**
     * Runs the undo command to move to the previous state and confirms the result is correct.
     * @param currentList A copy of the expected list of tasks (after undo).
     */
    private void assertUndoSuccess(TestTask... expectedList) {
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    
}
