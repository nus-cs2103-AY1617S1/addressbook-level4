package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandTest.TestDataHelper;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.TaskManager;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

//@@author A0142325R

/*
 * Responsible for testing the correct execution of listCommand
 */


public class ListCommandTest extends CommandTest{
    
    /*
     * Format: list tasks/events/done/undone/empty_parameter
     * Equivalence partitions: tasks, events,done,undone,empty_parameter,any other invalid 
     * paramaters which do not belong to any of the above listed parameters
     * 
     */
    
    //----------------------------Invalid execution------------------------------------------
    
    //test for valid command format
    
    
    @Test
    public void execute_listInvalidFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(ListCommand.MESSAGE_INVALID_LIST_COMMAND, ListCommand.MESSAGE_USAGE);
        assertAbsenceKeywordFormatBehaviorForCommand("list task", expectedMessage);
        
    }
    
    
    //-----------------------------Correct execution------------------------------------------
    
    
    //correct execution of scenario "list"
    
    @Test
    public void execute_list_showsAll() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateUndoneTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateDoneTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateUndoneEventWithName("KE Y");
        Task p4 = helper.generateDoneEventWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, p2,p3,p4);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(p1,p2,p3,p4);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }
    
    
    
    //correct execution of scenario "list tasks"
    
    @Test
    public void execute_list_showsTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateUndoneTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateDoneTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateUndoneEventWithName("KE Y");
        Task p4 = helper.generateDoneEventWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, pTarget2,p3,p4);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1,pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("list tasks",
                ListCommand.MESSAGE_TASK_SUCCESS,
                expectedAB,
                expectedList);
    
    }
    
    
    //correct execution of scenario "list events"
    
    @Test
    public void execute_list_showsEvents() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateUndoneTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateDoneTaskWithName("bla KEY bla bceofeia");
        Task pTarget3 = helper.generateUndoneEventWithName("KE Y");
        Task pTarget4 = helper.generateDoneEventWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, p2,pTarget3,pTarget4);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget3,pTarget4);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("list events",
                ListCommand.MESSAGE_EVENT_SUCCESS,
                expectedAB,
                expectedList);
    
    }
    
    
    //correct execution of scenario "list done"
    
    @Test
    public void execute_list_showsDoneTasksAndEvents() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateUndoneTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateDoneTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateUndoneEventWithName("KE Y");
        Task pTarget4 = helper.generateDoneEventWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget2,p3,pTarget4);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget2,pTarget4);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("list done",
                ListCommand.MESSAGE_LIST_DONE_TASK_SUCCESS,
                expectedAB,
                expectedList);
    }
    
    //correct execution of scenario "list undone"
    
    @Test
    public void execute_list_showsUndoneTasksAndEvents() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateUndoneTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateDoneTaskWithName("bla KEY bla bceofeia");
        Task pTarget3 = helper.generateUndoneEventWithName("KE Y");
        Task p4 = helper.generateDoneEventWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p2,pTarget3,p4);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1,pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("list undone",
                ListCommand.MESSAGE_LIST_UNDONE_TASK_SUCCESS,
                expectedAB,
                expectedList);
    }
    


}
