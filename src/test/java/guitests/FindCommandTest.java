package guitests;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends FlexiTrackGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); // no results
        //assertFindResult("find homework soccer", td.homework1, td.homework2, td.homework3, td.soccer); // multiple results
        commandBox.runCommand("find Mark");
        TestTask[] expectedHits = getExpectedHits(td.homework1, td.homework2, td.homework3, td.soccer);
        Arrays.sort(expectedHits);
        assertTrue(expectedHits.length==4);
        assertResultMessage(0 + " tasks listed!");

        // find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find soccer", td.soccer);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    private TestTask[] getExpectedHits(TestTask... expectedHits){
        return expectedHits;
    }
    
    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        Arrays.sort(expectedHits);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
