package guitests;

import java.util.List;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import w15c2.tusk.commons.core.Messages;
import w15c2.tusk.logic.commands.taskcommands.AddTaskCommand;
import w15c2.tusk.model.task.Task;
import w15c2.tusk.testutil.TaskTesterUtil;
import w15c2.tusk.testutil.TestUtil;

import static org.junit.Assert.*;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one task
        List<Task> currentTaskList = TestUtil.getInitialTasks().getInternalList();
        List<Task> tasksToAdd = TestUtil.getDefaultTasks().getInternalList();
        Task taskToAdd = tasksToAdd.get(0);
        
        assertAddSuccess(taskToAdd, currentTaskList);

        //add another task
        taskToAdd = tasksToAdd.get(1);
        assertAddSuccess(taskToAdd, currentTaskList);

        //add duplicate person
        //commandBox.runCommand(td.hoon.getAddCommand());
        //assertResultMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);
        //assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        taskToAdd = tasksToAdd.get(2);
        commandBox.runCommand("clear");
        currentTaskList.clear();
        assertAddSuccess(taskToAdd, currentTaskList);

        //invalid command (no description)
        commandBox.runCommand("add");
        assertResultMessage(AddTaskCommand.MESSAGE_EMPTY_TASK + AddTaskCommand.MESSAGE_USAGE);
    }

    private void assertAddSuccess(Task taskToAdd, List<Task> currentTaskList) {
        commandBox.runCommand(TaskTesterUtil.getAddCommandFromTask(taskToAdd));

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        currentTaskList.add(taskToAdd);
        assertTrue(taskListPanel.isListMatching(currentTaskList));
    }

}
