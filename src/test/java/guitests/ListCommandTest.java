package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.TestActivity;

public class ListCommandTest extends AddressBookGuiTest  {

    @Test
    public void list_fromEmptyPanels() {
        TestActivity[] currentList = td.getTypicalPersons();
        commandBox.runCommand("find nothing");
        commandBox.runCommand("list");
        assertTrue(activityListPanel.isListMatching(currentList));  
    }
    
}
