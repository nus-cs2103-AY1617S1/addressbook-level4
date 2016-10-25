package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.TaskCardHandle;
import seedu.task.logic.commands.AddTaskCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.EditEventCommand;
import seedu.task.logic.commands.EditTaskCommand;
import seedu.task.testutil.TestEvent;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.taskcommons.core.Messages;

public class EditCommandTest extends TaskBookGuiTest{

    @Test
    public void editTask() {
        //edit one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToEdit = td.arts;
        currentList = TestUtil.editTasksToList(currentList, 0 , taskToEdit);
        assertEditTaskSuccess(taskToEdit, 1, currentList);
        
        //edit another task
        taskToEdit = td.socSciences;
        currentList = TestUtil.editTasksToList(currentList, 3, taskToEdit);
        assertEditTaskSuccess(taskToEdit, 4 ,currentList);

        //edit to a duplicate task
        commandBox.runCommand(td.arts.getEditFloatTaskCommand(3));
        assertResultMessage(EditTaskCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //invalid command
        commandBox.runCommand("edits 1");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //invalid command format
        commandBox.runCommand("edit 1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }
    
    @Test
    public void editEvent() {
        //edit one event
        TestEvent[] currentList = te.getTypicalNotCompletedEvents();
        TestEvent taskToEdit = te.addedEvent;
        currentList = TestUtil.editEventsToList(currentList, 0 , taskToEdit);
        assertEditEventSuccess(taskToEdit, 1, currentList);
        
        //edit another event
        taskToEdit = te.addedEvent2;
        currentList = TestUtil.editEventsToList(currentList, 1, taskToEdit);
        assertEditEventSuccess(taskToEdit, 2 ,currentList);

        //edit to a duplicate event
        commandBox.runCommand(te.addedEvent.getEditCommand(2));
        assertResultMessage(EditEventCommand.MESSAGE_DUPLICATE_EVENT);
        assertTrue(eventListPanel.isListMatching(currentList));

    }
    
    private void assertEditTaskSuccess(TestTask taskToEdit, int index, TestTask... currentList) {
        commandBox.runCommand(taskToEdit.getEditFloatTaskCommand(index));

        //confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(taskToEdit.getTask().fullName);
        assertMatching(taskToEdit, editedCard);

        //confirm the list now contains all previous tasks plus the new edited task
        TestTask[] expectedList = TestUtil.addTasksToListAtIndex(currentList, index -1);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    private void assertEditEventSuccess(TestEvent eventToEdit, int index, TestEvent... currentList) {
        commandBox.runCommand(eventToEdit.getEditCommand(index));

        //confirm the new card contains the right data
        EventCardHandle editedCard = eventListPanel.navigateToEvent(eventToEdit.getEvent().fullName);
        assertMatching(eventToEdit, editedCard);

        //confirm the list now contains all previous events plus the new edited event
        TestEvent[] expectedList = TestUtil.addEventsToListAtIndex(currentList, index -1);
        assertTrue(eventListPanel.isListMatching(expectedList));
    }
}
