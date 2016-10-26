//@@author A0144919W
package guitests;

import org.junit.Test;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.commands.RedoCommand;
import seedu.tasklist.logic.commands.UndoCommand;
import seedu.tasklist.model.task.EndTime;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.StartTime;
import seedu.tasklist.model.task.TaskDetails;
import seedu.tasklist.testutil.TypicalTestTasks;

public class RedoCommandTest extends TaskListGuiTest {
    
    @Test
    public void redoOneChange() throws IllegalValueException {
       //attempt redo when no undo action has been made
       commandBox.runCommand("redo");
       assertResultMessage(RedoCommand.MESSAGE_FAILURE);
       //redo one change
       commandBox.runCommand("update 1 Attend yoga session from 2pm to 4pm p/high");
       TypicalTestTasks.task1.setTaskDetails(new TaskDetails("Attend yoga session"));
       TypicalTestTasks.task1.setStartTime(new StartTime("2pm"));
       TypicalTestTasks.task1.setEndTime(new EndTime("4pm"));
       TypicalTestTasks.task1.setPriority(new Priority("high")); 
       commandBox.runCommand("undo");
       TypicalTestTasks.task1.setTaskDetails(new TaskDetails("Buy eggs"));
       TypicalTestTasks.task1.setStartTime(new StartTime("5pm"));
       TypicalTestTasks.task1.setEndTime(new EndTime(""));
       TypicalTestTasks.task1.setPriority(new Priority("high"));
       commandBox.runCommand("redo");
       TypicalTestTasks.task1.setTaskDetails(new TaskDetails("Attend yoga session"));
       TypicalTestTasks.task1.setStartTime(new StartTime("2pm"));
       TypicalTestTasks.task1.setEndTime(new EndTime("4pm"));
       TypicalTestTasks.task1.setPriority(new Priority("high"));
       assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }
    
    @Test
    public void redoTwoChanges() throws IllegalValueException {
        //undo two changes
        commandBox.runCommand("update 1 Attend yoga session from 2pm to 4pm p/high");
        TypicalTestTasks.task1.setTaskDetails(new TaskDetails("Attend yoga session"));
        TypicalTestTasks.task1.setStartTime(new StartTime("2pm"));
        TypicalTestTasks.task1.setEndTime(new EndTime("4pm"));
        TypicalTestTasks.task1.setPriority(new Priority("high")); 
        commandBox.runCommand("delete 2");
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
        TypicalTestTasks.task1.setTaskDetails(new TaskDetails("Buy eggs"));
        TypicalTestTasks.task1.setStartTime(new StartTime("5pm"));
        TypicalTestTasks.task1.setEndTime(new EndTime(""));
        TypicalTestTasks.task1.setPriority(new Priority("high"));
        commandBox.runCommand("redo");
        TypicalTestTasks.task1.setTaskDetails(new TaskDetails("Attend yoga session"));
        TypicalTestTasks.task1.setStartTime(new StartTime("2pm"));
        TypicalTestTasks.task1.setEndTime(new EndTime("4pm"));
        TypicalTestTasks.task1.setPriority(new Priority("high")); 
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }
    
    @Test
    public void redoThreeChanges() throws IllegalValueException {
        //undo three changes
        commandBox.runCommand("update 1 Attend yoga session from 2pm to 4pm p/high");
        TypicalTestTasks.task1.setTaskDetails(new TaskDetails("Attend yoga session"));
        TypicalTestTasks.task1.setStartTime(new StartTime("2pm"));
        TypicalTestTasks.task1.setEndTime(new EndTime("4pm"));
        TypicalTestTasks.task1.setPriority(new Priority("high")); 
        commandBox.runCommand("delete 2");
        commandBox.runCommand("delete 2");
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("undo");
        TypicalTestTasks.task1.setTaskDetails(new TaskDetails("Buy eggs"));
        TypicalTestTasks.task1.setStartTime(new StartTime("5pm"));
        TypicalTestTasks.task1.setEndTime(new EndTime(""));
        TypicalTestTasks.task1.setPriority(new Priority("high"));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("redo");
        TypicalTestTasks.task1.setTaskDetails(new TaskDetails("Attend yoga session"));
        TypicalTestTasks.task1.setStartTime(new StartTime("2pm"));
        TypicalTestTasks.task1.setEndTime(new EndTime("4pm"));
        TypicalTestTasks.task1.setPriority(new Priority("high"));
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_FAILURE);
    }
    
    @Test
    public void redoAddTest() {
      //TODO
    }
    
    @Test
    public void redoDeleteTest() {
      //TODO
    }
    
    @Test
    public void redoUpdateTest() {
      //TODO
    }
    
    @Test
    public void redoDoneTest() {
      //TODO   
    }
    
    @Test
    public void redoClearTest() {
      //TODO
    }
    
    @Test
    public void redoSetstorageTest() {
      //TODO
    }
    
}