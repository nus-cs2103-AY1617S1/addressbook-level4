package guitests;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.model.task.TaskComponent;
import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskMasterGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find read", td.book.getTaskDateComponent().get(0), td.lecture.getTaskDateComponent().get(0)); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find read",td.lecture.getTaskDateComponent().get(0));
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
    
    //@@author A0147967J
    @Test
    public void find_byDeadline(){
    	try{
    		//By time-after and sharp
    		assertFindResult("find by 18 oct 5pm", td.labDeadline.getTaskDateComponent().get(0));
    		//By time-before
    		assertFindResult("find by 18 oct 4.59pm");
    	}catch(Exception e){}
    }
    
    @Test
    public void find_byTimeSlot(){
    	//Cover multiple
    	assertFindResult("find from 17 oct 10am to 17 oct 10pm", td.tutorialSlot.getTaskDateComponent().get(0), td.concert.getTaskDateComponent().get(0));
    	//Cover one
    	assertFindResult("find from 17 oct 10am to 17 oct 3pm", td.tutorialSlot.getTaskDateComponent().get(0));
    	//Cover none
    	assertFindResult("find from 17 oct 5pm to 17 oct 6pm");
    }
    
    @Test
    public void find_byTag(){
    	//Existing one tag one result successful
    	assertFindResult("find t/textBook", td.book.getTaskDateComponent().get(0));
    	//Existing multiple tags multiple results successful
    	assertFindResult("find t/CS2105 t/textBook", td.book.getTaskDateComponent().get(0), td.homework.getTaskDateComponent().get(0));
    	//Invalid/Non-existing tag lists nothing
    	assertFindResult("find t/nothing");
    	assertFindResult("find t/--[][]");
    }
    
    @Test
    public void find_byType(){
    	//Floating tasks
    	assertFindResult("find -F",td.trash.getTaskDateComponent().get(0), td.book.getTaskDateComponent().get(0), 
    	        td.homework.getTaskDateComponent().get(0), td.lecture.getTaskDateComponent().get(0), 
    	        td.meeting.getTaskDateComponent().get(0), td.george.getTaskDateComponent().get(0));
    	//Completed tasks
    	commandBox.runCommand("done 1");
    	assertFindResult("find -C", td.trash.getTaskDateComponent().get(0));    	
    }
    //@@author
    private void assertFindResult(String command, TaskComponent... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
