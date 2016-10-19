package guitests;

import guitests.guihandles.HelpWindowHandle;
import seedu.taskitty.model.task.Task;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HelpWindowTest extends TaskManagerGuiTest {

    @Test
    public void openHelpWindow() {

        //click anywhere on the app
        taskListPanel.clickOnListView(Task.TASK_COMPONENT_COUNT);

        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        assertHelpWindowOpen(mainMenu.openHelpWindowUsingMenu());

        assertHelpWindowOpen(commandBox.runHelpCommand());

    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }
}
