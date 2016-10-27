package guitests;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.testutil.TestPerson;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.dailyplanner.logic.commands.EditCommand.MESSAGE_EDIT_PERSON_SUCCESS;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.logic.parser.*;
import seedu.dailyplanner.model.task.*;

public class EditCommandTest extends AddressBookGuiTest {

    @Test
    public void edit() throws IllegalValueException {

	TestPerson[] currentList = td.getTypicalPersons();
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

    private void assertEditSuccess(int targetIndex, TestPerson[] currentList, String arg) throws IllegalValueException {
	
	TestPerson personToEdit = currentList[targetIndex - 1];
	char fieldToEdit = arg.charAt(0);
	int len = arg.length();
	String newField = arg.substring(2, len-1);
	nattyParser np = new nattyParser();
	
	switch (fieldToEdit) {
	
	case 'd':
	    String newDateString = np.parseDate(newField);
	    Date newDate = new Date(newDateString);
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
