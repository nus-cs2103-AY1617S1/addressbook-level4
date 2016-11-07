package guitests;

import jym.manager.commons.core.Messages;
import jym.manager.testutil.TestTask;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
<<<<<<< HEAD
        assertFindResult("find Mark"); //no results
        assertFindResult("find homework", td.doHomework, td.writeProgram); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find write program",td.writeProgram);
=======
        assertFindResult("find Mark"); // no results
        assertFindResult("find Meier", td.benson, td.daniel); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meier", td.daniel);
>>>>>>> nus-cs2103-AY1617S1/master
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
<<<<<<< HEAD
        assertFindResult("find do Homework"); //no results
=======
        assertFindResult("find Jean"); // no results
>>>>>>> nus-cs2103-AY1617S1/master
    }

//    @Test
//    public void find_invalidCommand_fail() {
//        commandBox.runCommand("findgeorge");
//        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
//    }

<<<<<<< HEAD
    private void assertFindResult(String command, TestTask... expectedHits ) {
=======
    private void assertFindResult(String command, TestPerson... expectedHits) {
>>>>>>> nus-cs2103-AY1617S1/master
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
