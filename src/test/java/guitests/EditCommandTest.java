package guitests;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;

import static org.junit.Assert.assertTrue;
import static seedu.dailyplanner.logic.commands.EditCommand.MESSAGE_EDIT_PERSON_SUCCESS;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.logic.parser.*;
import seedu.dailyplanner.model.task.*;
import seedu.testplanner.testutil.TestTask;
import seedu.testplanner.testutil.TestUtil;

public class EditCommandTest extends AddressBookGuiTest {

    @Test
    public void edit() throws IllegalValueException {

	TestTask[] currentList = td.getTypicalPersons();
	int targetIndex = currentList.length;
	
	// edit task date
	String newDate = "d/next friday";
	assertEditSuccess(targetIndex, currentList, newDate);

	// edit task start time
	String newStartTime = "s/10.15am";
	assertEditSuccess(targetIndex, currentList, newStartTime	);

	// edit task end time
	String newEndTime = "e/12.30pm";
	assertEditSuccess(targetIndex, currentList, newEndTime);
    }

    private void assertEditSuccess(int targetIndex, TestTask[] currentList, String arg) throws IllegalValueException {
	
	TestTask personToEdit = currentList[targetIndex - 1];
	char fieldToEdit = arg.charAt(0);
	int len = arg.length();
	String newField = arg.substring(2, len-1);
	nattyParser np = new nattyParser();
	
	switch (fieldToEdit) {
	
	case 'd':
	    String newDateString = np.parse(newField);
	    Date1 newDate = new Date1(newDateString);
	    personToEdit.setDate(newDate);
	    
	case 's':
	    String newStartTimeString = np.parseTime(newField);
	    StartTime newStartTime = new StartTime(newStartTimeString);
	    personToEdit.setStartTime(newStartTime);
	    
	case 'e':
	    String newEndTimeString = np.parseTime(newField);
	    EndTime newEndTime = new EndTime(newEndTimeString);
	    personToEdit.setEndTime(newEndTime);
	}
	
	commandBox.runCommand("edit " + targetIndex + arg);
	PersonCardHandle editedCard = personListPanel.navigateToPerson(personToEdit.getName().fullName);
	assertMatching(personToEdit, editedCard);
    }

}
