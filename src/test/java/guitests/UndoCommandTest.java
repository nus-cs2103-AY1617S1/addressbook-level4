package guitests;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import seedu.taskitty.logic.commands.UndoCommand;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestTaskList;
import seedu.taskitty.testutil.TestUtil;

public class UndoCommandTest extends TaskManagerGuiTest {
    /*
    Stack<TestTaskList> testTaskList;
    
    @Test
    public void undo() {
        testTaskList = new Stack<TestTaskList>();
        testTaskList.push(new TestTaskList(td.getTypicalTasks()));
        TestTask taskToAdd = td.todo;
        testTaskList.push(addTask(taskToAdd, testTaskList.peek()));
        //testTaskList.push(deleteTask(testTaskList.get(1).length / 2, testTaskList.peek()));
        commandBox.runCommand("clear");
        assertUndoSuccess(testTaskList.pop());
        //assertUndoSuccess(testTaskList.pop());
        //testTaskList.push(TestUtil.addPersonsToList(testTaskList.peek(), taskToAdd));
        //commandBox.runCommand("find xmas");
        //TestTask[] expectedList = { td.shop, td.dinner };
        //testTaskList.push(new TestTaskList(expectedList));
        //TestTask taskToEdit = td.event;
        //editTask(1, taskToEdit, testTaskList.peek());                   
        //assertUndoSuccess(testTaskList.pop());
        //assertUndoSuccess(testTaskList.pop());
        //assertUndoSuccess(testTaskList.pop());
        //assertNoMoreUndos();        
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
    
    private TestTaskList addTask(TestTask taskToAdd, TestTaskList list) {
        TestTaskList resultList = list.copy();
        commandBox.runCommand(taskToAdd.getAddCommand());
        resultList.addTaskToList(taskToAdd);
        return resultList;
    }
    
    private TestTask[] editTask(int index, TestTask taskToEdit, TestTask[] list) {
        TestTask[] resultList = TestUtil.replacePersonFromList(list, taskToEdit, index - 1);
        commandBox.runCommand(taskToEdit.getEditCommand(index));
        return resultList;
    }
    
    private TestTaskList deleteTask(int targetIndex, TestTaskList list) {
        TestTaskList resultList = list.copy();
        //TODO remove
        commandBox.runCommand("delete " + targetIndex);
        return resultList;
    }
    
    private void assertUndoSuccess(TestTaskList expectedList) {
        commandBox.runCommand("undo");

        assertTrue(expectedList.isListMatching(taskListPanel));
        
        assertResultMessage(UndoCommand.MESSAGE_UNDO_SUCCESS);
    }
    
    private void assertNoMoreUndos() {
        commandBox.runCommand("undo");        
        assertResultMessage(UndoCommand.MESSAGE_NO_PREVIOUS_COMMANDS);
    }*/
}
