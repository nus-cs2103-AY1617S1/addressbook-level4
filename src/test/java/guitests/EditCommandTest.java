package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;

import static org.junit.Assert.assertTrue;
import static seedu.dailyplanner.logic.commands.EditCommand.MESSAGE_EDIT_TASK_SUCCESS;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.logic.parser.*;
import seedu.dailyplanner.model.task.*;
import seedu.testplanner.testutil.TestTask;
import seedu.testplanner.testutil.TestUtil;

public class EditCommandTest extends DailyPlannerGuiTest {

	@Test
	public void edit() throws IllegalValueException {

		TestTask[] currentList = td.getTypicalTasks();
		int targetIndex = currentList.length;

		// edit task start
		String newStartTime = "s/today 10.15am";
		assertEditSuccess(targetIndex, currentList, newStartTime);

		// edit task end
		String newEndTime = "e/today 12.30pm";
		assertEditSuccess(targetIndex, currentList, newEndTime);
	}

	private void assertEditSuccess(int targetIndex, TestTask[] currentList, String arg) throws IllegalValueException {

		TestTask personToEdit = currentList[targetIndex - 1];
		char fieldToEdit = arg.charAt(0);
		int len = arg.length();
		String newField = arg.substring(2, len - 1);
		nattyParser np = new nattyParser();

		switch (fieldToEdit) {

		case 's':
			String newStartString = np.parse(newField);
			String[] sDateTimeArr = newStartString.split(" ");
			DateTime newStart = new DateTime(new Date(sDateTimeArr[0]), new Time(sDateTimeArr[1]));
			personToEdit.setStart(newStart);

		case 'e':
			String newEndString = np.parse(newField);
			String[] eDateTimeArr = newEndString.split(" ");
			DateTime newEnd = new DateTime(new Date(eDateTimeArr[0]), new Time(eDateTimeArr[1]));
			personToEdit.setStart(newEnd);

		}

		commandBox.runCommand("edit " + targetIndex + arg);
		TaskCardHandle editedCard = taskListPanel.navigateToPerson(personToEdit.getName());
		assertMatching(personToEdit, editedCard);
	}

}
