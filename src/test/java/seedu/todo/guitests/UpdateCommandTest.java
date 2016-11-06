package seedu.todo.guitests;

import static org.junit.Assert.*;
import static seedu.todo.testutil.AssertUtil.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import seedu.todo.commons.util.DateUtil;
import seedu.todo.controllers.UpdateController;
import seedu.todo.models.Event;
import seedu.todo.models.Task;

public class UpdateCommandTest extends GuiTest {
    
    // Fixtures
    private LocalDateTime twoDaysFromNow = LocalDateTime.now().plusDays(2);
    private String twoDaysFromNowString = DateUtil.formatDate(twoDaysFromNow);
    private String twoDaysFromNowIsoString = DateUtil.formatIsoDate(twoDaysFromNow);
    private Event testEvent = new Event();
    private Task testTask = new Task();
    
    @Before
    public void initTestCase() {
        testEvent = new Event();
        testTask = new Task();
        console.runCommand("clear");
    }
    
    @Test
    public void updateCommand_updateTaskName_success() {
        // Add a task
        console.runCommand("add Buy milk by 2016-10-15 2pm");
        
        // Update the task
        String command = "update 1 name Buy bread";
        testTask.setName("Buy bread");
        testTask.setDueDate(DateUtil.parseDateTime("2016-10-15 14:00:00"));
        console.runCommand(command);
        assertTaskVisible(testTask);
    }
    
    @Test
    public void updateCommand_updateTaskWithDeadlineToNewDeadline_success() {
        // Add a task
        console.runCommand("add Buy milk by 2016-10-15 2pm");
        
        // Update the task
        String command = "update 1 by today 5pm";
        testTask.setName("Buy milk");
        testTask.setDueDate(LocalDateTime.now().toLocalDate().atTime(17, 0));
        console.runCommand(command);
        assertTaskVisible(testTask);
    }
    
    @Test
    public void updateCommand_updateFloatingTaskToNewDeadline_success() {
        // Add a task
        console.runCommand("add Buy milk");
        
        // Update the task
        String command = "update 1 by today 5pm";
        testTask.setName("Buy milk");
        testTask.setDueDate(LocalDateTime.now().toLocalDate().atTime(17, 0));
        console.runCommand(command);
        assertTaskVisible(testTask);
    }

    @Test
    public void updateCommand_updateTaskWithDeadlineToFloatingTask() {
        // Add a task
        console.runCommand("add Buy milk by today");
        
        // Update the task
        String command = "update 1 by null";
        testTask.setName("Buy milk");
        testTask.setDueDate(null);
        console.runCommand(command);
        assertTaskVisible(testTask);
    }

    @Test
    public void updateCommand_updateEventName_success() {
        // Add a task
        console.runCommand(String.format("add event Presentation from %s 2pm to %s 9pm", twoDaysFromNowString, twoDaysFromNowString));
        
        // Update the task
        String command = "update 1 name Updated presentation";
        testEvent.setName("Updated presentation");
        testEvent.setStartDate(DateUtil.parseDateTime(String.format("%s 14:00:00", twoDaysFromNowIsoString)));
        testEvent.setEndDate(DateUtil.parseDateTime(String.format("%s 21:00:00", twoDaysFromNowIsoString)));
        console.runCommand(command);
        assertEventVisible(testEvent);
    }
    
    @Test
    public void updateCommand_updateEventStartDate_success() {
        // Add a task
        console.runCommand(String.format("add event Presentation from %s 2pm to %s 9pm", twoDaysFromNowString, twoDaysFromNowString));
        
        // Update the task
        String command = String.format("update 1 from %s 5pm", twoDaysFromNowString);
        
        testEvent.setName("Presentation");
        testEvent.setStartDate(DateUtil.parseDateTime(String.format("%s 17:00:00", twoDaysFromNowIsoString)));
        testEvent.setEndDate(DateUtil.parseDateTime(String.format("%s 21:00:00", twoDaysFromNowIsoString)));
        console.runCommand(command);
        assertEventVisible(testEvent);
    }

    @Test
    public void updateCommand_updateEventEndDate_success() {
        // Add a task
        console.runCommand(String.format("add event Presentation from %s 2pm to %s 9pm", twoDaysFromNowString, twoDaysFromNowString));
        
        // Update the task
        String command = String.format("update 1 to %s 5pm", twoDaysFromNowString);
        testEvent.setName("Presentation");
        testEvent.setStartDate(DateUtil.parseDateTime(String.format("%s 14:00:00", twoDaysFromNowIsoString)));
        testEvent.setEndDate(DateUtil.parseDateTime(String.format("%s 17:00:00", twoDaysFromNowIsoString)));
        console.runCommand(command);
        assertEventVisible(testEvent);
    }
    
    @Test
    public void updateTask_missingIndex_disambiguate() {
        console.runCommand("add Buy milk");
        console.runCommand("update");
        String consoleMessage = String.format(UpdateController.UPDATE_TASK_TEMPLATE,
                UpdateController.INDEX_FIELD, UpdateController.NAME_FIELD, UpdateController.DEADLINE_FIELD);
        assertEquals(consoleMessage, console.getConsoleInputText());
        assertSameDisambiguationMessage(UpdateController.MESSAGE_INVALID_ITEM_OR_PARAM, console);
    }
    
    @Test
    public void updateTask_missingUpdateParams_disambiguate() {
        console.runCommand("add Buy milk");
        console.runCommand("update 1");
        String consoleMessage = String.format(UpdateController.UPDATE_TASK_TEMPLATE,
                "1", UpdateController.NAME_FIELD, UpdateController.DEADLINE_FIELD);
        assertEquals(consoleMessage, console.getConsoleInputText());
        assertSameDisambiguationMessage(null, console);
    }
    
    @Test
    public void updateTask_missingParamType_disambiguate() {
        console.runCommand("add Buy milk");
        console.runCommand("update 1 Buy bread");
        String consoleMessage = String.format(UpdateController.UPDATE_TASK_TEMPLATE,
                UpdateController.INDEX_FIELD, UpdateController.NAME_FIELD, UpdateController.DEADLINE_FIELD);
        assertEquals(consoleMessage, console.getConsoleInputText());
        assertSameDisambiguationMessage(UpdateController.MESSAGE_INVALID_ITEM_OR_PARAM, console);
    }
    
    @Test
    public void updateTask_missingParamValue_disambiguate() {
        console.runCommand("add Buy milk");
        console.runCommand("update 1 name");
        String consoleMessage = String.format(UpdateController.UPDATE_TASK_TEMPLATE,
                "1", UpdateController.NAME_FIELD, UpdateController.DEADLINE_FIELD);
        assertEquals(consoleMessage, console.getConsoleInputText());
        assertSameDisambiguationMessage(null, console);
    }
    
    @Test
    public void updateTask_invalidIndex_disambiguate() {
        console.runCommand("add Buy milk");
        console.runCommand("update 2 name Buy bread");
        String consoleMessage = String.format(UpdateController.UPDATE_TASK_TEMPLATE,
                "2", "Buy bread", UpdateController.DEADLINE_FIELD);
        assertEquals(consoleMessage, console.getConsoleInputText());
        assertSameDisambiguationMessage(UpdateController.MESSAGE_INVALID_ITEM_OR_PARAM, console);
    }
    
    @Test
    public void updateTask_invalidDate_disambiguate() {
        console.runCommand(String.format("add Buy milk by %s", twoDaysFromNowIsoString));
        console.runCommand("update 1 by invaliddate");
        String consoleMessage = String.format(UpdateController.UPDATE_TASK_TEMPLATE,
                "1", UpdateController.NAME_FIELD, "invaliddate");
        assertEquals(consoleMessage, console.getConsoleInputText());
        assertSameDisambiguationMessage(UpdateController.MESSAGE_CANNOT_PARSE_DATE, console);
    }
    
    @Test
    public void updateEvent_missingParamValue_disambiguate() {
        console.runCommand(String.format("add event Presentation from %s 2pm to %s 9pm", twoDaysFromNowIsoString, twoDaysFromNowIsoString));
        console.runCommand("update 1 name");
        String consoleMessage = String.format(UpdateController.UPDATE_EVENT_TEMPLATE,
                "1", UpdateController.NAME_FIELD, UpdateController.START_TIME_FIELD, UpdateController.END_TIME_FIELD);
        assertEquals(consoleMessage, console.getConsoleInputText());
        assertSameDisambiguationMessage(null, console);
    }
    
    @Test
    public void updateEvent_missingParamValueDate_disambiguate() {
        console.runCommand(String.format("add event Presentation from %s 2pm to %s 9pm", twoDaysFromNowIsoString, twoDaysFromNowIsoString));
        console.runCommand("update 1 name");
        String consoleMessage = String.format(UpdateController.UPDATE_EVENT_TEMPLATE,
                "1", UpdateController.NAME_FIELD, UpdateController.START_TIME_FIELD, UpdateController.END_TIME_FIELD);
        assertEquals(consoleMessage, console.getConsoleInputText());
        assertSameDisambiguationMessage(null, console);
    }
    
    @Test
    public void updateEvent_invalidStartDate_disambiguate() {
        console.runCommand(String.format("add event Presentation from %s 2pm to %s 9pm", twoDaysFromNowIsoString, twoDaysFromNowIsoString));
        console.runCommand("update 1 from invaliddate to today 2pm");
        String consoleMessage = String.format(UpdateController.UPDATE_EVENT_TEMPLATE,
                "1", UpdateController.NAME_FIELD, "invaliddate", "today 2pm");
        assertEquals(consoleMessage, console.getConsoleInputText());
        assertSameDisambiguationMessage(UpdateController.MESSAGE_CANNOT_PARSE_DATE, console);
    }
    
    @Test
    public void updateEvent_invalidEndDate_disambiguate() {
        console.runCommand(String.format("add event Presentation from %s 2pm to %s 9pm", twoDaysFromNowIsoString, twoDaysFromNowIsoString));
        console.runCommand("update 1 from today 2pm to invaliddate");
        String consoleMessage = String.format(UpdateController.UPDATE_EVENT_TEMPLATE,
                "1", UpdateController.NAME_FIELD, "today 2pm", "invaliddate");
        assertEquals(consoleMessage, console.getConsoleInputText());
        assertSameDisambiguationMessage(UpdateController.MESSAGE_CANNOT_PARSE_DATE, console);
    }
    
    @Test
    public void updateTask_withStartEndDate_disambiguate() {
        console.runCommand(String.format("add Buy milk", twoDaysFromNowIsoString, twoDaysFromNowIsoString));
        console.runCommand("update 1 from 2pm to today 9pm");
        String consoleMessage = String.format(UpdateController.UPDATE_TASK_TEMPLATE,
                "1", UpdateController.NAME_FIELD, "2pm");
        assertEquals(consoleMessage, console.getConsoleInputText());
        assertSameDisambiguationMessage(null, console);
    }

}
