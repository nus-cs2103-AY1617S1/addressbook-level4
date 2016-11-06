package seedu.todo.guitests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.ConfigCenter;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.guitests.guihandles.AliasItemHandle;
import seedu.todo.models.Task;

// @@author A0139812A
public class AliasCommandTest extends GuiTest {
    
    // Fixtures
    private LocalDateTime twoDaysFromNow = LocalDateTime.now().plusDays(2);
    private String twoDaysFromNowIsoString = DateUtil.formatIsoDate(twoDaysFromNow);
    
    @Before
    public void beforeTest() {
        console.runCommand("clear");
        
        try {
            ConfigCenter.getInstance().saveConfig(new Config());
        } catch (IOException e) {
            fail("Failed to reset config.");
        }
    }

    @Test
    public void alias_view_success() {
        console.runCommand("alias");
        assertTrue(aliasView.hasLoaded());
    }
    
    @Test
    public void alias_toList_success() {
        console.runCommand("alias ls list");
        assertAliasItemVisible("ls", "list");
        
        console.runCommand("add Buy milk");
        console.runCommand("alias"); // Move away from IndexView
        
        Task testTask = new Task();
        testTask.setName("Buy milk");
        assertTaskVisibleAfterCmd("ls", testTask);
        assertValidCommand("ls");
    }
    
    @Test
    public void alias_toAdd_success() {
        console.runCommand("alias a add");
        assertAliasItemVisible("a", "add");
        
        Task testTask = new Task();
        testTask.setName("Buy milk");
        assertTaskVisibleAfterCmd("a Buy milk", testTask);
    }

    @Test
    public void alias_toAddTaskDeadline_success() {
        console.runCommand("alias a add");
        assertAliasItemVisible("a", "add");
        
        String command = String.format("a Buy milk by %s 5pm", twoDaysFromNowIsoString);
        Task testTask = new Task();
        testTask.setName("Buy milk");
        testTask.setDueDate(DateUtil.parseDateTime(String.format("%s 17:00:00", twoDaysFromNowIsoString)));
        assertTaskVisibleAfterCmd(command, testTask);
    }
    
    @Test
    public void alias_toInvalidCommand_isInvalid() {
        console.runCommand("alias invalidcommand ic");
        assertAliasItemVisible("invalidcommand", "ic");
        console.runCommand("ic");
        assertInvalidCommand("ic");
    }
    
    @Test
    public void alias_missingValue_disambiguate() {
        console.runCommand("alias list");
        String disambiguation = "alias list <alias value>";
        assertEquals(disambiguation, console.getConsoleInputText());
    }
    
    @Test
    public void alias_tooManyArgs_disambiguate() {
        console.runCommand("alias show me the money");
        String disambiguation = "alias show methemoney";
        assertEquals(disambiguation, console.getConsoleInputText());
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
