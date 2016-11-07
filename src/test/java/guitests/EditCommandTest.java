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
    public void edit_changeList() {
        
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        
        //Tests for edits that remove or add parameters - change between categories
        //edit from deadline to todo
        TestTask taskToEdit = td.addTodo;
        int targetIndex = currentList.size('d');
        assertEditSuccess(taskToEdit, targetIndex, 'd', currentList, false, DEFAULT_STATUS);

        //edit from event to deadline
        taskToEdit = td.addDeadline;
        targetIndex = currentList.size('e');
        assertEditSuccess(taskToEdit, targetIndex, 'e', currentList, false, DEFAULT_STATUS);
        
        //edit from todo to event
        taskToEdit = td.addEvent;
        targetIndex = currentList.size('t');
        assertEditSuccess(taskToEdit, targetIndex, 't', currentList, false, DEFAULT_STATUS);   
    }
    
    @Test
    public void edit() {
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        
        //editing date of deadline using date only
        commandBox.runCommand("edit d1 " + td.editedDeadline.getDateString());
        TestTask taskToEdit = td.editedDeadline;
        assertEditSuccess(taskToEdit, 1, 'd', currentList, true, DEFAULT_STATUS);
        
        System.out.println("edit ed " + td.editedEvent.getDateString());
        commandBox.runCommand("edit e1 " + td.editedEvent.getDateString());
        taskToEdit = td.editedEvent;
        assertEditSuccess(taskToEdit, 1, 'e', currentList, true, DEFAULT_STATUS);
        
        //invalid edit from event to deadline using only one date; need to specify time
        commandBox.runCommand("edit e1 24/12/2016");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Command.MESSAGE_FORMAT + EditCommand.MESSAGE_PARAMETER));
        
        //invalid edit from deadline to event by only specifying dates
        commandBox.runCommand("edit d1 24/12/2016 to 25/12/2016");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Command.MESSAGE_FORMAT + EditCommand.MESSAGE_PARAMETER));
        
        //edit into duplicate task
        int targetIndex = currentList.size('e');
        commandBox.runCommand(td.editedDeadline.getEditCommand(targetIndex - 1, 'e'));
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
        commandBox.runCommand(td.addTodo.getEditCommand(targetIndex, 't'));
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
        TestTask taskToEdit = td.addDeadline;
        assertEditSuccess(taskToEdit, targetIndex, 'd', currentList, false, DONE_STATUS);
    }
    
    @Test
    public void edit_overdueDeadlineTask() {
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        //edit an overdue deadline
        TestTask taskToEdit = td.overDeadline;
        int targetIndex = currentList.size('d');
        assertEditSuccess(taskToEdit, targetIndex, 'd', currentList, false, OVERDUE_STATUS);
    }
    
    @Test
    public void edit_overEvent() {
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        //edit a event from future to past, which will show up as "done".
        TestTask taskToEdit = td.overEvent;
        commandBox.runViewAllCommand();
        int targetIndex = currentList.size('e');
        currentList.markTaskAsDoneInList(targetIndex-1, 'e', taskToEdit);
        assertEditSuccess(taskToEdit, targetIndex, 'e', currentList, false, DONE_STATUS);
    }
    
    /**
     *  the event being edited was already marked as done by user before it was over, so it retains its status of being done event if
     *  the newly edited date is in the future.
     */
    @Test
    public void edit_markedAsDoneEvent() {
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        //edit an event that was manually marked as done by user previously.
        TestTask taskToEdit = td.addEvent;
        commandBox.runViewAllCommand();
        commandBox.runCommand("done e2");
        int targetIndex = currentList.size('e');
        currentList.markTaskAsDoneInList(targetIndex-1, 'e', taskToEdit);
        assertEditSuccess(taskToEdit, targetIndex, 'e', currentList, false, DONE_STATUS);
    }
    
    //@@author
    
    //@@author A0135793W
    private void assertEditSuccess(TestTask taskToEdit, int index, char category, TestTaskList currentList, 
            boolean isSpecialCommand, String taskStatus) {
        
        if (!isSpecialCommand) {
            commandBox.runCommand(taskToEdit.getEditCommand(index, category));
        }

        commandBox.runViewAllCommand();
        //confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(taskToEdit.getName().fullName, 
                taskToEdit.getPeriod().getNumArgs());
        assertMatching(taskToEdit, editedCard);
       
        //confirm the list now contains all previous tasks plus the new edited task
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

