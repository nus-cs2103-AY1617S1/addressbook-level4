package guitests;

import org.junit.Ignore;
import org.junit.Test;
import seedu.todo.commons.core.Messages;
import seedu.todo.testutil.TestPerson;

@Deprecated
public class FindCommandTest extends AddressBookGuiTest {

    @Test
    @Ignore
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find Meier", td.benson, td.daniel); //multiple results

        //find after deleting one result
//        commandBox.runCommand("delete 1");
        assertFindResult("find Meier",td.daniel);
    }

    @Test
    @Ignore
    public void find_emptyList(){
//        commandBox.runCommand("clear");
        assertFindResult("find Jean"); //no results
    }

    @Test
    @Ignore
    public void find_invalidCommand_fail() {
//        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestPerson... expectedHits ) {
//        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " persons listed!");
//        assertTrue(personListPanel.isListMatching(expectedHits));
    }
}
