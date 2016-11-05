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
        assertTaskVisibleAfterCmd(command, testTask);
    }
    
    @Test
    public void updateCommand_updateTaskWithDeadlineToNewDeadline_success() {
        // Add a task
        console.runCommand("add Buy milk by 2016-10-15 2pm");
        
        // Update the task
        String command = "update 1 by today 5pm";
        testTask.setName("Buy milk");
        testTask.setDueDate(LocalDateTime.now().toLocalDate().atTime(17, 0));
        assertTaskVisibleAfterCmd(command, testTask);
    }
    
    @Test
    public void updateCommand_updateFloatingTaskToNewDeadline_success() {
        // Add a task
        console.runCommand("add Buy milk");
        
        // Update the task
        String command = "update 1 by today 5pm";
        testTask.setName("Buy milk");
        testTask.setDueDate(LocalDateTime.now().toLocalDate().atTime(17, 0));
        assertTaskVisibleAfterCmd(command, testTask);
    }

    @Ignore
    @Test
    public void updateCommand_updateTaskWithDeadlineToFloatingTask() {
        // TODO: Make this pass
        
        // Add a task
        console.runCommand("add Buy milk by today");
        
        // Update the task
        String command = "update 1 nodeadline";
        testTask.setName("Buy milk");
        testTask.setDueDate(DateUtil.NO_DATETIME_VALUE);
        assertTaskVisibleAfterCmd(command, testTask);
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
        assertEventVisibleAfterCmd(command, testEvent);
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
        assertEventVisibleAfterCmd(command, testEvent);
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
        assertEventVisibleAfterCmd(command, testEvent);
    }
    
    @Test
    public void updateCommand_missingIndex_disambiguate() {
        console.runCommand("add Buy milk");
        console.runCommand("update");
        assertEquals("update <index> [name \"<name>\"] [by \"<deadline>\"]", console.getConsoleInputText());
        assertSameDisambiguationMessage(UpdateController.MESSAGE_INVALID_ITEM_OR_PARAM, console);
    }
    
    @Test
    public void updateCommand_missingUpdateParams_disambiguate() {
        console.runCommand("add Buy milk");
        console.runCommand("update 1");
        assertEquals("update 1 [name \"<name>\"] [by \"<deadline>\"]", console.getConsoleInputText());
        assertSameDisambiguationMessage(null, console);
    }
    
    @Test
    public void updateCommand_missingParamType_disambiguate() {
        console.runCommand("add Buy milk");
        console.runCommand("update 1 Buy bread");
        assertEquals("update <index> [name \"<name>\"] [by \"<deadline>\"]", console.getConsoleInputText());
        assertSameDisambiguationMessage(UpdateController.MESSAGE_INVALID_ITEM_OR_PARAM, console);
    }
    
    @Test
    public void updateCommand_missingParamValue_disambiguate() {
        console.runCommand("add Buy milk");
        console.runCommand("update "
                + ""
                + ""
                + ""
                + ""
                + "1 name");
        assertEquals("update 1 [name \"<name>\"] [by \"<deadline>\"]", console.getConsoleInputText());
        assertSameDisambiguationMessage(null, console);
    }
    
    @Test
    public void updateCommand_invalidIndex_disambiguate() {
        console.runCommand("add Buy milk");
        console.runCommand("update 2 name Buy bread");
        assertEquals("update 2 [name \"Buy bread\"] [by \"<deadline>\"]", console.getConsoleInputText());
        assertSameDisambiguationMessage(UpdateController.MESSAGE_INVALID_ITEM_OR_PARAM, console);
    }
    
    @Test
    public void updateCommand_invalidDate_disambiguate() {
        console.runCommand(String.format("add Buy milk by %s", twoDaysFromNowIsoString));
        console.runCommand("update 1 by invaliddate");
        assertEquals("update 1 [name \"<name>\"] [by \"invaliddate\"]", console.getConsoleInputText());
        assertSameDisambiguationMessage(UpdateController.MESSAGE_CANNOT_PARSE_DATE, console);
    }

}
