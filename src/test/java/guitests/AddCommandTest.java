package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskList;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.model.task.Status;

import static org.junit.Assert.assertTrue;

//@@author A0138601M
public class AddCommandTest extends ToDoListGuiTest {

    @Test
    public void add() {
        
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        
        //add one task
        TestTask taskToAdd = td.event;
        assertAddSuccess(taskToAdd, currentList);

        //add another task
        taskToAdd = td.deadline;
        assertAddSuccess(taskToAdd, currentList);

        //add to empty list
        commandBox.runCommand("clear");
        currentList.clear();
        taskToAdd = td.eventWithLocationAndRemarks;
        assertAddSuccess(taskToAdd, currentList);

        //invalid command
        commandBox.runCommand("adds invalidcommand");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Runs the add command to add new tasks and confirms the result is correct.
     * @param taskToAdd a new task to be added
     * @param currentList A copy of the current list of tasks (before addition).
     */
    private void assertAddSuccess(TestTask taskToAdd, TestTaskList currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName, taskToAdd.getStatus().getStatus());
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        currentList.addTasksToList(taskToAdd);
        assertTrue(taskListPanel.isListMatching(taskToAdd.getStatus().getStatus(), currentList.getIncompleteList()));
        
        //confirm no new task is added to completed pane
        //TODO
    }

}
