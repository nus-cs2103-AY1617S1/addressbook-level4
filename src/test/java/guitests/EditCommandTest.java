package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.TaskOcurrence;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

//@@author A0147967J
public class EditCommandTest extends TaskMasterGuiTest {

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
        
        //add a tag
        toBeEdited = currentList[index-1];
        toBeEdited.setTag(new UniqueTagList(new Tag("testTag")));
        currentList[index-1] = toBeEdited;
        assertEditSuccess(toBeEdited, "edit 4 t/testTag", currentList);
        
        //add tags
        toBeEdited = currentList[index-1];
        toBeEdited.setTag(new UniqueTagList(new Tag("testTag"),new Tag("testTag1")));
        currentList[index-1] = toBeEdited;
        assertEditSuccess(toBeEdited, "edit 4 t/testTag t/testTag1", currentList);
        
        //change name
        toBeEdited = currentList[index-1];
        toBeEdited.setName(new Name("Test name"));
        currentList[index-1] = toBeEdited;
        assertEditSuccess(toBeEdited, "edit 4 Test name", currentList);
        
        //invalid index
        commandBox.runCommand("edit " + currentList.length + 1 + " invalid index");
        assertResultMessage("The task index provided is invalid");
        
        //invalid command
        commandBox.runCommand("edits read weblecture");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //Edit a normal task to a recurring task
        toBeEdited = currentList[index - 1];
        toBeEdited.setRecurringType(RecurringType.MONTHLY);
        currentList[index - 1] = toBeEdited;
        assertEditSuccess(toBeEdited, "edit 4 monthly", currentList);
        
        //Edit it back also enabled
        toBeEdited = currentList[index - 1];
        toBeEdited.setRecurringType(RecurringType.NONE);
        currentList[index - 1] = toBeEdited;
        assertEditSuccess(toBeEdited, "edit 4 none", currentList);

    }
    
    private void assertEditSuccess(TestTask editedCopy, String command, TestTask... modifiedList) {
    	
        commandBox.runCommand(command);
        
        //confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedCopy.getName().fullName);
        assertMatching(editedCopy.getTaskDateComponent().get(0), editedCard);

        //confirm the list now contains all the unmodified tasks and the edited task
        TaskOcurrence[] taskComponents = TestUtil.convertTasksToDateComponents(modifiedList);
        
        assertTrue(taskListPanel.isListMatching(taskComponents));
    }
    


}
