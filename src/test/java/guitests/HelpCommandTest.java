package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

//@@author A0139194X
public class HelpCommandTest extends TaskManagerGuiTest {

    @Test
    public void execute_sucess() {
        commandBox.runCommand("help");
        assertResultMessage("Command summary displayed.");
    }
}
