package guitests;

import org.junit.Test;
import seedu.address.testutil.TestPerson;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.dailyplanner.logic.commands.EditCommand.MESSAGE_EDIT_PERSON_SUCCESS;

public class EditCommandTest extends AddressBookGuiTest{

    @Test
    public void edit() {
	
	//edit task name
	TestPerson[] currentList = td.getTypicalPersons();
	int targetIndex = currentList.length/2;
	String args = "d/"
	assertEditSuccess(targetIndex, currentList);
	//edit task date
	
	//edit task start time
	
	//edit task end time

    }

}
