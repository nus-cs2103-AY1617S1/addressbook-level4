package seedu.todo.guitests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.todo.commons.util.DateUtil;
import seedu.todo.models.Event;

// @@author A0139812A
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
        assertEventVisibleAfterCmd(command, event);
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
        assertEventNotVisibleAfterCmd(command, event);
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
        String command = "add event Presentation that never ends from 2pm";
        console.runCommand(command);
        String expectedDisambiguation = "add event \"Presentation that never ends\" from \"2pm\" to \"<end time>\"";
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
    }
    
    @Test
    public void addEvent_missingStartEndDate_disambiguate() {
        String command = "add event Presentation that does not take place";
        console.runCommand(command);
        String expectedDisambiguation = "add event \"Presentation that does not take place\" from \"<start time>\" to \"<end time>\"";
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
    }

    @Test
    public void addEvent_missingStartBeforeEnd_disambiguate() {
        String command = "add event Presentation from 9pm to 2pm";
        console.runCommand(command);
        String expectedDisambiguation = "add event \"Presentation\" from \"9pm\" to \"2pm\"";
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
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

}
