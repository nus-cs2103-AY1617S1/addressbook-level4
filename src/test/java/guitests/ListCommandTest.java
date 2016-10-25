//@@author A0148096W

package guitests;

import org.junit.Test;

import teamfour.tasc.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class ListCommandTest extends AddressBookGuiTest {

    /*
     * All exceptions for invalid arguments are handled 
     * so that the program does not crash for the user.
     * 
     * - The test methods test one argument type at a time.
     * - Then tests combined arguments with as little tests as possible.
     */
    
    //---------------- Tests individual arguments ----------------------
    
    @Test
    public void list_noParameter_nonEmptyList() {
        assertListResult("list", td.submitPrototype, 
                td.submitProgressReport, td.signUpForYoga,
                td.buyBirthdayGift, td.learnVim, td.developerMeeting);

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertListResult("list", 
                td.submitProgressReport, td.signUpForYoga,
                td.buyBirthdayGift, td.learnVim, td.developerMeeting);
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
        assertListResult("list overdue", td.submitProgressReport);
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
    public void list_deadline_byYear2020() {
        assertListResult("list by 12 dec 2020", td.submitProgressReport,
                td.buyBirthdayGift);
    }
    
    @Test
    public void list_startTime_fromYear2022() {
        assertListResult("list from 1 jan 2022", td.submitPrototype, 
                td.researchWhales);
    }
    
    @Test
    public void list_endTime_toYear2021() {
        assertListResult("list to 30 dec 2021", td.submitPrototype,
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
    public void list_sort_a_to_z() {
        assertListResult("list all sort a-z", td.developerMeeting, 
                td.buyBirthdayGift, td.learnVim,
                td.researchWhales, td.signUpForYoga, 
                td.submitProgressReport, td.submitPrototype);
    }
    
    @Test
    public void list_sort_z_to_a() {
        assertListResult("list all sort z-a", td.submitPrototype, 
                td.submitProgressReport, td.signUpForYoga,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.developerMeeting);
    }
    
    @Test
    public void list_sort_earliestFirst() {
        assertListResult("list all sort earliest first", 
                td.submitPrototype, td.submitProgressReport, 
                td.signUpForYoga, td.buyBirthdayGift,
                td.researchWhales, td.learnVim, 
                td.developerMeeting);
    }
    
    @Test
    public void list_sort_latestFirst() {
        assertListResult("list all sort latest first",  
                td.developerMeeting, td.researchWhales,
                td.learnVim, td.buyBirthdayGift, 
                td.signUpForYoga, td.submitProgressReport,
                td.submitPrototype);
    }
    
    @Test
    public void list_sort_default_noSort() {
        assertListResult("list all sort writeanythinghere", 
                td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    //---------------- Tests combined arguments ----------------------
    
    @Test
    public void list_combinedArgs_uncompletedTasks_withPeriod_withTag_sortZToA() {
        assertListResult("list uncomplete tasks from 1 jan 1998"
                + " to 1 jan 2020, tag urgent, sort z-a", 
                td.submitPrototype, td.submitProgressReport);
    }
    
    @Test
    public void list_combinedArgs_completedEvents_withEndTime_sortEarliestFirst() {
        assertListResult("list completed events to 18 sep 2024, sort earliest first", 
                td.researchWhales);
    }
    
    @Test
    public void list_combinedArgs_recurringTasks_withDeadline_sortLatestFirst() {
        assertListResult("list recurring by 19 sep 2024, sort latest first", 
                td.developerMeeting);
    }

    //---------------- Utility methods ----------------------
    
    private void assertListResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
