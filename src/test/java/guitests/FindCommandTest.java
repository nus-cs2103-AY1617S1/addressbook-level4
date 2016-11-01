package guitests;
//
//import org.junit.Test;
//import seedu.address.commons.core.Messages;
//import seedu.address.testutil.TestTask;
//import seedu.address.testutil.TypicalTestTasks;
//
//import static org.junit.Assert.assertTrue;
//
public class FindCommandTest extends TaskManagerGuiTest {
//
//    @Test
//    public void find_nonEmptyList() {
//        assertFindResult("find Mark"); //no results
//        assertFindResult("find 4", TypicalTestTasks.deadlineIn7Days, TypicalTestTasks.event1); //multiple results
//
//        //find after deleting one result
//        commandBox.runCommand("delete 1");
//        assertFindResult("find 4",TypicalTestTasks.event1);
//    }
//
//    @Test
//    public void find_emptyList(){
//        commandBox.runCommand("clear");
//        assertFindResult("find hw"); //no results
//    }
//
//    @Test
//    public void find_invalidCommand_fail() {
//        commandBox.runCommand("findhw");
//        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
//    }
//
//    private void assertFindResult(String command, TestTask... expectedHits ) {
//        commandBox.runCommand(command);
//        assertListSize(expectedHits.length);
//        assertResultMessage(expectedHits.length + " tasks listed!");
//        assertTrue(taskListPanel.isListMatching(expectedHits));
//    }
}
