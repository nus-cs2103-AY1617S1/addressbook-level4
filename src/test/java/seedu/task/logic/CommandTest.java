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
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }
    
    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }
    
    
    /************************************Utility Methods***************************/
    
    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'task book' and the 'last shown list' are expected to be empty.
     * @see #assertTaskCommandBehavior(String, String, ReadOnlyTaskBook, List)
     */
    protected void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
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
        List<ReadOnlyEvent> list = model.getFilteredEventList();
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
    protected void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord , expectedMessage); //index missing
        assertCommandBehavior(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
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
    
}
