package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import seedu.todo.ui.CardStyler;
//@@author A0093896H
public class CardStylerTest {

    private Text name;
    private Text details;
    private Text recurrence;
    private Text onDate;
    private Text byDate;
    private Text tags;
    private Circle priorityLevel;
    
    @Before
    public void setup() {
        name = new Text();
        details = new Text();
        onDate = new Text();
        byDate = new Text();
        tags = new Text();
        recurrence = new Text();       
        priorityLevel = new Circle();
    }
    
    @Test
    public void styleForCompletionTest() {
        CardStyler.styleForCompletion(name, details, onDate, byDate, tags, recurrence, priorityLevel);
        assertEquals(name.getFill(), CardStyler.COMPLETION_COLOR);
        assertEquals(details.getFill(), CardStyler.COMPLETION_COLOR);
        assertEquals(onDate.getFill(), CardStyler.COMPLETION_COLOR);
        assertEquals(byDate.getFill(), CardStyler.COMPLETION_COLOR);
        assertEquals(tags.getFill(), CardStyler.COMPLETION_COLOR);
        assertEquals(recurrence.getFill(), CardStyler.COMPLETION_COLOR);
        
        assertEquals(priorityLevel.getFill(), CardStyler.COMPLETION_PRIORITY_COLOR);
        assertEquals(priorityLevel.getStroke(), CardStyler.COMPLETION_PRIORITY_COLOR);
    }
    
    @Test
    public void styleForOverdueTest() {
        CardStyler.styleForOverdue(name, details, onDate, byDate, tags, recurrence);
        assertEquals(name.getFill(), CardStyler.OVERDUE_COLOR);
        assertEquals(details.getFill(), CardStyler.OVERDUE_COLOR);
        assertEquals(onDate.getFill(), CardStyler.OVERDUE_COLOR);
        assertEquals(byDate.getFill(), CardStyler.OVERDUE_COLOR);
        assertEquals(tags.getFill(), CardStyler.OVERDUE_COLOR);
        assertEquals(recurrence.getFill(), CardStyler.OVERDUE_COLOR);

    }
    
}
