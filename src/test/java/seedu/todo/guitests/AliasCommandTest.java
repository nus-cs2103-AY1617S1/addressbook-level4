package seedu.todo.guitests;

import static org.junit.Assert.*;

import org.junit.Test;

// @@author A0139812A
public class AliasCommandTest extends GuiTest {

    @Test
    public void alias_view_success() {
        console.runCommand("alias");
        assertTrue(aliasView.hasLoaded());
    }

}
