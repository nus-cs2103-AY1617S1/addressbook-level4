package guitests;

import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
import seedu.todo.ui.PrettifyDate;

//@@author A0093896H
/**
 * Unit test for PrettifyDate Class
 */
public class PrettifyDateTest {

    @Test
    public void prettifyDateTest() {
        LocalDate ldy = LocalDate.now().minusDays(1);        
        assertEquals(PrettifyDate.prettifyDate(ldy), "Yesterday");
        
        ldy = LocalDate.now().minusDays(2);
        assertEquals(PrettifyDate.prettifyDate(ldy), "2 days ago");
        
        ldy = LocalDate.now().plusDays(2);
        assertEquals(PrettifyDate.prettifyDate(ldy), "2 days later");
    }
    

}
