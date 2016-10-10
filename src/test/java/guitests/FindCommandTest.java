package guitests;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find Mcdonalds", td.carl, td.george); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Flunk", td.daniel);
    }
    
    @Test
    public void find_Date() {
        assertFindResult("find 14102016", td.benson, td.carl);
    }
    
    @Test
    public void find_address() {
        assertFindResult("find hall", td.daniel, td.elle);
    }
    
    @Test
    public void find_tag() {
        assertFindResult("find Priority", td.alice, td.benson, td.carl, td.daniel);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(personListPanel.isListMatching(expectedHits));
    }
}
