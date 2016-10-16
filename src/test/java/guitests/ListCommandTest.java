package guitests;

import org.junit.Test;

import teamfour.tasc.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class ListCommandTest extends AddressBookGuiTest {

    @Test
    public void list_noParameter_nonEmptyList() {
        assertListResult("list", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.learnVim, td.buyBirthdayGift, td.signUpForYoga);

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertListResult("list", 
                td.submitProgressReport, td.developerMeeting,
                td.learnVim, td.buyBirthdayGift, td.signUpForYoga);
    }

    @Test
    public void list_noParameter_emptyList(){
        commandBox.runCommand("clear");
        assertListResult("list"); //no results
    }
    
    @Test
    public void list_type_all() {
        assertListResult("list all", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void list_type_overdue() {
        assertListResult("list overdue");
    }
    
    @Test
    public void list_type_recurring() {
        assertListResult("list recurring", td.developerMeeting,
                td.learnVim);
    }

    @Test
    public void list_type_uncompleted() {
        assertListResult("list uncompleted", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.learnVim, td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void list_type_completed() {
        assertListResult("list completed", td.researchWhales);
    }
    
    @Test
    public void list_type_tasks() {
        assertListResult("list tasks", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.buyBirthdayGift);
    }
    
    @Test
    public void list_type_events() {
        assertListResult("list events", td.researchWhales,
                td.learnVim, td.signUpForYoga);
    }
    
    @Test
    public void list_type_completedEvents() {
        assertListResult("list completed events", td.researchWhales);
    }
    
    @Test
    public void list_type_uncompletedEvents() {
        assertListResult("list events uncompleted", td.learnVim, 
                td.signUpForYoga);
    }
    
    @Test
    public void list_deadline_byYear2016() {
        assertListResult("list by 12 dec 2016", td.submitProgressReport,
                td.buyBirthdayGift);
    }
    
    @Test
    public void list_startTime_fromYear2018() {
        assertListResult("list from 1 jan 2018", td.submitPrototype, 
                td.researchWhales);
    }
    
    @Test
    public void list_endTime_toYear2017() {
        assertListResult("list to 30 dec 2017", td.submitPrototype,
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim,
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void list_tags_withMatches() {
        assertListResult("list tag urgent", td.submitPrototype, 
                td.submitProgressReport);
    }
    
    @Test
    public void list_tags_noMatches() {
        assertListResult("list tag thistagdoesnotexist");
    }
    
    @Test
    public void list_allParameters() {
        assertListResult("list uncomplete tasks from 1 jan 1998"
                + " to 1 jan 2020, tag urgent", td.submitPrototype,
                td.submitProgressReport);
    }

    private void assertListResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
