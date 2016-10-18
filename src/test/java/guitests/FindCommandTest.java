package guitests;

import org.junit.Test;

import seedu.whatnow.testutil.TestTask;
import seedu.whatnow.commons.core.Messages;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends WhatNowGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find Buy", td.a, td.c, td.d, td.g); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Buy",td.c, td.d, td.g);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find apricots"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findcake");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
