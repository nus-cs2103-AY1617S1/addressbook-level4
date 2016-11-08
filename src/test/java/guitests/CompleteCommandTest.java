package guitests;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.GuiHandle;
import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.dailyplanner.commons.core.Messages;
import seedu.dailyplanner.logic.commands.AddCommand;
import seedu.testplanner.testutil.TestTask;
import seedu.testplanner.testutil.TestUtil;

import static org.junit.Assert.assertEquals;

//@@author A0140124B
public class CompleteCommandTest extends DailyPlannerGuiTest {

	@Test
	public void complete() {
		
		TestTask taskToComplete = td.CS2103_Project;
		assertCompleteSuccess("complete 1", taskToComplete);
	}

	private void assertCompleteSuccess(String command, TestTask taskToComplete) {
		
		commandBox.runCommand(command);
		//confirm the new card contains the right data
        TaskCardHandle completedCard = taskListPanel.navigateToPerson(taskToComplete.getName());
        assertEquals("Task is complete", completedCard.getCompletion(), "COMPLETE");
		
	}

}
