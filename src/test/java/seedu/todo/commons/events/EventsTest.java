package seedu.todo.commons.events;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.todo.commons.events.ui.JumpToListRequestEvent;
import seedu.todo.commons.events.ui.SummaryPanelSelectionEvent;
import seedu.todo.commons.events.ui.TagPanelSelectionEvent;
import seedu.todo.commons.events.ui.WeekSummaryPanelSelectionEvent;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.tag.Tag;
//@@author A0093896H
public class EventsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void jumpToListRequestEvent_test() {
        JumpToListRequestEvent jtlre = new JumpToListRequestEvent(0);
        assertEquals(0, jtlre.targetIndex);
        assertEquals(jtlre.toString(), "JumpToListRequestEvent");
    }
    
    @Test
    public void summaryPanelSelectionEvent_test() {
        SummaryPanelSelectionEvent spse = new SummaryPanelSelectionEvent();
        assertEquals(spse.toString(), "SummaryPanelSelectionEvent");
    }
    
    @Test
    public void weekSummaryPanelSelectionEvent_test() {
        WeekSummaryPanelSelectionEvent wspse = new WeekSummaryPanelSelectionEvent();
        assertEquals(wspse.toString(), "WeekSummaryPanelSelectionEvent");
    }
    
    @Test
    public void tagPanelSelectionEvent_test() throws IllegalValueException {
        Tag tag = new Tag("TEST");
        TagPanelSelectionEvent tpse = new TagPanelSelectionEvent(tag);
        
        assertEquals(tpse.tag, tag);
        assertEquals(tpse.toString(), "TagPanelSelectionEvent");
    }
    
    
}
