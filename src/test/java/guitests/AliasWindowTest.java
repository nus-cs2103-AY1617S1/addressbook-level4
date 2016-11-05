package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import guitests.guihandles.AliasWindowHandle;

//@@author A0138978E
public class AliasWindowTest extends TaskManagerGuiTest {

    @Test
    public void openAliasWindow() {
        commandBox.runCommand("list alias");
        assertAliasWindowOpen(mainGui.getAliasWindow());
    }

    private void assertAliasWindowOpen(AliasWindowHandle aliasWindowHandle) {
        assertTrue(aliasWindowHandle.isWindowOpen());
        aliasWindowHandle.closeWindow();
    }
}
