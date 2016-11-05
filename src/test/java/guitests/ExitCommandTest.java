package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ExitCommandTest extends TaskManagerGuiTest {

    @Test
    public void exit() {
        commandBox.runCommand("exit");
        assertTrue(!mainGui.isMainWindowOpen());

    }
}
