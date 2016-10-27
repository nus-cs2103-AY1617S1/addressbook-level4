package guitests;

import org.junit.Test;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class UndoRedoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undoRedo() {
        // Action: mark as done
        TestTask[] secondState = td.getTypicalTasks();
        secondState[1].markAsDone();
        commandBox.runCommand("done 2");
        
        secondState[0].markAsDone();
        commandBox.runCommand("done 1");
        
        // Action: delete task <- later change to edit
        TestTask[] thirdState = TestUtil.removeTaskFromList(secondState, 2);
        commandBox.runCommand("delete 2");
         
        // Action: add task
        TestTask taskToAdd = td.project;
        TestTask[] fourthState = TestUtil.addTasksToList(thirdState, taskToAdd);
        commandBox.runCommand(taskToAdd.getAddCommand());;
        
        // Action: delete task
        TestTask[] fifthState = TestUtil.removeTaskFromList(fourthState, 3);
        commandBox.runCommand("delete 3");
        
        // Action: Clear task manager
        commandBox.runCommand("clear");
        
        // Undo up to 5 times
        assertUndoRedoSuccess("undo", 4, new TestTask[][] {fifthState, fourthState, thirdState, secondState});
        assertUndoDoneSuccess(0);
        assertUndoFailed();
        
        assertRedoDoneSuccess(0);
        assertUndoRedoSuccess("redo", 3, new TestTask[][] {thirdState, fourthState, fifthState});
        assertRedoClearSuccess();
        assertRedoFailed();
    }
    
    private void assertUndoRedoSuccess(String command, int n, TestTask[]... states) {
        for (int i = 0; i < n; i++) {
            commandBox.runCommand(command);
            assertTrue(taskListPanel.isListMatching(states[i]));
        }
    }
    
    private void assertUndoDoneSuccess(int n) {
        commandBox.runCommand("undo");
        assertTrue(!taskListPanel.getTask(n).isDone());
    }
    
    private void assertRedoDoneSuccess(int n) {
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.getTask(n).isDone());
    }
    
    
    private void assertRedoClearSuccess() {
        commandBox.runCommand("redo");
        assertListSize(0);
    }
    
    private void assertUndoFailed() {
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_UNDO_FAILED);
    }
    
    private void assertRedoFailed() {
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_REDO_FAILED);
    }
    
}