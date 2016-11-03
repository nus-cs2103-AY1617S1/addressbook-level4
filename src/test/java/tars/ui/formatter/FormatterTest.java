package tars.ui.formatter;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Test;
import tars.model.task.ReadOnlyTask;
import tars.model.task.rsv.RsvTask;

/**
 * Formatter test
 * 
 * @@author A0139924W
 */
public class FormatterTest {
    
    @Test
    public void formatTaskList_emptyList() {
        String actualFormattedText =
                new Formatter().formatTaskList(new ArrayList<ReadOnlyTask>());
        String expectedFormmatedText =
                String.format(Formatter.EMPTY_LIST_MESSAGE, "tasks");

        assertEquals(expectedFormmatedText, actualFormattedText);
    }
    
    @Test
    public void formatRsvTaskList_emptyList() {
        String actualFormattedText =
                new Formatter().formatRsvTaskList(new ArrayList<RsvTask>());
        String expectedFormmatedText =
                String.format(Formatter.EMPTY_LIST_MESSAGE, "tasks");

        assertEquals(expectedFormmatedText, actualFormattedText);
    }
}
