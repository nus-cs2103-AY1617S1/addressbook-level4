package guitests;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import seedu.taskitty.logic.commands.UndoCommand;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestTaskList;

//@@ author A0139052L
public class UndoCommandTest extends TaskManagerGuiTest {
    
    Stack<TestTaskList> testTaskListStack;
    
    @Test
    public void undo() {
        
        // use a stack to hold all previous TestTaskList
        testTaskListStack = new Stack<TestTaskList>();
        testTaskListStack.push(new TestTaskList(td.getTypicalTasks()));
        
        // test undoing for all commands that mutates the list and saves its state
        TestTask taskToAdd = td.todo;
        testTaskListStack.push(addTask(taskToAdd, testTaskListStack.peek()));
        
        int targetIndex = testTaskListStack.peek().size() / 2;
        testTaskListStack.push(deleteTask(testTaskListStack.peek().size() / 2, testTaskListStack.peek()));
        
        commandBox.runCommand("clear");
        assertUndoSuccess(testTaskListStack.pop(), "clear");
        
        // test if using accelerator for undo works
        TestTaskList previous = testTaskListStack.pop();
        assertUndoUsingAcceleratorSuccess(previous, "delete " + targetIndex);
        testTaskListStack.push(previous);
        
        TestTask taskToEdit = td.event;
        testTaskListStack.push(editTask(1, taskToEdit, 't', testTaskListStack.peek()));
        commandBox.runCommand("find xmas");        
                           
        assertUndoSuccess(testTaskListStack.pop(), "find xmas");
        assertUndoUsingAcceleratorSuccess(testTaskListStack.pop(), taskToEdit.getEditCommand(1, 't'));
        assertUndoSuccess(testTaskListStack.pop(), taskToAdd.getAddCommand());
        assertNoMoreUndos();        
    }
    
    @Test
    public void invalidCommand_noUndos() {
        commandBox.runCommand("adds party");
        assertNoMoreUndos(); 
    }
    
    private TestTaskList addTask(TestTask taskToAdd, TestTaskList list) {
        TestTaskList resultList = list.copy();
        commandBox.runCommand(taskToAdd.getAddCommand());
        resultList.addTaskToList(taskToAdd);
        return resultList;
    }
    
    private TestTaskList editTask(int index, TestTask taskToEdit, char category, TestTaskList list) {
        TestTaskList resultList = list.copy();        
        commandBox.runCommand(taskToEdit.getEditCommand(index, category));
        resultList.editTaskFromList(index - 1, category, taskToEdit);
        return resultList;
    }
    
    private TestTaskList deleteTask(int targetIndex, TestTaskList list) {
        TestTaskList resultList = list.copy();
        commandBox.runCommand("delete " + targetIndex);
        resultList.removeTaskFromList(targetIndex - 1);
        return resultList;
    }
    
    private void assertUndoSuccess(TestTaskList expectedList, String commandText) {
        commandBox.runCommand("undo");

        assertTrue(expectedList.isListMatching(taskListPanel));
        
        assertResultMessage(UndoCommand.MESSAGE_UNDO_SUCCESS + commandText);
    }
    
    private void assertUndoUsingAcceleratorSuccess(TestTaskList expectedList, String commandText) {
        mainMenu.useUndoCommandUsingAccelerator();

        assertTrue(expectedList.isListMatching(taskListPanel));
        
        assertResultMessage(UndoCommand.MESSAGE_UNDO_SUCCESS + commandText);
    }
    
    private void assertNoMoreUndos() {
        commandBox.runCommand("undo");        
        assertResultMessage(UndoCommand.MESSAGE_NO_PREVIOUS_VALID_COMMANDS);
    }
}
