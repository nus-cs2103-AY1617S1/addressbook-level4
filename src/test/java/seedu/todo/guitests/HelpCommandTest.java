package seedu.todo.guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.todo.controllers.CommandDefinition;
import seedu.todo.controllers.HelpController;
import seedu.todo.guitests.guihandles.HelpItemHandle;

public class HelpCommandTest extends GuiTest {

    @Test
    public void help_show_success() {
        console.runCommand("help");
        assertTrue(helpView.hasLoaded());
        
        // Make sure all command items have loaded
        for (CommandDefinition commandDefinition : new HelpController().getAllCommandDefinitions()) {
            HelpItemHandle helpItem = helpView.getHelpItem(commandDefinition);
            assertNotNull(helpItem);
        }
    }

}
