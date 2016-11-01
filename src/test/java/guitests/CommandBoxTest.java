package guitests;

import seedu.address.testutil.TypicalTestTasks;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommandBoxTest extends AddressBookGuiTest {

    @Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(TypicalTestTasks.two.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
    }

    //@Test
    public void commandBox_commandFails_textStays(){
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
        //TODO: confirm the text box color turns to red
    }

}
