package guitests;

import org.junit.Test;

import tars.commons.core.Messages;
import tars.testutil.TestTask;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

public class FindCommandTest extends TarsGuiTest {

    // @@author A0124333U
    @Test
    public void find_quickSearch_nonEmptyList() {
        assertFindResultForQuickSearch("find Meeting"); // no results
        assertFindResultForQuickSearch("find Task B", td.taskB); // single result
        assertFindResultForQuickSearch("find Task", td.taskA, td.taskB, td.taskC, td.taskD, td.taskE, td.taskF, td.taskG); // multiple
                                                                                                             // results

        // find after deleting one result
        commandBox.runCommand("del 1");
        assertFindResultForQuickSearch("find A");
    }
    
    @Test
    public void find_filterSearch_nonEmptyList() {
        assertFindResultForFilterSearch("find /n Task B", td.taskB); // single result                                                                                                    // results

        // find after deleting one result
        commandBox.runCommand("del 1");
        assertFindResultForFilterSearch("find /n Task B"); //no results
    }

    @Test
    public void find_quickSearch_emptyList() {
        commandBox.runCommand("clear");
        assertFindResultForQuickSearch("find No Such Task"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findmeeting");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResultForQuickSearch(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);

        String[] keywordsArray = command.split("\\s+");
        ArrayList<String> keywordsList = new ArrayList<String>(Arrays.asList(keywordsArray));
        keywordsList.remove(0);

        assertResultMessage(
                expectedHits.length + " tasks listed!\n" + "Quick Search Keywords: " + keywordsList.toString());
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
    
    private void assertFindResultForFilterSearch(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);

        String keywordString = "[Task Name: Task B] ";

        assertResultMessage(
                expectedHits.length + " tasks listed!\n" + "Filter Search Keywords: " + keywordString);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
