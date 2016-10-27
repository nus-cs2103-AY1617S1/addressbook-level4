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

public class ReplaceCommandTest extends TaskSchedulerGuiTest {

    @Test
    public void replace() {
        
        TestTask[] currentList = td.getTypicalTasks();
        int indexToReplace; 
        TestTask taskToCopy;
        
        //invalid command
        commandBox.runCommand("replace eee " + td.ida.getTaskString());
        assertResultMessage(Messages.MESSAGE_PREV_TASK_NOT_FOUND);
        
        //replace with a deadline task
        indexToReplace = 2;
        taskToCopy = td.deadline;
        taskToCopy.setEndDate(currentList[indexToReplace - 1].getEndDate());
        assertReplaceSuccess(indexToReplace, taskToCopy, currentList);
        currentList[indexToReplace - 1] = taskToCopy;
        currentList = TestUtil.addTasksToList(currentList);

        //replace with a floating task
        indexToReplace = currentList.length;
        taskToCopy = td.floating;
        assertReplaceSuccess(indexToReplace, taskToCopy, currentList);
        currentList[indexToReplace - 1] = taskToCopy;
        currentList = TestUtil.addTasksToList(currentList);
        
        //replace with overdue task
        taskToCopy = td.overdue;
        indexToReplace = 1;
        assertReplaceSuccess(indexToReplace, taskToCopy, currentList);        
        //assert that overdue task is red
        assertTrue(taskListPanel.navigateToTask(indexToReplace - 1).getHBoxStyle().equals(TaskCard.OVERDUE_INDICATION));
        assertFalse(taskListPanel.navigateToTask(indexToReplace - 1).getHBoxStyle().equals(TaskCard.COMPLETED_INDICATION));

        currentList[indexToReplace - 1] = taskToCopy;
        currentList = TestUtil.addTasksToList(currentList);

        //replace with an event task
        indexToReplace = 1;
        taskToCopy = td.event;
        taskToCopy.setStartDate(currentList[indexToReplace - 1].getStartDate());
        assertReplaceSuccess(indexToReplace, taskToCopy, currentList);
        currentList[indexToReplace - 1] = taskToCopy;
        currentList = TestUtil.addTasksToList(currentList);
        
        //replace with a duplicate task
        indexToReplace = 5;
        commandBox.runCommand("replace " + indexToReplace + " " + td.event.getTaskString());
        assertResultMessage(Command.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //replace in empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("replace " + indexToReplace + " " + td.event.getTaskString());
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

    }

    private void assertReplaceSuccess(int indexToReplace, TestTask taskToCopy, TestTask... currentList) {
        
        commandBox.runCommand("replace " + indexToReplace + " " + taskToCopy.getTaskString());

        //confirm the replaced card contains the right data
        TaskCardHandle replacedCard = taskListPanel.navigateToTask(indexToReplace - 1);
        
        assertMatching(taskToCopy, replacedCard);

        //confirm the list now contains all previous tasks with the replaced task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList);
        expectedList[indexToReplace - 1] = taskToCopy;
        
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
