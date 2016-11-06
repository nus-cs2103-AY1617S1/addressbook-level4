package guitests;

//@@author A0135793W
import static org.junit.Assert.*;
import static seedu.taskitty.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.logic.commands.Command;
import seedu.taskitty.logic.commands.EditCommand;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestTaskList;

public class EditCommandTest extends TaskManagerGuiTest {
    
    private static final String DONE_STATUS = "done";
    private static final String OVERDUE_STATUS = "overdue";
    private static final String DEFAULT_STATUS = "default";
    @Test
    public void edit() {
        
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        
        //Tests for edits that remove or add parameters - change between categories
        //edit from deadline to todo
        TestTask taskToEdit = td.todo;
        int targetIndex = currentList.size('d');
        assertEditSuccess(taskToEdit, targetIndex, 'd', currentList, DEFAULT_STATUS);

        //edit from event to deadline
        taskToEdit = td.deadline;
        targetIndex = currentList.size('e');
        assertEditSuccess(taskToEdit, targetIndex, 'e', currentList, DEFAULT_STATUS);
        
        //edit from todo to event
        taskToEdit = td.event;
        targetIndex = currentList.size('t');
        assertEditSuccess(taskToEdit, targetIndex, 't', currentList, DEFAULT_STATUS);
        
        //edit into duplicate task
        commandBox.runCommand(td.deadline.getEditCommand(targetIndex - 1, 'e'));
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(currentList.isListMatching(taskListPanel));
        
        //invalid command
        commandBox.runCommand("edits party");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        commandBox.runCommand("edit 1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Command.MESSAGE_FORMAT + EditCommand.MESSAGE_PARAMETER));
        
        commandBox.runCommand("edit d deadline 09/09/2016 5pm");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Command.MESSAGE_FORMAT + EditCommand.MESSAGE_PARAMETER));
        
        //invalid index
        targetIndex = currentList.size('t') + 1;
        commandBox.runCommand(td.todo.getEditCommand(targetIndex, 't'));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        assertTrue(currentList.isListMatching(taskListPanel));
    }
   
    //@@author A0130853L
    @Test
    public void edit_doneDeadlineTask() {
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        //edit a done deadline
        commandBox.runCommand("done d1");
        commandBox.runViewAllCommand();
        int targetIndex = currentList.size('d');
        TestTask taskToEdit = td.deadline;
        assertEditSuccess(taskToEdit, targetIndex, 'd', currentList, DONE_STATUS);
    }
    
    @Test
    public void edit_overdueDeadlineTask() {
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        //edit an overdue deadline
        TestTask taskToEdit = td.overdueDeadline;
        int targetIndex = currentList.size('d');
        assertEditSuccess(taskToEdit, targetIndex, 'd', currentList, OVERDUE_STATUS);
    }
    
    @Test
    public void edit_overEvent() {
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        //edit a event from future to past, which will show up as "done".
        TestTask taskToEdit = td.overEvent;
        commandBox.runViewAllCommand();
        int targetIndex = currentList.size('e');
        currentList.markTaskAsDoneInList(targetIndex-1, 'e', taskToEdit);
        assertEditSuccess(taskToEdit, targetIndex, 'e', currentList, DONE_STATUS);
    }
    
    /**
     *  the event being edited was already marked as done by user before it was over, so it retains its status of being done event if
     *  the newly edited date is in the future.
     */
    @Test
    public void edit_markedAsDoneEvent() {
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        //edit an event that was manually marked as done by user previously.
        TestTask taskToEdit = td.event;
        commandBox.runViewAllCommand();
        commandBox.runCommand("done e2");
        int targetIndex = currentList.size('e');
        currentList.markTaskAsDoneInList(targetIndex-1, 'e', taskToEdit);
        assertEditSuccess(taskToEdit, targetIndex, 'e', currentList, DONE_STATUS);
    }
    
    //@@author
    private void assertEditSuccess(TestTask taskToEdit, int index, char category, TestTaskList currentList, String taskStatus) {
        commandBox.runCommand(taskToEdit.getEditCommand(index, category));

        //confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(taskToEdit.getName().fullName, taskToEdit.getPeriod().getNumArgs());
        assertMatching(taskToEdit, editedCard);
       
        //confirm the list now contains all previous persons plus the new person
        currentList.editTaskFromList(index - 1, category, taskToEdit);
        assertTrue(currentList.isListMatching(taskListPanel));
        
        verifyTasksWithSpecifiedStatuses(taskStatus, editedCard);
    }
    
    //@@author A0130853L
    /**
     * This method verifies the cards with specified statuses either retain or update their statuses according to the
     * the edited date of the task.
     */
    private void verifyTasksWithSpecifiedStatuses(String taskStatus, TaskCardHandle editedCard) {
        if (taskStatus.equals(DONE_STATUS)) {
            assertMarkAsDone(editedCard);
        } else if (taskStatus.equals(OVERDUE_STATUS)) {
            assertMarkAsOverdue(editedCard);
        }
    }
}

