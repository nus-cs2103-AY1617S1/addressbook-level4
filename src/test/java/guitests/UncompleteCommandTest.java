package guitests;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.GuiHandle;
import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.dailyplanner.commons.core.Messages;
import seedu.dailyplanner.logic.commands.AddCommand;
import seedu.testplanner.testutil.TestTask;
import seedu.testplanner.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

//@@author A0140124B
public class UncompleteCommandTest extends DailyPlannerGuiTest {

	@Test
	public void complete() {
		
		TestTask[] currentList = td.getTypicalTasks();
		TestTask taskToUncomplete = td.CS2103_Lecture;
		assertCompleteSuccess("uncomplete 2", taskToUncomplete);
	}

	private void assertCompleteSuccess(String command, TestTask taskToUnomplete) {
		
		commandBox.runCommand(command);
		
		//confirm the new card contains the right data
        TaskCardHandle uncompletedCard = taskListPanel.navigateToPerson(taskToUnomplete.getName());
        
        //assert that card is not complete
        assertTrue(!uncompletedCard.getCompletion().equals("COMPLETE"));
		
	}

}
