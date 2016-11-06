package seedu.todo.guitests;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.util.DateUtil;
import seedu.todo.controllers.TagController;
import seedu.todo.controllers.UntagController;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.Event;
import seedu.todo.models.Task;

/**
 * @@author A0093907W
 */
public class TagUntagCommandTest extends GuiTest {
    private final LocalDateTime oneDayFromNow = LocalDateTime.now().plusDays(1);
    private final String oneDayFromNowString = DateUtil.formatDate(oneDayFromNow);
    private final String oneDayFromNowIsoString = DateUtil.formatIsoDate(oneDayFromNow);
    private final LocalDateTime twoDaysFromNow = LocalDateTime.now().plusDays(2);
    private final String twoDaysFromNowString = DateUtil.formatDate(twoDaysFromNow);
    private final String twoDaysFromNowIsoString = DateUtil.formatIsoDate(twoDaysFromNow);
    private final LocalDateTime oneDayToNow = LocalDateTime.now().minusDays(1);
    private final String oneDayToNowString = DateUtil.formatDate(oneDayToNow);
    private final String oneDayToNowIsoString = DateUtil.formatIsoDate(oneDayToNow);
    
    private String commandAdd1 = String.format("add task Buy KOI by \"%s 8pm\"", oneDayToNowString);
    private Task task1 = new Task();
    private String commandAdd2 = String.format("add task Buy Milk by \"%s 9pm\"", oneDayFromNowString);
    private Task task2 = new Task();
    private String commandAdd3 = String.format("add event Some Event from \"%s 4pm\" to \"%s 5pm\"",
            twoDaysFromNowString, twoDaysFromNowString);
    private Event event3 = new Event();
    
    @Before
    public void initFixtures() {
        // Need to re-initialize these on each test because we are modifying
        // them on tags.        
        task1 = new Task();
        task2 = new Task();
        event3 = new Event();
        task1.setName("Buy KOI");
        task1.setDueDate(DateUtil.parseDateTime(
                String.format("%s 20:00:00", oneDayToNowIsoString)));
        task2.setName("Buy Milk");
        task2.setDueDate(DateUtil.parseDateTime(
                String.format("%s 21:00:00", oneDayFromNowIsoString)));
        event3.setName("Some Event");
        event3.setStartDate(DateUtil.parseDateTime(
                String.format("%s 16:00:00", twoDaysFromNowIsoString)));
        event3.setEndDate(DateUtil.parseDateTime(
                String.format("%s 17:00:00", twoDaysFromNowIsoString)));
        console.runCommand("clear");
        console.runCommand(commandAdd1);
        console.runCommand(commandAdd2);
        console.runCommand(commandAdd3);
    }
    
    @Test
    public void tag_task_success() {
        console.runCommand("tag 1 argh");
        task1.addTag("argh");
        assertTaskVisible(task1);
    }
    
    @Test
    public void tag_event_success() {
        console.runCommand("tag 3 zzz");
        event3.addTag("zzz");
        assertEventVisible(event3);
    }
    
    @Test
    public void untag_task_success() {
        console.runCommand("tag 1 bugs");
        console.runCommand("untag 1 bugs");
        assertTaskVisible(task1);
    }
    
    @Test
    public void untag_event_success() {
        console.runCommand("tag 3 errors");
        console.runCommand("untag 3 errors");
        assertEventVisible(event3);
    }
    
    @Test
    public void tag_missingIndex_fail() {
        console.runCommand("tag");
        String consoleMessage = Renderer.MESSAGE_DISAMBIGUATE + "\n\n"
                + TagController.MESSAGE_MISSING_INDEX_AND_TAG_NAME;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void tag_missingTag_fail() {
        console.runCommand("tag 1");
        String consoleMessage = Renderer.MESSAGE_DISAMBIGUATE + "\n\n"
                + TagController.MESSAGE_TAG_NAME_NOT_FOUND;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void untag_missingIndex_fail() {
        console.runCommand("untag");
        String consoleMessage = Renderer.MESSAGE_DISAMBIGUATE + "\n\n"
                + UntagController.MESSAGE_MISSING_INDEX_AND_TAG_NAME;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void untag_missingTag_fail() {
        console.runCommand("untag 1");
        String consoleMessage = Renderer.MESSAGE_DISAMBIGUATE + "\n\n"
                + UntagController.MESSAGE_TAG_NAME_NOT_FOUND;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void untag_tagNotExist_fail() {
        console.runCommand("untag 1 sucks");
        String consoleMessage = Renderer.MESSAGE_DISAMBIGUATE + "\n\n"
                + UntagController.MESSAGE_TAG_NAME_DOES_NOT_EXIST;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void tag_taskWithinLimit_success() {
        for (int i = 0; i < Task.MAX_TAG_LIST_SIZE; i++) {
            String tag = String.format("zz%s", i + 1);
            console.runCommand(String.format("tag 1 %s", tag));
            task1.addTag(tag);
        }
        assertTaskVisible(task1);
        assertEquals(TagController.MESSAGE_TAG_SUCCESS, console.getConsoleTextArea());
    }
    
    @Test
    public void tag_taskExceedLimit_fail() {
        for (int i = 0; i < Task.MAX_TAG_LIST_SIZE + 1; i++) {
            String tag = String.format("zz%s", i + 1);
            console.runCommand(String.format("tag 1 %s", tag));
            task1.addTag(tag);
        }
        assertTaskVisible(task1);
        String consoleMessage = Renderer.MESSAGE_DISAMBIGUATE + "\n\n"
                + TagController.MESSAGE_EXCEED_TAG_SIZE;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void tag_invalidIndex_fail() {
        console.runCommand("tag tmd");
        String consoleMessage = Renderer.MESSAGE_DISAMBIGUATE + "\n\n"
                + TagController.MESSAGE_INDEX_NOT_NUMBER;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void untag_invalidIndex_fail() {
        console.runCommand("untag tmd");
        String consoleMessage = Renderer.MESSAGE_DISAMBIGUATE + "\n\n"
                + UntagController.MESSAGE_INDEX_NOT_NUMBER;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void tag_indexOutOfRange_fail() {
        console.runCommand("tag 100 incoherent");
        String consoleMessage = Renderer.MESSAGE_DISAMBIGUATE + "\n\n"
                + TagController.MESSAGE_INDEX_OUT_OF_RANGE;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void untag_indexOutOfRange_fail() {
        console.runCommand("untag 100 gah");
        String consoleMessage = Renderer.MESSAGE_DISAMBIGUATE + "\n\n"
                + UntagController.MESSAGE_INDEX_OUT_OF_RANGE;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void tag_duplicate_fail() {
        console.runCommand("tag 1 boogs");
        console.runCommand("tag 1 boogs");
        String consoleMessage = Renderer.MESSAGE_DISAMBIGUATE + "\n\n"
                + TagController.MESSAGE_TAG_NAME_EXIST;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
}
