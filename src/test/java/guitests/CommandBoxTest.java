package guitests;

import org.junit.Test;

import harmony.mastermind.testutil.TypicalTestTasks;

import static org.junit.Assert.assertEquals;

public class CommandBoxTest extends TaskManagerGuiTest {
    private final String EMPTY_INPUT = "";

    //@@author A0124797R
    @Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(TypicalTestTasks.task4.getAddCommand());
        assertCommandBox(EMPTY_INPUT);
    }
    
    @Test
    public void commandBox_commandFail_textNotCleared() {
        String invalidCommand = "delete assignment";
        commandBox.runCommand(invalidCommand);
        assertCommandBox(invalidCommand);
    }

    @Test
    public void getPreviousCommand_noPreviousInput_noChangeInCommandBox() {
        commandBox.getPreviousCommand();
        assertCommandBox(EMPTY_INPUT);
    }
    
    @Test
    public void getPreviousCommand_withOneInput_previousInputLoaded() {
        commandBox.runCommand(TypicalTestTasks.task5.getAddCommand());
        assertCommandBox(EMPTY_INPUT);
        
        commandBox.getPreviousCommand();
        assertCommandBox(TypicalTestTasks.task5.getAddCommand());
        
        commandBox.getPreviousCommand();
        assertCommandBox(TypicalTestTasks.task5.getAddCommand());
    }
    
    @Test
    public void previousCommand_withMultipleInput_commandBoxChangeAccordingly() {
        commandBox.runCommand(TypicalTestTasks.task5.getAddCommand());
        commandBox.runCommand(TypicalTestTasks.task6.getAddCommand());
        commandBox.runCommand("undo");
        
        assertCommandBox(EMPTY_INPUT);
        
        commandBox.getPreviousCommand();
        assertCommandBox("undo");
        
        commandBox.getPreviousCommand();
        assertCommandBox(TypicalTestTasks.task6.getAddCommand());
        
        commandBox.getPreviousCommand();
        assertCommandBox(TypicalTestTasks.task5.getAddCommand());
        
        // get back first input
        commandBox.getNextCommand();
        commandBox.getNextCommand();
        commandBox.getNextCommand();
        assertCommandBox(EMPTY_INPUT);
    }

}
