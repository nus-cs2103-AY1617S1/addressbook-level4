package guitests;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import seedu.taskitty.logic.commands.UndoCommand;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestTaskList;
import seedu.taskitty.testutil.TestUtil;

public class UndoCommandTest extends TaskManagerGuiTest {
    
    Stack<TestTaskList> testTaskList;
    
    @Test
    public void undo() {
        
        testTaskList = new Stack<TestTaskList>();
        testTaskList.push(new TestTaskList(td.getTypicalTasks()));
        
        TestTask taskToAdd = td.todo;
        testTaskList.push(addTask(taskToAdd, testTaskList.peek()));
//        testTaskList.push(deleteTask(testTaskList.size() / 2, testTaskList.peek()));
        commandBox.runCommand("clear");
        TestTaskList previous = testTaskList.peek();
        testTaskList.push(previous);
        assertUndoSuccess(testTaskList.pop(), "clear");
        
        
        commandBox.runCommand("find xmass");
//        TestTask taskToEdit = td.event;
//        editTask(1, taskToEdit, testTaskList.peek());                   
        assertUndoSuccess(testTaskList.pop(), "find xmass");
        assertUndoSuccess(testTaskList.pop(), taskToAdd.getAddCommand());
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
    
//    private TestTask[] editTask(int index, TestTask taskToEdit, TestTaskList list) {
//        TestTask[] resultList = TestUtil.replacePersonFromList(list, taskToEdit, index - 1);
//        commandBox.runCommand(taskToEdit.getEditCommand(index));
//        return resultList;
//    }
    
//    private TestTaskList deleteTask(int targetIndex, TestTaskList list) {
//        TestTaskList resultList = list.copy();
//        commandBox.runCommand("delete " + targetIndex);
//        resultList.removeTaskFromList(targetIndex);
//        return resultList;
//    }
    
    private void assertUndoSuccess(TestTaskList expectedList, String commandText) {
        commandBox.runCommand("undo");

        assertTrue(expectedList.isListMatching(taskListPanel));
        
        assertResultMessage(UndoCommand.MESSAGE_UNDO_SUCCESS + commandText);
    }
    
    private void assertNoMoreUndos() {
        commandBox.runCommand("undo");        
        assertResultMessage(UndoCommand.MESSAGE_NO_PREVIOUS_COMMANDS);
    }
}
