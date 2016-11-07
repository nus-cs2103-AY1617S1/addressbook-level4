//@@author A0141052Y
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TypicalTestTasks;

public class SearchCommandTest extends TaskManagerGuiTest {
    @Test
    public void search() {
        TestTask[] currentList = td.getTypicalTasks();
        
        // test search no name
        assertSearchResult("", currentList);

        // test search full name
        assertSearchResult(TypicalTestTasks.daniel.getName().taskName, TypicalTestTasks.daniel);
        
        // test search partial name
        assertSearchResult("Have lunch", TypicalTestTasks.daniel);
        
        // test search no results
        assertSearchResult("this does not exist");
    }
    
    private void resetCommandBox() {
        commandBox.pressEnter();
    }
    
    private void assertSearchResult(String query, TestTask... expectedHits ) {
        commandBox.runCommand("searchbox");
        commandBox.enterCommand(query);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
        
        commandBox.runCommand(query);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
        
        resetCommandBox();
    }
}
