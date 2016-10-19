package guitests;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import seedu.taskitty.logic.commands.UndoCommand;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestUtil;

public class UndoCommandTest extends TaskManagerGuiTest {
    /*
    Stack<TestTask[]> testTaskList;
    
    @Test
    public void undo() {
        testTaskList = new Stack<TestTask[]>();
        testTaskList.push(td.getTypicalTasks());
        TestTask taskToAdd = td.todo;
        testTaskList.push(addTask(taskToAdd, testTaskList.peek()));
        testTaskList.push(deleteTask(testTaskList.get(1).length / 2, testTaskList.peek()));
        commandBox.runCommand("clear");
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        testTaskList.push(TestUtil.addPersonsToList(testTaskList.peek(), taskToAdd));
        commandBox.runCommand("find xmas");
        testTaskList.push(new TestTask[]{td.shop, td.dinner});
        TestTask taskToEdit = td.event;
        editTask(1, taskToEdit, testTaskList.peek());                   
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertNoMoreUndos();        
    }
    
    @Test
    public void invalidCommand_noUndos() {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("adds party");
        assertNoMoreUndos(); 
        commandBox.runCommand("delete " + currentList.length + 1);
        assertNoMoreUndos(); 
        commandBox.runCommand("findevent");
        assertNoMoreUndos(); 
    }
    
    private TestTask[] addTask(TestTask taskToAdd, TestTask[] list) {
        TestTask[] resultList = TestUtil.addPersonsToList(list, taskToAdd);
        commandBox.runCommand(taskToAdd.getAddCommand());
        return resultList;
    }
    
    private TestTask[] editTask(int index, TestTask taskToEdit, TestTask[] list) {
        TestTask[] resultList = TestUtil.replacePersonFromList(list, taskToEdit, index - 1);
        commandBox.runCommand(taskToEdit.getEditCommand(index));
        return resultList;
    }
    
    private TestTask[] deleteTask(int targetIndex, TestTask[] list) {
        TestTask[] resultList = TestUtil.removePersonFromList(list, targetIndex);
        commandBox.runCommand("delete " + targetIndex);
        return resultList;
    }
    
    private void assertUndoSuccess(TestTask... expectedList) {
        commandBox.runCommand("undo");

        assertTrue(taskListPanel.isListMatching(expectedList));
        
        assertResultMessage(UndoCommand.MESSAGE_UNDO_SUCCESS);
    }
    
    private void assertNoMoreUndos() {
        commandBox.runCommand("undo");        
        assertResultMessage(UndoCommand.MESSAGE_NO_PREVIOUS_COMMANDS);
    }*/
}
