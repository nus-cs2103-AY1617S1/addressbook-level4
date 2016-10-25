//@@author A0127014W
package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

public class CollapseExpandCommandTest extends AddressBookGuiTest{

    @Test
    public void collapse_alreadyCollapsed() {
        commandBox.runCommand("collapse");
        assertResultMessage("Task view collapsed");
        commandBox.runCommand("collapse");
        assertResultMessage("Already in collapsed view, type \"expand\" to go into expanded view");
    }

    @Test
    public void collapse_after_expand() {
        commandBox.runCommand("expand");
        assertResultMessage("Task view expanded");
        commandBox.runCommand("collapse");
        assertResultMessage("Task view collapsed");
    }

    /*
    @Test
    public void expand_alreadyExpanded() {
        commandBox.runCommand("expand");
        assertResultMessage("Task view expanded");
        commandBox.runCommand("expand");
        assertResultMessage("Already in expanded view, type \"collapse\" to go into collapsed view");
    }

    @Test
    public void expand_after_collapse() {
        commandBox.runCommand("collapse");
        assertResultMessage("Task view collapsed");
        commandBox.runCommand("expand");
        assertResultMessage("Task view expanded");
    }
    */
}
