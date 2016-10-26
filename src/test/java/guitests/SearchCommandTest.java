//@@author A0141052Y
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.testutil.TestTask;

public class SearchCommandTest extends TaskManagerGuiTest {
    @Test
    public void search() {
        TestTask[] currentList = td.getTypicalTasks();

        // test search full name
        assertSearchResult(td.daniel.getName().taskName, td.daniel);
        
        // test search partial name
        assertSearchResult("Have lunch", td.daniel);
        
        // test search no name
        assertSearchResult("", currentList);
        
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
