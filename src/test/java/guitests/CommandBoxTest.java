package guitests;

import org.junit.Test;

import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.testutil.TypicalTestTasks;

import static org.junit.Assert.assertEquals;

//@@author A0148031R
public class CommandBoxTest extends ToDoListGuiTest {

    @Test
    public void commandBox_CommandSucceeds_TextCleared() throws IllegalValueException {
        commandBox.runCommand(TypicalTestTasks.BENSON.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
    }

    @Test
    public void commandBox_CommandFails_TextStays(){
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
        //TODO: confirm the text box color turns to red
    }
    
    @Test
    public void commandBox_CommandHistory_Empty() {
        // No previous command
        commandBox.scrollToPreviousCommand();
        assertEquals(commandBox.getCommandInput(), "");
        
        // No next command
        commandBox.scrollToNextCommand();
        assertEquals(commandBox.getCommandInput(), "");
    }
    
    @Test
    public void commandBox_CommandHistory_Exists() {
		String addCommand = "add commandhistorytestevent";
        commandBox.runCommand(addCommand);
        commandBox.runCommand("undo");
        
        // Get previous undo command
        commandBox.scrollToPreviousCommand();
        assertEquals(commandBox.getCommandInput(), "undo");
        
        // Get previous add command
        commandBox.scrollToPreviousCommand();
        assertEquals(commandBox.getCommandInput(), addCommand);
        
        // Get next undo command
        commandBox.scrollToNextCommand();
        assertEquals(commandBox.getCommandInput(), "undo");
        
        // No next command
        commandBox.scrollToNextCommand();
        assertEquals(commandBox.getCommandInput(), "");
    }

}
