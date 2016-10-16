package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.logic.commands.AddCommand;
import seedu.taskitty.logic.commands.UndoCommand;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestUtil;

public class UndoCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] list1 = td.getTypicalTasks();
        TestTask taskToAdd = td.todo;
        TestTask[] list2 = addTask(taskToAdd, list1);
        taskToAdd = td.deadline;
        TestTask[] list3 = addTask(taskToAdd, list2);
        taskToAdd = td.event;
        TestTask[] list4 = addTask(taskToAdd, list3);
        assertUndoSuccess(list3);
        assertUndoSuccess(list2);
        assertUndoSuccess(list1);
        assertNoMoreUndos(list1);
    }

    private TestTask[] addTask(TestTask taskToAdd, TestTask[] list3) {
        TestTask[] list4 = TestUtil.addPersonsToList(list3, taskToAdd);
        commandBox.runCommand(taskToAdd.getAddCommand());
        return list4;
    }
    
    private void assertUndoSuccess(TestTask... expectedList) {
        commandBox.runCommand("undo");
        
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        assertResultMessage(UndoCommand.MESSAGE_UNDO_SUCCESS);
    }
    
    private void assertNoMoreUndos(TestTask... expectedList) {
        commandBox.runCommand("undo");
        
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        assertResultMessage(UndoCommand.MESSAGE_NO_PREVIOUS_COMMANDS);
    }
}
