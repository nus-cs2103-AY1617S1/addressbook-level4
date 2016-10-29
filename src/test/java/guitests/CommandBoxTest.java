//@@author A0147994J
package guitests;

import org.junit.Test;
import seedu.ggist.commons.core.Messages;

import static org.junit.Assert.assertEquals;

public class CommandBoxTest extends TaskManagerGuiTest {

    @Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(td.floating.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
    }

    @Test
    public void commandBox_commandFails_textStays(){
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
        //TODO: confirm the text box color turns to red
    }

}
