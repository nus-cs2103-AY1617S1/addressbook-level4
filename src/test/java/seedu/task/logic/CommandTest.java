package seedu.task.logic;

import static org.junit.Assert.assertEquals;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.taskcommons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.model.ModelManager;
import seedu.task.model.ReadOnlyTaskBook;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Event;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;
import seedu.task.model.item.Task;
import seedu.task.storage.StorageManager;
import seedu.taskcommons.core.EventsCenter;

public class CommandTest extends LogicBasicTest {

    /******************************Pre and Post set up*****************************/
    @Before
    public void setup() {
        super.setup();
    }

    @After
    public void teardown() {
        super.teardown();
    }
    
    
    /************************************Test cases*****************************/
    //TODO: more invalid command cases
    @Test
    public void execute_invalidCommand() throws Exception {
        String invalidCommand = "       ";

        //empty command
        assertCommandBehavior_task(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }
    
    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior_task(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }
    
    
    /************************************Utility Methods***************************/
    
    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'task book' and the 'last shown list' are expected to be empty.
     * @see #assertTaskCommandBehavior(String, String, ReadOnlyTaskBook, List)
     */
    protected void assertCommandBehavior_task(String inputCommand, String expectedMessage) throws Exception {
        assertTaskCommandBehavior(inputCommand, expectedMessage, new TaskBook(), Collections.emptyList());
    }
    
    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'task book' and the 'last shown list' are expected to be empty.
     * @see #assertTaskCommandBehavior(String, String, ReadOnlyTaskBook, List)
     */
    protected void assertCommandBehavior_event(String inputCommand, String expectedMessage) throws Exception {
        assertEventCommandBehavior(inputCommand, expectedMessage, new TaskBook(), Collections.emptyList());
    }
    
    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'task book' and the 'last shown list' are expected to be empty.
     */
    protected void assertHelpCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertEventCommandBehavior(inputCommand, expectedMessage, new TaskBook(), Collections.emptyList());
        assertTaskCommandBehavior(inputCommand, expectedMessage, new TaskBook(), Collections.emptyList());
    }
    
    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task book data are same as those in the {@code expectedTaskBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskBook} was saved to the storage file. <br>
     */
    protected void assertTaskCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);
        
        List<ReadOnlyTask> list = model.getFilteredTaskList();
        
        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedTaskBook);
    }
    
    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task book data are same as those in the {@code expectedTaskBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskBook} was saved to the storage file. <br>
     */
    protected void assertEventCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook,
                                       List<? extends ReadOnlyEvent> expectedShownList) throws Exception {
        
        //Execute the command
        CommandResult result = logic.execute(inputCommand);
        //List<ReadOnlyEvent> list = model.getFilteredEventList();
        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredEventList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedTaskBook);
    }
    
    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task book data are same as those in the {@code expectedTaskBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskBook} was saved to the storage file. <br>
     */
    protected void assertTaskAndEventCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook,
                                       List<? extends ReadOnlyTask> expectedTaskList, 
                                       List<? extends ReadOnlyEvent> expectedEventList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);
        
        List<ReadOnlyTask> list = model.getFilteredTaskList();
        
        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedTaskList, model.getFilteredTaskList());
        assertEquals(expectedEventList, model.getFilteredEventList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedTaskBook);
    }
    
    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    protected void assertTaskIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior_task(commandWord , expectedMessage); //index missing
        assertCommandBehavior_task(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandBehavior_task(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandBehavior_task(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandBehavior_task(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    protected void assertEventIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior_event(commandWord , expectedMessage); //index missing
        assertCommandBehavior_event(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandBehavior_event(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandBehavior_event(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandBehavior_event(commandWord + " not_a_number", expectedMessage);
    }
    
    /**
     * Confirms the 'invalid argument type behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    protected void assertIncorrectTypeFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior_task(commandWord + " 1", expectedMessage);
    }
    
    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    protected void assertTaskIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new TaskBook());
        for (Task t : taskList) {
            model.addTask(t);
        }

        assertTaskCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskBook(), taskList);
    }
    
    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    protected void assertEventIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Event> eventList = helper.generateEventList(2);

        // set AB state to 2 tasks
        model.resetData(new TaskBook());
        for (Event t : eventList) {
            model.addEvent(t);
        }

        assertEventCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskBook(), eventList);
    }
    
    /**
     * Before executing edit command, executes the add command and list command
     * and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task book data are same as those in the {@code expectedTaskBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskBook} was saved to the storage file. <br>
     */
    protected void assertEditTaskCommandBehavior(String addCommandInput, String listCommandInput,
                                       String inputCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {
        
        //Adds a task and lists the task
        logic.execute(addCommandInput);
        logic.execute(listCommandInput);
        
        //Execute the edit command
        CommandResult result = logic.execute(inputCommand);
        
        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedTaskBook);
    }
    
    /**
     * Before executing edit command, executes the add command and list command
     * and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task book data are same as those in the {@code expectedTaskBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskBook} was saved to the storage file. <br>
     */
    protected void assertEditEventCommandBehavior(String addCommandInput, String listCommandInput,
                                       String inputCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook,
                                       List<? extends ReadOnlyEvent> expectedShownList) throws Exception {
        
        //Adds an event and lists the event
        logic.execute(addCommandInput);
        logic.execute(listCommandInput);
        
        //Execute the edit command
        CommandResult result = logic.execute(inputCommand);
        
        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredEventList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedTaskBook);
    }
    
    /**
     * Testing for editing task or event to duplicate
     * Before executing edit command, executes the add command for 2 tasks or events and list command for tasks or events
     * and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task book data are same as those in the {@code expectedTaskBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskBook} was saved to the storage file. <br>
     */
    protected void assertEditDuplicateCommandBehavior(String addCommandInput, String addCommandInput2, String listCommandInput,
                                       String inputCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook) throws Exception {
        
        //Adds 2 tasks and lists the task
        logic.execute(addCommandInput);
        logic.execute(addCommandInput2);
        logic.execute(listCommandInput);
        
        //Execute the edit command
        CommandResult result = logic.execute(inputCommand);
        
        
        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedTaskBook);
    }
    
    /**
     * Before executing clear command, marks the task as complete 
     * After executing clear command, executes the list all tasks command
     * and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task book data are same as those in the {@code expectedTaskBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskBook} was saved to the storage file. <br>
     */
    protected void assertClearTaskCommandBehavior(String clearCommand, String markCommand,
                                       String listCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {
        
        //executes the clear command and lists the task
        logic.execute(markCommand);   
        CommandResult result = logic.execute(clearCommand);
        logic.execute(listCommand);      
        
        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedTaskBook);
    }
    
    
    /**
     * After executing clear command, executes the list all events command
     * and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task book data are same as those in the {@code expectedTaskBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskBook} was saved to the storage file. <br>
     */
    protected void assertClearEventCommandBehavior(String clearCommand,
                                       String listCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook,
                                       List<? extends ReadOnlyEvent> expectedShownList) throws Exception {
        
        //executes the clear command and lists the event
        CommandResult result = logic.execute(clearCommand);
        logic.execute(listCommand);      
        
        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredEventList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedTaskBook);
    }
    
    /**
     * Before executing clear command, marks the task as complete 
     * After executing clear command, executes the list all tasks and events command
     * and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task book data are same as those in the {@code expectedTaskBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskBook} was saved to the storage file. <br>
     */
    protected void assertClearTaskAndEventCommandBehavior(String clearCommand, String markCommand, 
                                       String listTaskCommand, String listEventCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook,
                                       List<? extends ReadOnlyTask> expectedTaskList, 
                                       List<? extends ReadOnlyEvent> expectedEventList) throws Exception {
        
        //executes the clear command and lists the tasks and events
        logic.execute(markCommand);
        CommandResult result = logic.execute(clearCommand);
        logic.execute(listTaskCommand);
        logic.execute(listEventCommand);
        
        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedTaskList, model.getFilteredTaskList());
        assertEquals(expectedEventList, model.getFilteredEventList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedTaskBook);
    }
}
