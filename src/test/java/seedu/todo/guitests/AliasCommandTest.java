package seedu.todo.guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.todo.guitests.guihandles.AliasItemHandle;
import seedu.todo.models.Task;

// @@author A0139812A
public class AliasCommandTest extends GuiTest {

    @Test
    public void alias_view_success() {
        console.runCommand("alias");
        assertTrue(aliasView.hasLoaded());
    }
    
    @Test
    public void alias_toList_success() {
        console.runCommand("alias list ls");
        assertAliasItemVisible("list", "ls");
        
        console.runCommand("add Buy milk");
        Task testTask = new Task();
        testTask.setName("Buy milk");
        assertTaskVisibleAfterCmd("ls", testTask);
    }
    
    /**
     * Helper function to assert that AliasItem is visible.
     */
    private void assertAliasItemVisible(String aliasKey, String aliasValue) {
        // Make sure we can see the Alias View.
        assertTrue(aliasView.hasLoaded());
        
        // Gets the matching AliasItem. Since it matches, if it's not null -> it definitely exists.
        AliasItemHandle aliasItem = aliasView.getAliasItem(aliasKey, aliasValue);
        assertNotNull(aliasItem);
    }

}
