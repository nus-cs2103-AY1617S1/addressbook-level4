//@@author A0144919W
package guitests;

import static seedu.tasklist.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.commands.AddCommand;
import seedu.tasklist.logic.commands.ClearCommand;
import seedu.tasklist.logic.commands.DoneCommand;
import seedu.tasklist.logic.commands.SetStorageCommand;
import seedu.tasklist.logic.commands.UndoCommand;
import seedu.tasklist.logic.commands.UpdateCommand;
import seedu.tasklist.model.task.EndTime;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.StartTime;
import seedu.tasklist.model.task.TaskDetails;
import seedu.tasklist.testutil.TestTask;
import seedu.tasklist.testutil.TypicalTestTasks;

public class UndoCommandTest extends TaskListGuiTest {

    @Test
    public void undoOneChange() throws IllegalValueException {
        //undo a change that was never made
        commandBox.runCommand("update 20 Buy eggs");
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_FAILURE);
        //undo one change
        commandBox.runCommand("update 1 Attend yoga session from 2pm to 4pm p/high");
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
    
    @Test
    public void undoTwoChanges() throws IllegalValueException {
        //undo a change that was never made
        commandBox.runCommand("update 20 Buy eggs");
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_FAILURE);
        //undo two changes
        commandBox.runCommand("update 1 Attend yoga session from 2pm to 4pm p/high");
        commandBox.runCommand("delete 2");
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_FAILURE);
    }
    
    @Test
    public void undoThreeChanges() throws IllegalValueException {
        //undo three changes
        commandBox.runCommand("update 1 Attend yoga session from 2pm to 4pm p/high");
        commandBox.runCommand("delete 2");
        commandBox.runCommand("delete 2");
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_FAILURE);
    }
  //@@author A0135769N
    @Test
    public void undoAddTest() throws IllegalValueException {
        commandBox.runCommand("add Attend Takewando session from 9pm to 10pm p/high");
        TestTask task = new TestTask();
        task.setTaskDetails(new TaskDetails("Attend Takewando session"));
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS + ". " + AddCommand.MESSAGE_OVERLAP, task.getTaskDetails()));
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
    
    @Test
    public void undoDeleteTest() {
        commandBox.runCommand("delete 2");
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, TypicalTestTasks.task2.getTaskDetails()));
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
    
    @Test
    public void undoUpdateTest() throws IllegalValueException {
        commandBox.runCommand("update 7 from 10pm p/low");
        assertResultMessage(String.format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS,TypicalTestTasks.task7.getTaskDetails()));
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
    
    @Test
    public void undoDoneTest() {
        commandBox.runCommand("done 7");
        assertResultMessage(String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, TypicalTestTasks.task7.getTaskDetails()));
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
    
    @Test
    public void undoClearTest() {
        commandBox.runCommand("clear");
        assertResultMessage(ClearCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
    
    @Test
    public void undoSetstorageTest() {
        String filepath1 = "docs";
        String filepath2 = "config";
        commandBox.runCommand("setstorage " + filepath1);
    	assertResultMessage(String.format(SetStorageCommand.MESSAGE_SUCCESS + filepath1));
    	commandBox.runCommand("setstorage " + filepath2);
    	assertResultMessage(String.format(SetStorageCommand.MESSAGE_SUCCESS + filepath2));
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("setstorage default");
    	assertResultMessage(String.format(SetStorageCommand.MESSAGE_SUCCESS + "default"));
    }
}