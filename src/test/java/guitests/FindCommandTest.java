package guitests;

import org.junit.Test;

import seedu.lifekeeper.commons.core.Messages;
import seedu.lifekeeper.testutil.TestActivity;
import seedu.lifekeeper.testutil.TypicalTestActivities;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
       
        assertFindResult("find Daniel",td.findDaniel); 

        //find after deleting one result
        commandBox.runCommand("delete 2");
        assertFindResult("find Daniel",TypicalTestActivities.findDaniel);
    }
    


    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    @Test
    public void findtag_nonEmptyList(){
        assertFindResult("findtag lunch",td.getTaggedActivitiesOnly("lunch"));
        
        assertFindResult("findtag nothing");//no results
    
        assertFindResult("findtag dinner",td.getTaggedActivitiesOnly("dinner"));
    }
    
    private void assertFindResult(String command, TestActivity... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " task(s) listed!");
        assertTrue(activityListPanel.isListMatching(expectedHits));
    }
}
