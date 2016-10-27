package guitests;

import guitests.guihandles.HelpPanelHandle;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HelpPanelTest extends TarsGuiTest {

    @Test
    public void openHelpPanel() {

        assertHelpPanelSelected(commandBox.runHelpCommand());

    }

    private void assertHelpPanelSelected(HelpPanelHandle helpPanelHandle) {
        assertTrue(helpPanelHandle.isTabSelected());
    }
}
