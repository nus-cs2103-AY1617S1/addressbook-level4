//@@author A0144919W
package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.commands.AddCommand;
import seedu.tasklist.logic.commands.ClearCommand;
import seedu.tasklist.logic.commands.DoneCommand;
import seedu.tasklist.logic.commands.RedoCommand;
import seedu.tasklist.logic.commands.SetStorageCommand;
import seedu.tasklist.logic.commands.UndoCommand;
import seedu.tasklist.logic.commands.UpdateCommand;
import seedu.tasklist.model.task.EndTime;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.StartTime;
import seedu.tasklist.model.task.TaskDetails;
import seedu.tasklist.testutil.TestTask;
import seedu.tasklist.testutil.TestUtil;
import seedu.tasklist.testutil.TypicalTestTasks;

public class RedoCommandTest extends TaskListGuiTest {
    
    @Test
    public void redoOneChange() throws IllegalValueException {
       //attempt redo when no undo action has been made
       commandBox.runCommand("redo");
       assertResultMessage(RedoCommand.MESSAGE_FAILURE);
       //redo one change
       commandBox.runCommand("update 1 Attend yoga session from 2pm to 4pm p/high");
       commandBox.runCommand("undo");
       commandBox.runCommand("redo");
       assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }
    
    @Test
    public void redoTwoChanges() throws IllegalValueException {
        //undo two changes
        commandBox.runCommand("update 1 Attend yoga session from 2pm to 4pm p/high");
        commandBox.runCommand("delete 2");
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_FAILURE);
    }
    
    @Test
    public void redoThreeChanges() throws IllegalValueException {
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
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_FAILURE);
    }
    //@@author A0135769N
    @Test
    public void redoAddTest() throws IllegalValueException {
     	commandBox.runCommand("add visit beach front from 9pm to 10pm p/high");
        TestTask task = new TestTask();
        task.setTaskDetails(new TaskDetails("visit beach front"));
        task.setStartTime(new StartTime("9pm"));
        task.setEndTime(new EndTime("10pm"));
        task.setPriority(new Priority("high"));
        TestTask[] currentList = td.getTypicalTasks();
        currentList = TestUtil.addTasksToList(currentList, task);
        int initial_length = currentList.length;
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("redo");
        int final_length = currentList.length;
        assertEquals("No net addition of tasks", initial_length,final_length);
    }
    
    @Test
    public void redoDeleteTest() {
    	commandBox.runCommand("delete 2");
    	TestTask[] currentList = td.getTypicalTasks();
    	int targetIndex = (int)(Math.random()*currentList.length + 1);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex); 
    	int initial_length = currentList.length;
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("redo");
        int final_length = currentList.length;
        assertEquals("No net addition of tasks", initial_length,final_length);
    }
    
    @Test
    public void redoUpdateTest() {
    	 commandBox.runCommand("update 7 from 10pm p/low");
         assertResultMessage(String.format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS,TypicalTestTasks.task7.getTaskDetails()));
         commandBox.runCommand("undo");
         assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
         commandBox.runCommand("redo");
         assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }
    
    @Test
    public void redoDoneTest() {
    	commandBox.runCommand("done 7");
        assertResultMessage(String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, TypicalTestTasks.task7.getTaskDetails()));
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }
    
    @Test
    public void redoClearTest() {
    	commandBox.runCommand("clear");
        assertResultMessage(ClearCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }
    
    @Test
    public void redoSetstorageTest() {
        String filepath1 = "docs/tasklist.xml";
        String filepath2 = "config/checkstyle.xml";
        commandBox.runCommand("setstorage " + filepath1);
    	assertResultMessage(String.format(SetStorageCommand.MESSAGE_SUCCESS + filepath1));
    	commandBox.runCommand("setstorage " + filepath2);
    	assertResultMessage(String.format(SetStorageCommand.MESSAGE_SUCCESS + filepath2));
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("setstorage default");
    	assertResultMessage(String.format(SetStorageCommand.MESSAGE_SUCCESS + "default"));
    }
    
}