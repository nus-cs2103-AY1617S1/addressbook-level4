package guitests;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.logic.commands.AddCommand;
import seedu.taskitty.logic.commands.UndoCommand;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestUtil;

public class UndoCommandTest extends TaskManagerGuiTest {
    
    Stack<TestTask[]> testTaskList;
    
    @Test
    public void add() {
        testTaskList = new Stack<TestTask[]>();
        testTaskList.push(td.getTypicalTasks());
        TestTask taskToAdd = td.todo;
        testTaskList.push(addTask(taskToAdd, testTaskList.peek()));
        taskToAdd = td.deadline;
        testTaskList.push(addTask(taskToAdd, testTaskList.peek()));
        taskToAdd = td.event;
        addTask(taskToAdd, testTaskList.peek());
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertNoMoreUndos();
    }
    
    @Test
    public void clear() {
        TestTask[] list1 = td.getTypicalTasks();
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        commandBox.runCommand("clear");
        assertUndoSuccess(list1);
        assertNoMoreUndos();
    }
    
    @Test
    public void edit() {
        testTaskList = new Stack<TestTask[]>();
        testTaskList.push(td.getTypicalTasks());
        TestTask taskToEdit = td.todo;
        testTaskList.push(editTask(1, taskToEdit, testTaskList.peek()));
        taskToEdit = td.deadline;
        testTaskList.push(editTask(3, taskToEdit, testTaskList.peek()));
        taskToEdit = td.event;
        editTask(2, taskToEdit, testTaskList.peek());
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertNoMoreUndos();
    }
    
    @Test
    public void delete() {
        testTaskList = new Stack<TestTask[]>();
        testTaskList.push(td.getTypicalTasks());
        int targetIndex = 1;
        testTaskList.push(deleteTask(targetIndex, testTaskList.peek()));
        targetIndex = testTaskList.get(1).length;
        testTaskList.push(deleteTask(targetIndex, testTaskList.peek()));
        targetIndex = testTaskList.get(2).length/2;
        deleteTask(targetIndex, testTaskList.peek());
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertNoMoreUndos();       
    }
    
    @Test
    public void find() {
        commandBox.runCommand("find task");
        commandBox.runCommand("find xmas");
        commandBox.runCommand("find xmas");
        assertUndoSuccess(td.shop, td.dinner);
        assertUndoSuccess();
        assertUndoSuccess(td.getTypicalTasks());
        assertNoMoreUndos();
    }
    
    @Test
    public void multiple() {
        testTaskList = new Stack<TestTask[]>();
        testTaskList.push(td.getTypicalTasks());
        TestTask taskToAdd = td.todo;
        testTaskList.push(addTask(taskToAdd, testTaskList.peek()));
        taskToAdd = td.deadline;
        testTaskList.push(addTask(taskToAdd, testTaskList.peek()));
        testTaskList.push(deleteTask(testTaskList.get(1).length / 2, testTaskList.peek()));
        commandBox.runCommand("find xmas");
        testTaskList.push(new TestTask[]{td.shop, td.dinner});
        commandBox.runCommand("clear");
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertNoMoreUndos();
    }
    
    @Test
    public void inBetween() {
        testTaskList = new Stack<TestTask[]>();
        testTaskList.push(td.getTypicalTasks());
        TestTask taskToAdd = td.todo;
        testTaskList.push(addTask(taskToAdd, testTaskList.peek()));
        taskToAdd = td.deadline;
        testTaskList.push(addTask(taskToAdd, testTaskList.peek()));
        testTaskList.push(deleteTask(testTaskList.get(1).length / 2, testTaskList.peek()));
        testTaskList.push(deleteTask(1, testTaskList.peek()));
        commandBox.runCommand("clear");
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        testTaskList.push(addTask(taskToAdd, testTaskList.peek()));
        commandBox.runCommand("find xmas");
        testTaskList.push(new TestTask[]{td.shop, td.dinner});
        commandBox.runCommand("clear");
        testTaskList.push(new TestTask[]{});
        addTask(taskToAdd, testTaskList.peek());        
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
        assertUndoSuccess(testTaskList.pop());
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
    }
}
