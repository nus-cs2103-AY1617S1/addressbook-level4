//@@author A0148096W

package guitests;

import org.junit.Test;

import teamfour.tasc.logic.commands.ShowCommand;
import teamfour.tasc.testutil.TestTask;

import static org.junit.Assert.assertTrue;
import static teamfour.tasc.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ShowCommandTest extends AddressBookGuiTest {

    /*
     * All exceptions for invalid arguments are handled 
     * so that the program does not crash for the user.
     * 
     * - The test methods test one argument type at a time.
     * - Then tests combined arguments and continuous executions of show command.
     */
    
    //---------------- Tests individual arguments ----------------------
    
    @Test
    public void show_invalidCommand_showUsageMessage() {
        commandBox.runCommand("show");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
    }

    @Test
    public void show_emptyList_noResults(){
        commandBox.runCommand("clear");
        assertListResult("show tasks"); //no results
    }
    
    @Test
    public void list_type_overdue() {
        assertListResult("show overdue", td.submitProgressReport);
    }
    
    @Test
    public void list_type_recurring() {
        assertListResult("show recurring", td.developerMeeting,
                td.learnVim);
    }

    @Test
    public void show_type_uncompleted() {
        assertListResult("show uncompleted", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.learnVim, td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void show_type_completed() {
        assertListResult("show completed", td.researchWhales);
    }
    
    @Test
    public void show_type_tasks() {
        assertListResult("show tasks", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.buyBirthdayGift);
    }
    
    @Test
    public void show_type_events() {
        assertListResult("show events", td.researchWhales,
                td.learnVim, td.signUpForYoga);
    }
    
    @Test
    public void show_type_completedEvents() {
        assertListResult("show completed events", td.researchWhales);
    }
    
    @Test
    public void show_type_uncompletedEvents() {
        assertListResult("show events uncompleted", td.learnVim, 
                td.signUpForYoga);
    }
    
    @Test
    public void show_date_on1Jan2022() {
        assertListResult("show on 1 jan 2022", td.researchWhales);
    }
    
    @Test
    public void show_deadline_byYear2020() {
        assertListResult("show by 12 dec 2020", td.submitProgressReport,
                td.buyBirthdayGift);
    }
    
    @Test
    public void show_startTime_fromYear2022() {
        assertListResult("show from 1 jan 2022", td.submitPrototype, 
                td.researchWhales);
    }
    
    @Test
    public void show_endTime_toYear2021() {
        assertListResult("show to 30 dec 2021", td.submitPrototype,
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim,
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void show_tags_withMatches() {
        assertListResult("show tag urgent", td.submitPrototype, 
                td.submitProgressReport);
    }
    
    @Test
    public void show_tags_noMatches() {
        assertListResult("show tag thistagdoesnotexist");
    }
    

    //---------------- Tests combined arguments ----------------------
    
    @Test
    public void show_combinedArgs() {
        assertListResult("show uncomplete tasks from 1 jan 1998"
                + " to 1 jan 2024, tag urgent", td.submitPrototype,
                td.submitProgressReport);
    }
    
    @Test
    public void show_continuously_narrowsList() {
        assertListResult("show to 30 dec 2021", td.submitPrototype,
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim,
                td.buyBirthdayGift, td.signUpForYoga);
        
        assertListResult("show uncompleted", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.learnVim, td.buyBirthdayGift, td.signUpForYoga);
        
        assertListResult("show events", td.learnVim, 
                td.signUpForYoga);

        assertListResult("show recurring", td.learnVim);
        
        assertListResult("show completed");
    }

    //---------------- Utility methods ----------------------
    
    private void assertListResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
