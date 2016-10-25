package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.testutil.TestTask;
import seedu.taskscheduler.testutil.TestUtil;
import seedu.taskscheduler.ui.TaskCard;

//@@author A0148145E

public class EditCommandTest extends TaskSchedulerGuiTest {

    @Test
    public void edit() {
        //edit with an event task
        TestTask[] currentList = td.getTypicalTasks();
        int indexToEdit = 1;
        TestTask taskToCopy = td.event;
        assertEditSuccess(indexToEdit, taskToCopy, currentList);
        currentList[indexToEdit - 1] = taskToCopy;
        currentList = TestUtil.addTasksToList(currentList);
        
        //edit with a deadline task
        indexToEdit = 2;
        taskToCopy = td.deadline;
        assertEditSuccess(indexToEdit, taskToCopy, currentList);
        currentList[indexToEdit - 1] = taskToCopy;
        currentList = TestUtil.addTasksToList(currentList);

        //edit with a floating task
        indexToEdit = 3;
        taskToCopy = td.floating;
        assertEditSuccess(indexToEdit, taskToCopy, currentList);
        currentList[indexToEdit - 1] = taskToCopy;
        currentList = TestUtil.addTasksToList(currentList);
        
        //edit with overdue task
        taskToCopy = td.overdue;
        indexToEdit = 4;
        assertEditSuccess(indexToEdit, taskToCopy, currentList);        
        //assert that overdue task is red
        assertTrue(taskListPanel.navigateToTask(indexToEdit - 1).getHBoxStyle().equals(TaskCard.OVERDUE_INDICATION));
        assertFalse(taskListPanel.navigateToTask(indexToEdit - 1).getHBoxStyle().equals(TaskCard.COMPLETED_INDICATION));

        currentList[indexToEdit - 1] = taskToCopy;
        currentList = TestUtil.addTasksToList(currentList);

        //edit with a duplicate task
        indexToEdit = 5;
        commandBox.runCommand("edit " + indexToEdit + " " + td.event.getTaskString());
        assertResultMessage(Command.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //edit in empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("edit " + indexToEdit + " " + td.event.getTaskString());
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        //invalid command
        commandBox.runCommand("edit eee " + td.ida.getTaskString());
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertEditSuccess(int indexToEdit, TestTask taskToCopy, TestTask... currentList) {
        commandBox.runCommand("edit " + indexToEdit + " " + taskToCopy.getTaskString());

        //confirm the edited card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(indexToEdit - 1);
        
        assertMatching(taskToCopy, editedCard);

        //confirm the list now contains all previous tasks with the edited task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList);
        expectedList[indexToEdit - 1] = taskToCopy;
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
