package guitests;

import guitests.guihandles.HelpPanelHandle;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

// @@author A0124333U
/**
 * GUI test for help command
 */
public class HelpPanelTest extends TarsGuiTest {

    @Test
    public void openHelpPanel() {
        assertHelpPanelSelected(commandBox.runHelpCommand());
    }

    private void assertHelpPanelSelected(HelpPanelHandle helpPanelHandle) {
        assertTrue(helpPanelHandle.isTabSelected());
    }
}
