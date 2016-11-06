package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.agendum.commons.core.Messages;
import seedu.agendum.testutil.TestTask;

//@@author A0148031R
public class MarkCommandTest extends ToDoListGuiTest{

    @Test
    public void mark_nonEmptytask_succeed() {
        TestTask[] currentList = td.getTypicalTasks();
        currentList[0].markAsCompleted();
        TestTask taskToMark = currentList[0];
        assertMarkSuccess("mark 1", taskToMark, currentList);
    }
    
    @Test
    public void mark_nonEmptytask_duplicates() {
        assertMarkDuplicates("mark 7");
    }
    
    @Test
    public void mark_emptytask() {
        assetMarkEmptyTask("mark 8");
    }
    
    private void assertMarkSuccess(String command, TestTask taskToMark, TestTask... currentList) {
        commandBox.runCommand(command);
        
        //confirm the new card contains the right data
        if (taskToMark.isCompleted()) {
            TaskCardHandle addedCard = completedTasksPanel.navigateToTask(taskToMark.getName().fullName);
            assertMatching(taskToMark, addedCard);
        } else if (!taskToMark.isCompleted() && !taskToMark.hasTime()) {
            TaskCardHandle addedCard = floatingTasksPanel.navigateToTask(taskToMark.getName().fullName);
            assertMatching(taskToMark, addedCard);
        } else if (!taskToMark.isCompleted() && taskToMark.hasTime()) {
            TaskCardHandle addedCard = upcomingTasksPanel.navigateToTask(taskToMark.getName().fullName);
            assertMatching(taskToMark, addedCard);
        }
        
        //confirm the list now contains all previous tasks plus the new task
        taskToMark.setLastUpdatedTimeToNow();
        TestTask[] expectedList = currentList;
        assertAllPanelsMatch(expectedList);
        assertResultMessage(Messages.MESSAGE_MARK_TASK_SUCCESS);
    }
    
    private void assertMarkDuplicates(String command) {
        commandBox.runCommand(command);
        assertResultMessage(Messages.MESSAGE_DUPLICATE);
    }
    
    private void assetMarkEmptyTask(String command) {
        commandBox.runCommand(command);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
}
