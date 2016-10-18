package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.address.model.task.Name;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskDateComponent;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class EditCommandTest extends TaskListGuiTest {

    @Test
    public void edit() throws IllegalValueException {
    	
    	//Fix Index first to see edit effect
        //edit deadline
    	int index = 4;
        TestTask[] currentList = td.getTypicalTasks();
        TestTask toBeEdited = currentList[index-1];
        toBeEdited.setEndDate("2 oct 10am");
        currentList[index-1] = toBeEdited;
        assertEditSuccess(toBeEdited, "edit 4 by 2 oct 10am", currentList);
        
        //edit it to time slot
        toBeEdited = currentList[index-1];
        toBeEdited.setEndDate("2 oct 9am");
        toBeEdited.setEndDate("2 oct 11am");
        currentList[index-1] = toBeEdited;
        assertEditSuccess(toBeEdited, "edit 4 from 2 oct 9am to 2 oct 11am", currentList);
        

    }
    
    private void assertEditSuccess(TestTask editedCopy, String command, TestTask... modifiedList) {
    	
        commandBox.runCommand(command);
        
        //confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedCopy.getName().fullName);
        assertMatching(editedCopy.getTaskDateComponent().get(0), editedCard);

        //confirm the list now contains all the unmodified tasks and the edited task
        TaskDateComponent[] taskComponents = TestUtil.convertTasksToDateComponents(modifiedList);
        
        assertTrue(taskListPanel.isListMatching(taskComponents));
    }
    


}
