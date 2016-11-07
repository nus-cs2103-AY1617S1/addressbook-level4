package guitests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import guitests.guihandles.AliasCardHandle;
import guitests.guihandles.AliasWindowHandle;
import w15c2.tusk.model.Alias;
import w15c2.tusk.testutil.AliasTesterUtil;

//@@author A0138978E
public class AliasCommandTest extends TaskManagerGuiTest {

    @Test
    public void aliasCommand_openAliasWindow() {
        commandBox.runCommand("list alias");
        assertAliasWindowOpen(mainGui.getAliasWindow());
    }
    
    @Test
    public void aliasCommand_addOneAlias() {
        Alias aliasToAdd = new Alias("am", "add meeting");
        commandBox.runCommand(AliasTesterUtil.getAddAliasCommandFromAlias(aliasToAdd));
        commandBox.runCommand("list alias");
        
        assertAddAliasSuccess(mainGui.getAliasWindow(), new ArrayList<Alias>(), aliasToAdd);
    }

    private void assertAddAliasSuccess(AliasWindowHandle aliasWindowHandle, List<Alias> currentAliasList, Alias addedAlias) {
        assertTrue(aliasWindowHandle.isWindowOpen());
        AliasCardHandle addedAliasCard = aliasWindowHandle.getAliasCardHandle(addedAlias);
        assertTrue(addedAliasCard.isSameAlias(addedAlias));
        aliasWindowHandle.closeWindow();
    }
    
    private void assertAliasWindowOpen(AliasWindowHandle aliasWindowHandle) {
        assertTrue(aliasWindowHandle.isWindowOpen());
        aliasWindowHandle.closeWindow();
    }
}
