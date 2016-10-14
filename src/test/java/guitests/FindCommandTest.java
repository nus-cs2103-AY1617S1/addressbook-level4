package guitests;

import org.junit.Test;

import teamfour.tasc.commons.core.Messages;
import teamfour.tasc.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find DSFKSDKFSDJKFDSKJFKDS"); //no results
        assertFindResult("find Submit", td.submitPrototype, td.submitProgressReport); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Submit",td.submitProgressReport);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find DSFKSDKFSDJKFDSKJFKDS"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findsubmitreport");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
