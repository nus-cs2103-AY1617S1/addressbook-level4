package guitests;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.AddCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.*;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.taskH;
        commandBox.runCommand("add Help Jim with his task, at 2016-10-25 9am");
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = TypicalTestTasks.taskI;
        commandBox.runCommand("add Iron new clothes, by 2016-10-27 10pm");
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand("add Help Jim with his task, at 2016-10-25 9am");
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("add Accompany mom to the doctor, from 2016-10-26 2pm to 2016-10-26 5pm #gwsMum");
        assertAddSuccess(TypicalTestTasks.taskA);
        
        commandBox.runCommand("clear");
        commandBox.runCommand("add Accompany friend to the doctor");
        assertAddSuccess(TypicalTestTasks.taskK);
        
        commandBox.runCommand("clear");
        commandBox.runCommand("add Christmas party, at christmas 2pm");
        assertAddSuccess(TypicalTestTasks.taskL);
        
        commandBox.runCommand("clear");
        commandBox.runCommand("add Christmas party, at christmas 2pm by 11pm");
        assertAddSuccess(TypicalTestTasks.taskM);
        
        commandBox.runCommand("clear");
        commandBox.runCommand("add Christmas party, to christmas 8pm from 2pm");
        assertAddSuccess(TypicalTestTasks.taskN);


        //invalid command
        commandBox.runCommand("adds Meet Jim");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //@@author A0152958R
        commandBox.runCommand("add Meet Jim, at what time");
        assertResultMessage(Messages.MESSAGE_INVALID_TIME_FORMAT);
        
        commandBox.runCommand("add Meet Jim, at 9pm to 10pm");
        assertResultMessage(Messages.MESSAGE_INVALID_TIME_FORMAT);
        
        commandBox.runCommand("add Meet Jim, from tomorrow to today");
        assertResultMessage(Messages.MESSAGE_INVALID_TIME_INTERVAL);   
        
        commandBox.runCommand("add Meet Jim, at tomorrow by today");
        assertResultMessage(Messages.MESSAGE_INVALID_TIME_INTERVAL);   
        
        commandBox.runCommand("add Meet Jim, from today to tomorrow by yesterday");
        assertResultMessage(Messages.MESSAGE_INVALID_TIME_INTERVAL);      
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
