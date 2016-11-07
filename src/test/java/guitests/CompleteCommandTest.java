package guitests;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.GuiHandle;
import guitests.guihandles.PersonCardHandle;
import org.junit.Test;

import seedu.dailyplanner.commons.core.Messages;
import seedu.dailyplanner.logic.commands.AddCommand;
import seedu.testplanner.testutil.TestTask;
import seedu.testplanner.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

//@@author A0140124B
public class CompleteCommandTest extends AddressBookGuiTest {

	@Test
	public void add() {
		// add one person
		TestTask[] currentList = td.getTypicalPersons();
		TestTask personToAdd = td.learnPython;
		assertCompleteSuccess(personToAdd, currentList);
		currentList = TestUtil.addPersonsToList(currentList, personToAdd);

	}

	private void assertCompleteSuccess(TestTask personToAdd, TestTask... currentList) {
		
		commandBox.runCommand(personToAdd.getAddCommand());
		
	}

}
