//@@author A0126240W
package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.logic.commands.AddCommand;
import seedu.whatnow.testutil.TestTask;
import seedu.whatnow.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.whatnow.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

public class AddCommandTest extends WhatNowGuiTest {

    @Test
    public void add() {    
        TestTask[] currentList = td.getTypicalTasks();
        
        //add one task
        TestTask taskToAdd = td.h;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add another task
        taskToAdd = td.i;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add another task: add task with 1 date
        taskToAdd = td.s;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add another task: add task with 2 dates
        taskToAdd = td.t;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add another task: add task with 2 dates & 2 time
        taskToAdd = td.u;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add another task: add task with 1 date & 2 time
        taskToAdd = td.v;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);     
        
        //add duplicate task
        commandBox.runCommand(td.h.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add duplicate task
        commandBox.runCommand(td.t.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //add duplicate task
        commandBox.runCommand(td.v.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.a);

        //unknown command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //invalid command
        commandBox.runCommand("add 'workwork'");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        
        //invalid number of arguments for add command
        commandBox.runCommand("add \"\"");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        
        //invalid arguments for add command
        commandBox.runCommand("add \"Buy milk\" hahaha");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        
        //add another task: add task without date & time
        commandBox.runCommand("clear");
        commandBox.runCommand("add \"Buy milk\"");
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, "Buy milk"));
        
        //add another task: add task with 1 date
        commandBox.runCommand("clear");
        commandBox.runCommand("add \"Buy milk\" on 23/2/2017");
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, "Buy milk on 23/02/2017"));
        commandBox.runCommand("add \"Buy milk\" by 27/2/2017");
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, "Buy milk on 27/02/2017"));
        
        //add another task: add task with 2 date
        commandBox.runCommand("clear");
        commandBox.runCommand("add \"Buy milk\" from 12/2/2017 to 25/2/2017");
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, "Buy milk from 12/02/2017 to 25/02/2017"));
        commandBox.runCommand("add \"Buy milk\" 22/2/2017 to 26/2/2017");
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, "Buy milk from 22/02/2017 to 26/02/2017"));
        
        //add another task: add task with 1 date 1 time
        commandBox.runCommand("clear");
        commandBox.runCommand("add \"Buy milk\" 1/2/2018 4pm");
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, "Buy milk on 01/02/2018 04:00pm"));
        commandBox.runCommand("add \"Buy milk\" at 3pm on 3rd oct 2017");
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, "Buy milk on 03/10/2017 03:00pm"));
    
        //add another task: add task with 1 date 2 time
        commandBox.runCommand("clear");
        commandBox.runCommand("add \"Buy milk\" on 4th dec 2017 from 1.30pm to 4:00pm");
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, "Buy milk on 04/12/2017 from 01:30pm to 04:00pm"));
        commandBox.runCommand("add \"Buy milk\" on 03/12/2017 2.30am to 4.30am");
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, "Buy milk on 03/12/2017 from 02:30am to 04:30am"));
    
        //add another task: add task with 2 date 1 time
        commandBox.runCommand("clear");
        commandBox.runCommand("add \"Buy milk\" from 12/2/2017 4pm to 25/2/2017");
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, "Buy milk from 12/02/2017 04:00pm to 25/02/2017 11:59pm"));
        commandBox.runCommand("add \"Buy milk\" 22/2/2017 to 26/2/2017 3pm");
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, "Buy milk from 22/02/2017 12:00am to 26/02/2017 03:00pm"));
    
        //add another task: add task with 2 date 2 time
        commandBox.runCommand("clear");
        commandBox.runCommand("add \"Buy milk\" from 12/2/2017 to 25/2/2017 3pm to 4pm");
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, "Buy milk from 12/02/2017 03:00pm to 25/02/2017 04:00pm"));
        commandBox.runCommand("add \"Buy milk\" 22/2/2017 2pm to 26/2/2017 4pm");
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, "Buy milk from 22/02/2017 02:00pm to 26/02/2017 04:00pm"));
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());
        Arrays.sort(currentList);

        //confirm the new card contains the right data
        TaskCardHandle addedCard = scheduleListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(scheduleListPanel.isListMatching(expectedList));
    }
}
