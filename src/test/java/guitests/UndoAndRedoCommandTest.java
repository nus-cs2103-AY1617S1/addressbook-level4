package guitests;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import seedu.taskitty.logic.commands.RedoCommand;
import seedu.taskitty.logic.commands.UndoCommand;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestTaskList;

//@@ author A0139052L
public class UndoAndRedoCommandTest extends TaskManagerGuiTest {
    
    private Stack<TestTaskList> undoTestTaskListStack;
    private Stack<TestTaskList> redoTestTaskListStack;
    private Stack<String> undoCommandTextStack;
    private Stack<String> redoCommandTextStack;
    private TestTaskList currentList;
    
    @Test
    public void undoAndRedo() {
        
        // use a stack to hold all previous TestTaskList and command texts
        undoTestTaskListStack = new Stack<TestTaskList>();
        redoTestTaskListStack = new Stack<TestTaskList>();
        undoCommandTextStack = new Stack<String>();
        redoCommandTextStack = new Stack<String>();
               
        // store the current list into undo stack before running the command
        currentList = new TestTaskList(td.getTypicalTasks());
        undoTestTaskListStack.push(currentList.copy());        
        currentList.addTaskToList(td.todo);
        String commandText = td.todo.getAddCommand();
        commandBox.runCommand(commandText);
        undoCommandTextStack.push(commandText);
        
        undoTestTaskListStack.push(currentList.copy());                        
        int targetIndex = currentList.size() / 2;
        commandText = "delete " + targetIndex;
        commandBox.runCommand(commandText);
        currentList.removeTaskFromList(targetIndex - 1);
        undoCommandTextStack.push(commandText);
       
        undoTestTaskListStack.push(currentList.copy());                        
        commandText = "clear";
        commandBox.runCommand(commandText);
        undoCommandTextStack.push(commandText);
        currentList = new TestTaskList(new TestTask[] {});
        
        //test undo brings back the previous list and store the list before undoing and the command text to the redo stack
        runUndo(false);
        
        // test if using accelerator for undo works
        runUndo(true);
        
        //test if redo brings back the undone list
        runRedo(false);
               
        undoTestTaskListStack.push(currentList.copy());        
        targetIndex = currentList.size('d');
        commandText = td.event.getEditCommand(targetIndex, 'd');        
        commandBox.runCommand(commandText);
        currentList.editTaskFromList(targetIndex - 1, 'd', td.event);
        undoCommandTextStack.push(commandText);        
        
        // clear redo stack when new undoable command has been given and check that there is no redo available
        redoCommandTextStack.clear();
        redoTestTaskListStack.clear();
        assertNoMoreRedos();
        
        undoTestTaskListStack.push(currentList.copy());
        targetIndex = currentList.size('t');
        commandText = "done t" + targetIndex;
        commandBox.runCommand(commandText);
        TestTask taskToMark = currentList.getTaskFromList(targetIndex - 1, 't');
        currentList.markTaskAsDoneInList(targetIndex - 1, 't', taskToMark);
        undoCommandTextStack.push(commandText);
        
        // run undo for all the commands until we get back original list and check no more undos after that
        runUndo(false);
        runUndo(true);
        runUndo(false);
        runUndo(true);
        assertNoMoreUndos();
        
        // run redo for all undos done until we get back the latest list and check no more redos after that
        runRedo(true);
        runRedo(false);
        runRedo(true);
        commandBox.runCommand("view all");
        runRedo(false);
        assertNoMoreRedos();
                        
    }
        
    @Test
    public void invalidCommand_noUndos() {
        commandBox.runCommand("adds party");
        assertNoMoreUndos(); 
    }
    
    /**
     * Runs the redo function and asserts whether the proper task list is restored
     * and stores the relevant data into the undo stack
     * @param useAccelerator whether to use the accelerator to test or not
     */
    private void runRedo(boolean useAccelerator) {
        TestTaskList undoneList = redoTestTaskListStack.pop();
        String undoneCommandText = redoCommandTextStack.pop();
        
        assertRedoSuccess(undoneList, undoneCommandText, useAccelerator); 
        
        undoTestTaskListStack.push(currentList.copy());
        undoCommandTextStack.push(undoneCommandText);
        currentList = undoneList;
    }

    /**
     * Runs the undo function and asserts whether the proper task list is restored
     * and stores the relevant data into the redo stack
     * @param useAccelerator whether to use the accelerator(shortcut key) to test or not
     */
    private void runUndo(boolean useAccelerator) {
        String previousCommandText = undoCommandTextStack.pop();
        TestTaskList previousList = undoTestTaskListStack.pop();
        
        assertUndoSuccess(previousList, previousCommandText, useAccelerator);
        
        redoTestTaskListStack.push(currentList.copy());
        redoCommandTextStack.push(previousCommandText);
        currentList = previousList;
    }
    
    /**
     * Runs the undo command and asserts whether the undo function is successful by checking whether the 
     * current list matches the expected list and the correct result message is displayed
     * @param expectedList the list expected after undoing obtained from the undo stack
     * @param commandText the commandText of the command obtained from the undo stack
     * @param useAccelerator whether to use the accelerator(shortcut key) to test or not
     */
    private void assertUndoSuccess(TestTaskList expectedList, String commandText, boolean useAccelerator) {
        if (useAccelerator) {
            mainMenu.useUndoCommandUsingAccelerator();
        } else {
            commandBox.runCommand("undo");
        }
        assertTrue(expectedList.isListMatching(taskListPanel));
        
        assertResultMessage(String.format(UndoCommand.MESSAGE_UNDO_SUCCESS, commandText.trim()));
    }   
    
    /**
     * Runs the redo command and asserts whether the redo function is successful by checking whether the 
     * current list matches the expected list and the correct result message is displayed
     * @param expectedList the list expected after undoing obtained from the redo stack
     * @param commandText the commandText of the command obtained from the redo stack
     * @param useAccelerator whether to use the accelerator(shortcut key) to test or not
     */
    private void assertRedoSuccess(TestTaskList expectedList, String commandText, boolean useAccelerator) {
        if (useAccelerator) {
            mainMenu.useRedoCommandUsingAccelerator();
        } else {
            commandBox.runCommand("redo");
        }
        assertTrue(expectedList.isListMatching(taskListPanel));
        
        assertResultMessage(String.format(RedoCommand.MESSAGE_REDO_SUCCESS, commandText.trim()));
    }
    
    /**
     * Runs the undo function and assert that there are no more undoes left and
     * the error message is displayed
     */
    private void assertNoMoreUndos() {
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_PREVIOUS_VALID_COMMANDS);
    }
    
    /**
     * Runs the redo function and assert that there are no more redos left and
     * the error message is displayed
     */
    private void assertNoMoreRedos() {
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_NO_RECENT_UNDO_COMMANDS);
    }
}
