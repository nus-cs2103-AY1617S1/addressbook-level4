package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.logic.commands.AddCommand;
import seedu.unburden.testutil.TestTask;
import seedu.unburden.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends ListOfTaskGuiTest {
	
        //@Test
        public void add() throws IllegalValueException {
            //add one task
            TestTask[] currentList = td.getTypicalPersons();
            TestTask taskToAdd = td.hoon;
            assertAddSuccess(taskToAdd, currentList);
            currentList = TestUtil.addTasksToList(currentList, taskToAdd);

            //add another task
            taskToAdd = td.ida;
            assertAddSuccess(taskToAdd, currentList);
            currentList = TestUtil.addTasksToList(currentList, taskToAdd);

            //add duplicate task
            commandBox.runCommand(td.hoon.getAddCommand());
            assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
            assertTrue(taskListPanel.isListMatching(currentList));

            //add task with date
            taskToAdd = td.luhua;
            assertAddSuccess(taskToAdd, currentList);
            currentList = TestUtil.addTasksToList(currentList, taskToAdd);

            //add task with date and end time
            taskToAdd = td.hahaha;
            assertAddSuccess(taskToAdd, currentList);
            currentList = TestUtil.addTasksToList(currentList, taskToAdd);

            //add task with date and both start time/end time
            taskToAdd = td.hahahaha;
            assertAddSuccess(taskToAdd, currentList);
            currentList = TestUtil.addTasksToList(currentList, taskToAdd);
            
            //clear the list
            commandBox.runCommand("clear");
            assertAddSuccess(td.alice);

            //invalid command
            commandBox.runCommand("adds Johnny");
            assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
            
            commandBox.runCommand("add Johnny st/2000");
            assertResultMessage(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
            
            commandBox.runCommand("add Johnny et/2100 ");
            assertResultMessage(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
            
            commandBox.runCommand("add Johnny s/2000 e/2100");
            assertResultMessage(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
       }
        
              

    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) throws IllegalValueException {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToPerson(personToAdd.getName().getFullName());
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, personToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
