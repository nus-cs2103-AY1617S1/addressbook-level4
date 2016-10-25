package guitests;

import org.junit.Test;

import seedu.taskitty.testutil.TestTaskList;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends TaskManagerGuiTest {

    @Test
    public void clear() {

        //display all tasks
        commandBox.runViewAllCommand();
        
        //verify a non-empty list can be cleared
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        assertTrue(currentList.isListMatching(taskListPanel));
        assertClearCommandSuccess();
        currentList.clear();
        
        //verify clearCommand can be used with accelerator
        commandBox.runCommand("undo");
        assertClearCommandWithAcceleratorSuccess();
        
        //verify other commands can work after a clear command
        commandBox.runCommand(td.todo.getAddCommand());
        currentList.addTaskToList(td.todo);
        assertTrue(currentList.isListMatching(taskListPanel));
        commandBox.runCommand("delete 1");
        assertTodoListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
        currentList.clear();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertTodoListSize(0);
        assertDeadlineListSize(0);
        assertEventListSize(0);
        assertResultMessage("Task manager has been cleared!");
    }
    
    private void assertClearCommandWithAcceleratorSuccess() {
        mainMenu.useClearCommandUsingAccelerator();
        assertTodoListSize(0);
        assertDeadlineListSize(0);
        assertEventListSize(0);
        assertResultMessage("Task manager has been cleared!");
    }
}
