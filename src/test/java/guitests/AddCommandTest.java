//package guitests;
//
//import guitests.guihandles.TaskCardHandle;
//import org.junit.Test;
//
//import seedu.whatnow.commons.core.Messages;
//import seedu.whatnow.logic.commands.AddCommand;
//import seedu.whatnow.testutil.TestTask;
//import seedu.whatnow.testutil.TestUtil;
//
//import static org.junit.Assert.assertTrue;
//
//public class AddCommandTest extends WhatNowGuiTest {
//
//    @Test
//    public void add() {
//        //add one task: add task without date & time
//        TestTask[] currentList = td.getTypicalTasks();
//        TestTask taskToAdd = td.h;
//        System.out.println("TASK TO ADD: " + taskToAdd.toString());
//        assertAddSuccess(taskToAdd, currentList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        
//        //add another task
//        taskToAdd = td.i;
//        assertAddSuccess(taskToAdd, currentList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        
//        //add another task: add task with 1 date
//        taskToAdd = td.s;
//        assertAddSuccess(taskToAdd, currentList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        
//        //add another task: add task with 2 dates
//        taskToAdd = td.t;
//        assertAddSuccess(taskToAdd, currentList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        
//        //add another task: add task with 2 dates & 2 time
//        taskToAdd = td.u;
//        assertAddSuccess(taskToAdd, currentList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        
//        //add another task: add task with 1 date & 2 time
//        taskToAdd = td.v;
//        assertAddSuccess(taskToAdd, currentList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        
//        //add duplicate task
//        commandBox.runCommand(td.h.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));
//
//        //add duplicate task
//        commandBox.runCommand(td.t.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));
//        
//        //add duplicate task
//        commandBox.runCommand(td.v.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));
//        
//        //add to empty list
//        commandBox.runCommand("clear");
//        assertAddSuccess(td.a);
//
//        //invalid command
//        commandBox.runCommand("adds Johnny");
//        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
//    }
//
//    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
//        commandBox.runCommand(taskToAdd.getAddCommand());
//
//        //confirm the new card contains the right data
//        TaskCardHandle addedCard = scheduleListPanel.navigateToTask(taskToAdd.getName().fullName);
//        assertMatching(taskToAdd, addedCard);
//
//        //confirm the list now contains all previous tasks plus the new task
//        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
//        assertTrue(scheduleListPanel.isListMatching(expectedList));
//    }
//}
