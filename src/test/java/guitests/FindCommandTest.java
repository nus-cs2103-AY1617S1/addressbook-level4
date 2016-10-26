package guitests;

import org.junit.Test;

import seedu.whatnow.testutil.TestTask;
import seedu.whatnow.commons.core.Messages;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends WhatNowGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find Party", td.k, td.p); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete schedule 1");
        assertFindResult("find Party", td.p);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Apricot"); //no results
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
