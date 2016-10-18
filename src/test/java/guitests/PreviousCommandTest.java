package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import harmony.mastermind.logic.commands.PreviousCommand;

public class PreviousCommandTest extends TaskManagerGuiTest {
    
    @Test
    //@@author A0124797R
    public void PreviousCommand() {
        commandBox.runCommand(td.task5.getAddCommand());
        commandBox.runCommand("p");
        
        //ensure the previous command is in the commandBox
        assertEquals(commandBox.getCommandInput(), td.task5.getAddCommand());

        //confirm the result message is correct
        assertResultMessage(PreviousCommand.MESSAGE_SUCCESS);
        
    }

}
