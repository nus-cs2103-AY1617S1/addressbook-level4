package guitests;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.model.task.TaskDateComponent;
import seedu.address.testutil.TestTask;
import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskListGuiTest {

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
    
//    @Test
//    public void find_byDate(){
//    	try{
//    		//By date
//    		assertFindResult("find by 18 oct", td.labDeadline);
//    		//By time-after and sharp
//    		assertFindResult("find by 18 oct 5pm", td.labDeadline);
//    		//By time-before
//    		assertFindResult("find by 17 oct");
//    	}catch(Exception e){}
//    }
//    
//    @Test
//    public void find_byTimeSlot(){
//    	//Cover multiple
//    	assertFindResult("find from 17 oct 10am to 17 oct 10pm", td.tutorialSlot, td.concert);
//    	//Cover one
//    	//assertFindResult("find from 17 oct 10am to 17 oct 3pm", td.tutorialSlot);
//    	//Cover none
//    	assertFindResult("find from 17 oct 5pm to 17 oct 6pm");
//    }
    
    @Test
    public void find_byType(){
    	//Floating tasks
    	assertFindResult("find -F",td.trash, td.book, td.homework, td.lecture, td.meeting, td.george);
    	//Completed tasks
    	commandBox.runCommand("done 1");
    	assertFindResult("find -C", td.trash);    	
    }
    
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
