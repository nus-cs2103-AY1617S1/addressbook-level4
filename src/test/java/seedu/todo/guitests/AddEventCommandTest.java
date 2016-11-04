package seedu.todo.guitests;

import static org.junit.Assert.*;
import static seedu.todo.testutil.AssertUtil.assertSameDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;

import seedu.todo.commons.util.DateUtil;
import seedu.todo.guitests.guihandles.TaskListDateItemHandle;
import seedu.todo.guitests.guihandles.TaskListEventItemHandle;
import seedu.todo.models.Event;

public class AddEventCommandTest extends GuiTest {

    @Test
    public void addEvent_eventSameDateInFuture_isVisible() {
        // Get formatted string for two days from now, e.g. 17 Oct 2016
        LocalDateTime twoDaysFromNow = LocalDateTime.now().plusDays(2);
        String twoDaysFromNowString = DateUtil.formatDate(twoDaysFromNow);
        String twoDaysFromNowIsoString = DateUtil.formatIsoDate(twoDaysFromNow);
        
        String command = String.format("add event Presentation in the Future from %s 2pm to %s 9pm", twoDaysFromNowString, twoDaysFromNowString);
        Event event = new Event();
        event.setName("Presentation in the Future");
        event.setStartDate(DateUtil.parseDateTime(String.format("%s 14:00:00", twoDaysFromNowIsoString)));
        event.setEndDate(DateUtil.parseDateTime(String.format("%s 19:00:00", twoDaysFromNowIsoString)));
        assertAddSuccess(command, event);
    }
    
    @Test
    public void addEvent_eventSameDateInPast_isNotVisible() {
        // Get formatted string for two days before now, e.g. 13 Oct 2016
        LocalDateTime twoDaysBeforeNow = LocalDateTime.now().minusDays(2);
        String twoDaysBeforeNowString = DateUtil.formatDate(twoDaysBeforeNow);
        String twoDaysBeforeNowIsoString = DateUtil.formatIsoDate(twoDaysBeforeNow);
        
        // Creates a task in the same date to make sure that a DateItem is created but not a EventItem
        console.runCommand("add Task in the same day by " + twoDaysBeforeNowString);
        
        String command = String.format("add event Presentation in the Past from %s 2pm to %s 9pm", twoDaysBeforeNowString, twoDaysBeforeNowString);
        Event event = new Event();
        event.setName("Presentation in the Past");
        event.setStartDate(DateUtil.parseDateTime(String.format("%s 14:00:00", twoDaysBeforeNowIsoString)));
        event.setEndDate(DateUtil.parseDateTime(String.format("%s 19:00:00", twoDaysBeforeNowIsoString)));
        assertAddNotVisible(command, event);
    }
    
    @Test
    public void addEvent_missingStartDate_disambiguate() {
        String command = "add event Presentation that never starts to 9pm";
        console.runCommand(command);
        String expectedDisambiguation = "add event \"Presentation that never starts\" from \"<start time>\" to \"9pm\"";
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
    }

    @Test
    public void addEvent_missingEndDate_disambiguate() {
        // TODO
    }

    @Test
    public void addEvent_missingStartEndDate_disambiguate() {
        // TODO
    }
    
    @Test
    public void addEvent_missingName_disambiguate() {
        String command = "add event from 2pm to 9pm";
        console.runCommand(command);
        String expectedDisambiguation = "add event \"<name>\" from \"2pm\" to \"9pm\"";
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
    }
    
    @Test
    public void addEvent_unmatchedQuotes_commandRemains() {
        String command = "add event \"Presentation from 2pm to 9pm";
        console.runCommand(command);
        assertEquals(console.getConsoleInputText(), command);
    }

    /**
     * Utility method for testing if event has been successfully added to the GUI.
     * This runs a command and checks if TaskList contains TaskListEventItem that matches
     * the task that was just added. <br><br>
     * 
     * TODO: Extract out method in AddController that can return task from command,
     *       and possibly remove the need to have eventToAdd.
     */
    private void assertAddSuccess(String command, Event eventToAdd) {
        // Run the command in the console.
        console.runCommand(command);
        
        // Get the event date.
        LocalDateTime eventStartDateTime = eventToAdd.getStartDate();
        if (eventStartDateTime == null) {
            eventStartDateTime = DateUtil.NO_DATETIME_VALUE;
        }
        LocalDate eventStartDate = eventStartDateTime.toLocalDate();
        
        // Check TaskList if it contains a TaskListDateItem with the date of the event start date.
        TaskListDateItemHandle dateItem = taskList.getTaskListDateItem(eventStartDate);
        assertSameDate(eventStartDate, dateItem);
        
        // Check TaskListDateItem if it contains the TaskListEventItem with the same data.
        TaskListEventItemHandle eventItem = dateItem.getTaskListEventItem(eventToAdd.getName());
        assertEquals(eventItem.getName(), eventToAdd.getName());
    }
    
    private void assertAddNotVisible(String command, Event eventToAdd) {
        // Run the command in the console.
        console.runCommand(command);
        
        // Get the event date.
        LocalDateTime eventStartDateTime = eventToAdd.getStartDate();
        if (eventStartDateTime == null) {
            eventStartDateTime = DateUtil.NO_DATETIME_VALUE;
        }
        LocalDate eventStartDate = eventStartDateTime.toLocalDate();
        
        // Gets the date item that might contain the event
        TaskListDateItemHandle dateItem = taskList.getTaskListDateItem(eventStartDate);
        
        // It's fine if there's not date item, because it's not visible.
        if (dateItem == null) {
            return;
        }
        
        TaskListEventItemHandle eventItem = dateItem.getTaskListEventItem(eventToAdd.getName());
        assertNull(eventItem);
    }

}
