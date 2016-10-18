package guitests;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.logic.commands.MarkCommand;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.Task;
import seedu.doerList.testutil.TestTask;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MarkCommandTest extends DoerListGuiTest {

    @Test
    public void mark() {
        //marks one task from top
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertMarkSuccess(targetIndex);
        
        //marks one task from middle
        targetIndex = currentList.length;
        assertMarkSuccess(targetIndex / 2);
        
        //marks one task from end
        targetIndex = currentList.length - 1;
        assertMarkSuccess(targetIndex);
        
        //marks invalid task
        commandBox.runCommand("mark " + currentList.length + 1);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
    }
    
    private void assertMarkSuccess(int indexToBeMarked) {
        commandBox.runCommand("mark " + indexToBeMarked);
        
        Task taskToMark = (Task) taskListPanel.getTask(indexToBeMarked - 1);
        
        assertTrue(taskToMark.getBuildInCategories().contains(BuildInCategoryList.COMPLETE));
        
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }
    
    
}
