package guitests;

import guitests.guihandles.HelpPanelHandle;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * GUI test for help command
 *
 * @@author A0124333U
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
