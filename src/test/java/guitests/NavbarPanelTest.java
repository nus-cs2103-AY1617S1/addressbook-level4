package guitests;

import org.junit.Test;

import seedu.address.model.task.TaskComponent;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

//@@author A0147967J
public class NavbarPanelTest extends TaskMasterGuiTest {
	
	private final String NAVBAR_TASKS = " Tasks";
    private final String NAVBAR_DEADLINES = " Deadlines";
    private final String NAVBAR_INCOMING_DEADLINES = " Incoming Deadlines";
    private final String NAVBAR_FLOATING_TASKS = " Floating Tasks";
    private final String NAVBAR_COMPLETED = " Completed";
    	
	@Test
	public void navigation() throws Exception{
		
		//Navigate to completed ones
		commandBox.runCommand("done 1");
		assertResult(NAVBAR_COMPLETED, td.trash.getComponentForNonRecurringType());
		
		//Navigate to all tasks
		TaskComponent[] expected = Arrays.copyOfRange(td.getTypicalTaskComponents(), 1, td.getTypicalTaskComponents().length);
		assertResult(NAVBAR_TASKS, expected);
		
		//Navigate to incoming deadlines
		commandBox.runCommand(td.incoming.getAddNonFloatingCommand());
		assertResult(NAVBAR_INCOMING_DEADLINES, td.labDeadline.getComponentForNonRecurringType(),
												td.essayDeadline.getComponentForNonRecurringType(),
												td.incoming.getComponentForNonRecurringType());
		
		//Navigate to deadlines
		assertResult(NAVBAR_DEADLINES, td.labDeadline.getComponentForNonRecurringType(),
												td.essayDeadline.getComponentForNonRecurringType());
		//Navigate to all floating tasks
		expected = Arrays.copyOfRange(td.getTypicalTaskComponents(), 1, 6);
		assertResult(NAVBAR_FLOATING_TASKS, expected);
		
	}
		
	private void assertResult(String navigation, TaskComponent... expectedHits) {
        navbar.navigateTo(navigation);
        assertListSize(expectedHits.length);
        if(navigation.equals(NAVBAR_TASKS))
        	assertResultMessage("Listed all tasks");
        else
        	assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

}
